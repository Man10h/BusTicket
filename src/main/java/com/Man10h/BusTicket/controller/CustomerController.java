package com.Man10h.BusTicket.controller;

import com.Man10h.BusTicket.model.dto.TicketDTO;
import com.Man10h.BusTicket.model.dto.UserDTO;
import com.Man10h.BusTicket.model.response.InvoiceResponse;
import com.Man10h.BusTicket.model.response.TicketResponse;
import com.Man10h.BusTicket.model.response.TripResponse;
import com.Man10h.BusTicket.model.response.UserResponse;
import com.Man10h.BusTicket.service.PaymentService;
import com.Man10h.BusTicket.service.TripService;
import com.Man10h.BusTicket.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final UserService userService;
    private final TripService tripService;
    private final PaymentService paymentService;

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

    @GetMapping("/getRecommendationTrips")
    public ResponseEntity<?> getRecommendationTrips(@AuthenticationPrincipal Jwt jwt) {
        try{
            List<TripResponse> tripResponses = tripService.getRecommendationTrips(jwt.getClaim("sub"));
            return ResponseEntity.ok(tripResponses);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(400).build();
        }
    }

    @GetMapping("/getAllTickets")
    public ResponseEntity<?> getAllTickets(@AuthenticationPrincipal Jwt jwt) {
        try{
            List<TicketResponse> ticketResponses = paymentService.getAllTickets(jwt.getClaim("sub"));
            return ResponseEntity.ok(ticketResponses);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(400).build();
        }
    }

    @PostMapping("/createTicket")
    public ResponseEntity<?> createTicket(@RequestBody TicketDTO ticketDTO, @AuthenticationPrincipal Jwt jwt) {
        try{
            TicketResponse ticketResponse = paymentService.createTicket(ticketDTO, jwt.getClaim("sub"));
            return ResponseEntity.ok(ticketResponse);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(400).build();
        }
    }

    @GetMapping("/getAllInvoices")
    public ResponseEntity<?> getAllInvoices(@AuthenticationPrincipal Jwt jwt) {
        try{
            List<InvoiceResponse> invoiceResponses = paymentService.getAllInvoices(jwt.getClaim("sub"));
            return ResponseEntity.ok(invoiceResponses);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(400).build();
        }
    }

    @PostMapping("/createInvoice")
    public ResponseEntity<?> createInvoice(@RequestBody Map<String, Object> payload, @AuthenticationPrincipal Jwt jwt) {
        try{
            InvoiceResponse invoiceResponse = paymentService.createInvoice(payload, jwt.getClaim("sub"));
            return ResponseEntity.ok(invoiceResponse);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(400).build();
        }
    }


    @PostMapping("/updateInvoice")
    public ResponseEntity<?> updateInvoiceIsPaid(@RequestBody Map<String, Object> payload) {
        try{
            Integer invoiceId = (Integer) payload.get("invoiceId");
            if(invoiceId == null) {
                return ResponseEntity.status(400).build();
            }
            InvoiceResponse invoiceResponse = paymentService.updateInvoiceIsPaid((long) invoiceId);
            return ResponseEntity.ok(invoiceResponse);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(400).build();
        }
    }
}
