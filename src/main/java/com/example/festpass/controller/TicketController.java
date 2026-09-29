package com.example.festpass.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.festpass.entity.Ticket;
import com.example.festpass.service.TicketService;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "*")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // =====================================================
    // GENERATE TICKET
    // =====================================================

    @PostMapping("/generate")
    public ResponseEntity<Ticket> generateTicket(
            @RequestParam Long eventId,
            @RequestParam Long attendeeId) {

        Ticket ticket =
                ticketService.createTicket(
                        eventId,
                        attendeeId
                );

        return ResponseEntity.ok(ticket);
    }


    // =====================================================
    // GET ALL TICKETS
    // =====================================================

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {

        List<Ticket> tickets =
                ticketService.getAllTickets();

        return ResponseEntity.ok(tickets);
    }


    // =====================================================
    // GET TICKET BY TICKET NUMBER
    // =====================================================

    @GetMapping("/{ticketNumber}")
    public ResponseEntity<Ticket> getTicket(
            @PathVariable String ticketNumber) {

        Ticket ticket =
                ticketService.getTicketByNumber(
                        ticketNumber
                );

        return ResponseEntity.ok(ticket);
    }


    // =====================================================
    // CHECK-IN USING QR TOKEN
    // =====================================================

    @PostMapping("/checkin")
    public ResponseEntity<Ticket> checkIn(
            @RequestParam String qrToken) {

        Ticket ticket =
                ticketService.checkIn(
                        qrToken
                );

        return ResponseEntity.ok(ticket);
    }


    // =====================================================
    // CHECK-IN USING TICKET NUMBER
    // =====================================================

    @PostMapping("/checkin-by-ticket")
    public ResponseEntity<Ticket> checkInByTicket(
            @RequestParam String ticketNumber) {

        Ticket ticket =
                ticketService.checkInByTicketNumber(
                        ticketNumber
                );

        return ResponseEntity.ok(ticket);
    }

}