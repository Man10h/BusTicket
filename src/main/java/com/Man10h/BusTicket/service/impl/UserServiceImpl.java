package com.Man10h.BusTicket.service.impl;

import com.Man10h.BusTicket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${spring.security.oauth2.client.registration.bus_ticket.client-secret}")
    private String client_secret;

    @Value("${spring.security.oauth2.client.registration.bus_ticket.client-id}")
    private String client_id;

    @Value("${spring.security.oauth2.client.registration.bus_ticket.redirect-uri}")
    private String redirect_uri;

    @Override
    public Map<String, String> getToken(Map<String, Object> payload) {
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
            res.put("access_token", access_token);
            res.put("refresh_token", refresh_token);
            return res;
        }
        return null;
    }
}
