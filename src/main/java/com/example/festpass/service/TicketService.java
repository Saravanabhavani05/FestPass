package com.example.festpass.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.festpass.entity.Attendee;
import com.example.festpass.entity.Event;
import com.example.festpass.entity.Ticket;
import com.example.festpass.repository.AttendeeRepository;
import com.example.festpass.repository.EventRepository;
import com.example.festpass.repository.TicketRepository;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;
    private final AttendeeRepository attendeeRepository;

    public TicketService(
            TicketRepository ticketRepository,
            EventRepository eventRepository,
            AttendeeRepository attendeeRepository) {

        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
        this.attendeeRepository = attendeeRepository;
    }

    // =========================
    // GENERATE TICKET
    // =========================

    public Ticket createTicket(Long eventId, Long attendeeId) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new RuntimeException("Event not found"));

        Attendee attendee = attendeeRepository.findById(attendeeId)
                .orElseThrow(() ->
                        new RuntimeException("Attendee not found"));

        if (event.getAvailableTickets() == null ||
                event.getAvailableTickets() <= 0) {

            throw new RuntimeException("No tickets available");
        }

        Ticket ticket = new Ticket();

        ticket.setTicketNumber(
                "FP-" +
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase()
        );

        ticket.setQrToken(
                UUID.randomUUID().toString()
        );

        ticket.setStatus("ACTIVE");

        ticket.setIssuedAt(
                LocalDateTime.now()
        );

        ticket.setEvent(event);
        ticket.setAttendee(attendee);

        event.setAvailableTickets(
                event.getAvailableTickets() - 1
        );

        eventRepository.save(event);

        return ticketRepository.save(ticket);
    }

    // =========================
    // GET ALL TICKETS
    // =========================

    public List<Ticket> getAllTickets() {

        return ticketRepository.findAll();
    }

    // =========================
    // GET TICKET BY NUMBER
    // =========================

    public Ticket getTicketByNumber(String ticketNumber) {

        if (ticketNumber == null ||
                ticketNumber.trim().isEmpty()) {

            throw new RuntimeException(
                    "Ticket number is required");
        }

        return ticketRepository
                .findByTicketNumber(ticketNumber.trim())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Ticket not found"));
    }

    // =========================
    // CHECK-IN USING QR TOKEN
    // =========================

    public Ticket checkIn(String qrToken) {

        if (qrToken == null ||
                qrToken.trim().isEmpty()) {

            throw new RuntimeException(
                    "QR token is required");
        }

        Ticket ticket = ticketRepository
                .findByQrToken(qrToken.trim())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Invalid QR code"));

        if ("USED".equalsIgnoreCase(
                ticket.getStatus())) {

            throw new RuntimeException(
                    "Ticket already used");
        }

        if ("CANCELLED".equalsIgnoreCase(
                ticket.getStatus())) {

            throw new RuntimeException(
                    "Ticket cancelled");
        }

        ticket.setStatus("USED");

        ticket.setCheckedInAt(
                LocalDateTime.now());

        return ticketRepository.save(ticket);
    }

    // =========================
    // CHECK-IN USING TICKET NUMBER
    // =========================

    public Ticket checkInByTicketNumber(
            String ticketNumber) {

        if (ticketNumber == null ||
                ticketNumber.trim().isEmpty()) {

            throw new RuntimeException(
                    "Ticket number is required");
        }

        Ticket ticket = ticketRepository
                .findByTicketNumber(ticketNumber.trim())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Ticket not found"));

        if ("USED".equalsIgnoreCase(
                ticket.getStatus())) {

            throw new RuntimeException(
                    "Ticket already used");
        }

        if ("CANCELLED".equalsIgnoreCase(
                ticket.getStatus())) {

            throw new RuntimeException(
                    "Ticket cancelled");
        }

        ticket.setStatus("USED");

        ticket.setCheckedInAt(
                LocalDateTime.now());

        return ticketRepository.save(ticket);
    }
}