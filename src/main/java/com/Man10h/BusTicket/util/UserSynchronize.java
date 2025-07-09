package com.Man10h.BusTicket.util;

import com.Man10h.BusTicket.model.entity.RoleEntity;
import com.Man10h.BusTicket.model.entity.UserEntity;
import com.Man10h.BusTicket.repository.RoleRepository;
import com.Man10h.BusTicket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserSynchronize {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserEntity synchronize(Jwt jwt) {
        String userId = jwt.getSubject();
        String email = jwt.getClaims().get("email").toString();
        String username = jwt.getClaims().get("preferred_username").toString();

        Optional<UserEntity> optionalId = userRepository.findById(userId);
        Optional<UserEntity> optionalUsername = userRepository.findByUsername(username);
        Optional<UserEntity> optionalEmail = userRepository.findByEmail(email);
        if (optionalId.isPresent() || optionalEmail.isPresent() || optionalUsername.isPresent()) {
            if(optionalEmail.get().getId().equals(userId) && optionalUsername.get().getId().equals(userId)) {
                return optionalEmail.get();

            }
            return null;
        }
        RoleEntity roleEntity = roleRepository.findById(1L).get();
        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .roleEntity(roleEntity)
                .email(email)
                .id(userId)
                .busEntityList(new ArrayList<>())
                .imageEntityList(new ArrayList<>())
                .ticketEntityList(new ArrayList<>())
                .invoiceEntityList(new ArrayList<>())
                .build();
        userRepository.save(userEntity);
        return userEntity;
    }
}
