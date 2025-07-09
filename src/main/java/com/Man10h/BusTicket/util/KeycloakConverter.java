package com.Man10h.BusTicket.util;

import com.Man10h.BusTicket.model.entity.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class KeycloakConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final UserSynchronize userSynchronize;

    public Collection<? extends GrantedAuthority> convertToGrantedAuthorities(Jwt jwt) {
        UserEntity user = userSynchronize.synchronize(jwt);
        return user.getAuthorities();
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt source) {
        return new JwtAuthenticationToken(source, convertToGrantedAuthorities(source));
    }
}
