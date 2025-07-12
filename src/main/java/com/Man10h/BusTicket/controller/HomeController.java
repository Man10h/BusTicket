package com.Man10h.BusTicket.controller;

import com.Man10h.BusTicket.model.dto.QueryDTO;
import com.Man10h.BusTicket.service.TripService;
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
    private final TripService tripService;

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

    @GetMapping("/findTripByQuery")
    public ResponseEntity<?> findTripByQuery(@ModelAttribute QueryDTO queryDTO,
                                             @RequestParam(name = "page") int page,
                                             @RequestParam(name="pageSize") int pageSize) {
        try{
            return ResponseEntity.ok(tripService.find(queryDTO, page, pageSize));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(400).build();
        }
    }

    @GetMapping("/findTripById")
    public ResponseEntity<?> findById(@RequestParam("tripId") Long tripId) {
        try{
            return ResponseEntity.ok(tripService.findById(tripId));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(400).build();
        }
    }

}
