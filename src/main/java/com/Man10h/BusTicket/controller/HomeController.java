package com.Man10h.BusTicket.controller;

import com.Man10h.BusTicket.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;

    @GetMapping
    public String home() {
        return "Hello World";
    }

    @PostMapping("/getAllToken")
    public ResponseEntity<Map<String, String>> getToken(@RequestBody Map<String, Object> payload) {
        Map<String, String> response = userService.getAllToken(payload);
        if(response == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getToken")
    public ResponseEntity<?> getToken(@RequestParam(name = "refreshToken") String refreshToken) {
        String access_token = userService.getToken(refreshToken);
        if(access_token == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(access_token);
    }

}
