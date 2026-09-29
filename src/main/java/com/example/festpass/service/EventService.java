package com.example.festpass.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.festpass.entity.Event;
import com.example.festpass.repository.EventRepository;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public Event updateEvent(Long id, Event updatedEvent) {

        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        existingEvent.setEventName(updatedEvent.getEventName());
        existingEvent.setEventDate(updatedEvent.getEventDate());
        existingEvent.setVenue(updatedEvent.getVenue());
        existingEvent.setCapacity(updatedEvent.getCapacity());
        existingEvent.setAvailableTickets(updatedEvent.getAvailableTickets());
        existingEvent.setTicketPrice(updatedEvent.getTicketPrice());
        existingEvent.setActive(updatedEvent.getActive());

        return eventRepository.save(existingEvent);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}