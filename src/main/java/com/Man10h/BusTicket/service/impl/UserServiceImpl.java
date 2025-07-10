package com.Man10h.BusTicket.service.impl;

import com.Man10h.BusTicket.convert.UserConvert;
import com.Man10h.BusTicket.exception.ex.RoleErrorException;
import com.Man10h.BusTicket.exception.ex.UserErrorException;
import com.Man10h.BusTicket.model.dto.UserDTO;
import com.Man10h.BusTicket.model.entity.ImageEntity;
import com.Man10h.BusTicket.model.entity.RoleEntity;
import com.Man10h.BusTicket.model.entity.UserEntity;
import com.Man10h.BusTicket.model.response.UserResponse;
import com.Man10h.BusTicket.repository.ImageRepository;
import com.Man10h.BusTicket.repository.RoleRepository;
import com.Man10h.BusTicket.repository.UserRepository;
import com.Man10h.BusTicket.service.CloudinaryService;
import com.Man10h.BusTicket.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserConvert userConvert;
    private final RestTemplate restTemplate;
    private final CloudinaryService cloudinaryService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ImageRepository imageRepository;

    @Value("${spring.security.oauth2.client.registration.bus_ticket.client-secret}")
    private String client_secret;

    @Value("${spring.security.oauth2.client.registration.bus_ticket.client-id}")
    private String client_id;

    @Value("${spring.security.oauth2.client.registration.bus_ticket.redirect-uri}")
    private String redirect_uri;

    @Override
    public Map<String, String> getAllToken(Map<String, Object> payload) {
        String code = payload.get("code").toString();
        String uri = "http://localhost:8080/realms/bus_ticket/protocol/openid-connect/token";

        MultiValueMap<String, String> entity = new LinkedMultiValueMap<>();
        entity.add("client_id", client_id);
        entity.add("client_secret", client_secret);
        entity.add("grant_type", "authorization_code");
        entity.add("code", code);
        entity.add("redirect_uri", redirect_uri);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(entity, httpHeaders);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, String> res = new HashMap<>();
            String access_token = response.getBody().get("access_token").toString();
            String refresh_token = response.getBody().get("refresh_token").toString();
            String expires_in = response.getBody().get("expires_in").toString();
            res.put("access_token", access_token);
            res.put("refresh_token", refresh_token);
            res.put("expires_in", expires_in);
            return res;
        }
        return null;
    }

    @Override
    public String getToken(String refreshToken) {
        String uri = "http://localhost:8080/realms/bus_ticket/protocol/openid-connect/token";
        MultiValueMap<String, String> entity = new LinkedMultiValueMap<>();
        entity.add("client_id", client_id);
        entity.add("client_secret", client_secret);
        entity.add("grant_type", "refresh_token");
        entity.add("refresh_token", refreshToken);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(entity, httpHeaders);
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                uri, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<Map<String, Object>>() {}
        );
        if (response.getStatusCode() == HttpStatus.OK) {
            if (Objects.requireNonNull(response.getBody()).get("access_token") != null) {
                return response.getBody().get("access_token").toString();
            }
            return null;
        }
        return null;
    }

    @Override
    public UserResponse updateUser(UserDTO userDTO) {
        if(userDTO.getId() == null){
            return null;
        }
        Optional<UserEntity> optionalUser = userRepository.findById(userDTO.getId());
        if(optionalUser.isEmpty()){
            throw new UserErrorException("Cannot find user");
        }
        UserEntity userEntity = optionalUser.get();
        if(userDTO.getRoleId() != null){
            Optional<RoleEntity> optionalRole = roleRepository.findById(userDTO.getRoleId());
            if(optionalRole.isEmpty()){
                throw new RoleErrorException("Cannot find role");
            }
            RoleEntity roleEntity = optionalRole.get();
            userEntity.setRoleEntity(roleEntity);
        }
        if(userDTO.getMultipartFiles() != null && userDTO.getMultipartFiles().size() == 1){
            if(userEntity.getImageEntityList() != null){
                imageRepository.deleteByUserEntity_Id(userDTO.getId());
            }
            for(MultipartFile multipartFile : userDTO.getMultipartFiles()) {
                Map<String, Object> result = cloudinaryService.upload(multipartFile);
                ImageEntity imageEntity = ImageEntity.builder()
                        .url((String) result.get("url"))
                        .userEntity(userEntity)
                        .build();
                imageRepository.save(imageEntity);
            }
        }
        userRepository.save(userEntity);
        return userConvert.convert(userEntity);
    }

    @Override
    public UserResponse getUserById(String id) {
        Optional<UserEntity> optional = userRepository.findById(id);
        if(optional.isEmpty()){
            throw new UserErrorException("Cannot find user");
        }
        UserEntity userEntity = optional.get();
        return userConvert.convert(userEntity);
    }
}
