package com.crm.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.crm.application.model.Event;
import com.crm.application.service.EventService;

import java.util.List;

@Controller
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping(value = "/event/{id}")
    public ResponseEntity getEvent(@PathVariable("id") Long id) {
        Event event = eventService.getEventById(id);
        if (event == null) {
            return new ResponseEntity<>("No event found for Id: " + id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @GetMapping("/events")
    public ResponseEntity listAllEvents() {
        List<Event> events = (List<Event>) eventService.findAllEvent();
        if (events.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @PostMapping("/event/{id}")
    public ResponseEntity createEvent(@RequestBody Event event, @PathVariable("id") Long id) {
        if (event.getStart().equals("") || event.getStart() == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        if (event.getEnd().equals("") || event.getEnd() == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            eventService.saveEvent(id, event);
            return new ResponseEntity<>(event, HttpStatus.CREATED);
        }

    }

    @DeleteMapping("/event/{id}")
    public ResponseEntity deleteEvent(@PathVariable("id") Long id) {
        eventService.deleteEvent(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PutMapping("/event/edit/{id}")
    public ResponseEntity<Event> editClient(@PathVariable("id") Long id, @RequestBody Event event) {
        eventService.updateEvent(id, event);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
