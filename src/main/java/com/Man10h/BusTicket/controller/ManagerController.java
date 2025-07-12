package com.Man10h.BusTicket.controller;

import com.Man10h.BusTicket.model.dto.BusDTO;
import com.Man10h.BusTicket.model.dto.QueryDTO;
import com.Man10h.BusTicket.model.dto.TripDTO;
import com.Man10h.BusTicket.model.response.TripResponse;
import com.Man10h.BusTicket.service.BusService;
import com.Man10h.BusTicket.service.TripService;
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
    private final TripService tripService;

    @GetMapping("/getAllBus")
    public ResponseEntity<?> getBusByUserId(@AuthenticationPrincipal Jwt jwt,
                                            @RequestParam(name = "page") int page,
                                            @RequestParam(name = "pageSize") int pageSize) {
        return ResponseEntity.ok(busService.getBusByUserId(jwt.getClaim("sub"), page, pageSize));
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

    @GetMapping("/getBusByName")
    public ResponseEntity<?> getBusByName(@RequestParam("busName") String busName) {
        return ResponseEntity.ok(busService.getBusByName(busName));
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


    @PostMapping("/createTrip")
    public ResponseEntity<?> createTrip(@RequestBody TripDTO tripDTO, @AuthenticationPrincipal Jwt jwt) {
        try{
            TripResponse tripResponse = tripService.createTrip(tripDTO, jwt.getClaim("sub"));
            if(tripResponse == null){
                return ResponseEntity.status(400).build();
            }
            return ResponseEntity.ok(tripResponse);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(400).build();
        }
    }

    @PostMapping("/updateTrip")
    public ResponseEntity<?> updateTrip(@RequestBody TripDTO tripDTO) {
        try{
            TripResponse tripResponse = tripService.updateTrip(tripDTO);
            if(tripResponse == null){
                return ResponseEntity.status(400).build();
            }
            return ResponseEntity.ok(tripResponse);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(400).build();
        }
    }

    @DeleteMapping("/deleteTrip/{tripId}")
    public ResponseEntity<?> deleteTrip(@PathVariable(name = "tripId") Long tripId) {
        try{
            tripService.deleteTrip(tripId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(400).build();
        }
    }


}
