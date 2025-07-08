package com.Man10h.BusTicket.util;

import com.Man10h.BusTicket.model.entity.UserEntity;
import com.Man10h.BusTicket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserSynchronize {

    @Autowired
    private UserRepository userRepository;

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
        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .email(email)
                .id(userId)
                .build();
        return userRepository.save(userEntity);
    }
}
