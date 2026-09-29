package com.example.festpass.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.festpass.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Optional<Ticket> findByTicketNumber(String ticketNumber);

    Optional<Ticket> findByQrToken(String qrToken);
}