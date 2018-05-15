package com.crm.application.service;

import com.crm.application.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {

    List<Event> findAllEvent();

    Optional<Event> getEventById(Long id);

    void saveEvent(Long id, Event event);

    void updateEvent(long id, Event event);

    void deleteEvent(Long id);
}
