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

    @PostMapping("/getToken")
    public ResponseEntity<Map<String, String>> getToken(@RequestBody Map<String, Object> payload) {
        Map<String, String> response = userService.getToken(payload);
        if(response == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(response);
    }

}
