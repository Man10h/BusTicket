package com.Man10h.BusTicket.controller;

import com.Man10h.BusTicket.model.dto.BusDTO;
import com.Man10h.BusTicket.service.BusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final BusService busService;

    @GetMapping("/getAllBus")
    public ResponseEntity<?> getBusByUserId(@AuthenticationPrincipal Jwt jwt,
                                            @RequestParam(name = "page") int page,
                                            @RequestParam(name = "size") int size) {
        return ResponseEntity.ok(busService.getBusByUserId(jwt.getClaim("sub"), page, size));
    }

    @GetMapping("/bus")
    public ResponseEntity<?> getBusById(@RequestParam("busId") Long busId) {
        try{
            return ResponseEntity.ok(busService.getBusById(busId));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(400).build();
        }
    }

    @PostMapping("/createBus")
    public ResponseEntity<?> createBus(@AuthenticationPrincipal Jwt jwt,
                                       @ModelAttribute BusDTO busDTO) {
        try{
            return ResponseEntity.ok(busService.createBus(busDTO, jwt.getClaim("sub")));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(400).build();
        }
    }

    @PostMapping("/updateBus")
    public ResponseEntity<?> updateBus(@ModelAttribute BusDTO busDTO) {
        try{
            return ResponseEntity.ok(busService.updateBus(busDTO));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(400).build();
        }
    }

    @DeleteMapping("/bus/{busId}")
    public ResponseEntity<?> deleteBus(@PathVariable(name = "busId") Long busId) {
        try {
            busService.deleteBus(busId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(400).build();
        }
    }
}
