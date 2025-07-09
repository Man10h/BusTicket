package com.Man10h.BusTicket.controller;

import com.Man10h.BusTicket.model.dto.UserDTO;
import com.Man10h.BusTicket.model.response.UserResponse;
import com.Man10h.BusTicket.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<?> getUserById(@AuthenticationPrincipal Jwt jwt) {
        try{
            UserResponse userResponse = userService.getUserById(jwt.getClaim("sub"));
            if(userResponse == null) {
                return ResponseEntity.status(400).build();
            }
            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            return ResponseEntity.status(400).build();
        }
    }

    @PostMapping("/updateProfile")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO) {
        try{
            UserResponse userResponse = userService.updateUser(userDTO);
            if(userResponse == null) {
                return ResponseEntity.status(400).build();
            }
            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            return ResponseEntity.status(400).build();
        }
    }

}
