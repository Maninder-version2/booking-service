package com.trainticketapp.bookingservice.controller;

import java.util.Map;
import java.util.Objects;

import static com.trainticketapp.bookingservice.model.CommonConstants.*;

import com.trainticketapp.bookingservice.model.TicketReceipt;
import com.trainticketapp.bookingservice.dto.TicketPurchaseRequest;
import com.trainticketapp.bookingservice.service.TicketBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    @Autowired
    private TicketBookingService ticketBookingService;

    @PostMapping("/purchaseTicket")
    public ResponseEntity<TicketReceipt> purchaseTicket(@RequestBody TicketPurchaseRequest request) {
        TicketReceipt ticketReceipt = ticketBookingService.purchaseTicket(request);
        if (Objects.isNull(ticketReceipt)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketReceipt);
    }

    @GetMapping("/users/{userId}/ticketReceipt")
    public ResponseEntity<TicketReceipt> getTicketPurchaseReceipt(@PathVariable String userId) {
        TicketReceipt ticketReceipt = ticketBookingService.getReceipt(userId);
        if (Objects.isNull(ticketReceipt)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ticketReceipt);
    }

    @GetMapping("/usersBySection")
    public ResponseEntity<Map<String, String>> getUsersBySection(@RequestParam String section) {
        Map<String, String> sectionVsUsers = ticketBookingService.getUsersBySection(section);
        return ResponseEntity.ok(sectionVsUsers);
    }

    @DeleteMapping("/users/{userId}/remove")
    public ResponseEntity<String> removeUser(@PathVariable String userId) {
        boolean removed = ticketBookingService.removeUser(userId);
        if (removed) {
            return ResponseEntity.ok().body("User removed successfully.");
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/users/{userId}/modifySeat")
    public ResponseEntity<Void> modifyUserSeat(@PathVariable String userId, @RequestBody Map<String, String> sectionSeat) {
        String section = sectionSeat.get(SECTION);
        String seat = sectionSeat.get(SEAT);
        boolean modified = ticketBookingService.modifyUserSeat(userId, section, seat);
        if (modified) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}