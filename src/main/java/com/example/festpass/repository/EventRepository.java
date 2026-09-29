package com.example.festpass.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.festpass.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

}