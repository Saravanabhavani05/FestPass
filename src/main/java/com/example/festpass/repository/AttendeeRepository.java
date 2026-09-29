package com.example.festpass.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.festpass.entity.Attendee;

public interface AttendeeRepository extends JpaRepository<Attendee, Long> {

}