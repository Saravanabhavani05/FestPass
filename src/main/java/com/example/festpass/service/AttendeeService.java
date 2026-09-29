package com.example.festpass.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.festpass.entity.Attendee;
import com.example.festpass.repository.AttendeeRepository;

@Service
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;

    public AttendeeService(AttendeeRepository attendeeRepository) {
        this.attendeeRepository = attendeeRepository;
    }

    public Attendee createAttendee(Attendee attendee) {
        return attendeeRepository.save(attendee);
    }

    public List<Attendee> getAllAttendees() {
        return attendeeRepository.findAll();
    }

    public Optional<Attendee> getAttendeeById(Long id) {
        return attendeeRepository.findById(id);
    }

    public Attendee updateAttendee(Long id, Attendee updatedAttendee) {

        Attendee existingAttendee = attendeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendee not found"));

        existingAttendee.setName(updatedAttendee.getName());
        existingAttendee.setEmail(updatedAttendee.getEmail());
        existingAttendee.setPhone(updatedAttendee.getPhone());
        existingAttendee.setCollege(updatedAttendee.getCollege());

        return attendeeRepository.save(existingAttendee);
    }

    public void deleteAttendee(Long id) {
        attendeeRepository.deleteById(id);
    }
}