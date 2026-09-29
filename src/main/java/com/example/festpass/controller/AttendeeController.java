package com.example.festpass.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.festpass.entity.Attendee;
import com.example.festpass.service.AttendeeService;

@RestController
@RequestMapping("/api/attendees")
@CrossOrigin(origins = "*")
public class AttendeeController {

    private final AttendeeService attendeeService;

    public AttendeeController(AttendeeService attendeeService) {
        this.attendeeService = attendeeService;
    }

    @PostMapping
    public Attendee createAttendee(@RequestBody Attendee attendee) {
        return attendeeService.createAttendee(attendee);
    }

    @GetMapping
    public List<Attendee> getAllAttendees() {
        return attendeeService.getAllAttendees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Attendee> getAttendee(@PathVariable Long id) {
        return attendeeService.getAttendeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Attendee updateAttendee(@PathVariable Long id,
                                   @RequestBody Attendee attendee) {
        return attendeeService.updateAttendee(id, attendee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAttendee(@PathVariable Long id) {
        attendeeService.deleteAttendee(id);
        return ResponseEntity.ok("Attendee deleted successfully");
    }
}