package com.Man10h.BusTicket.util;

import jakarta.servlet.http.HttpServletRequest;
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
public class KeycloakConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    public Collection<? extends GrantedAuthority> convertToGrantedAuthorities(Jwt jwt) {
        Map<String, Object> realm_access = jwt.getClaimAsMap("realm_access");
        if(realm_access != null && !realm_access.isEmpty() && realm_access.containsKey("roles")) {
            List<String> roles = (List<String>) realm_access.get("roles");
            return roles.stream().map(it -> new SimpleGrantedAuthority("ROLE_" + it)).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt source) {
        return new JwtAuthenticationToken(source, convertToGrantedAuthorities(source));
    }
}
