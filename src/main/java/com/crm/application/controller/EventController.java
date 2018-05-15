package com.crm.application.controller;

import com.crm.application.model.Event;
import com.crm.application.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        if (!eventService.getEventById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(eventService.getEventById(id).get(), HttpStatus.OK);
    }

    @GetMapping("/events")
    public ResponseEntity getAllEvents() {
        List<Event> events = eventService.findAllEvent();
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
        if (eventService.getEventById(id).isPresent()) {
            eventService.deleteEvent(id);
            return new ResponseEntity<>(id, HttpStatus.OK);
        }
        return new ResponseEntity<>(id, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/event/edit/{id}")
    public ResponseEntity<Event> editEvent(@PathVariable("id") Long id, @RequestBody Event event, BindingResult bindingResult) {
        if (eventService.getEventById(id).isPresent()) {
            if (bindingResult.hasErrors()) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {
                eventService.updateEvent(id, event);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
