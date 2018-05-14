package com.crm.application.service;

import com.crm.application.model.Event;

public interface EventService {

    Iterable<Event> findAllEvent();

    Event getEventById(Long id);

    void saveEvent(Long id, Event event);

    void updateEvent(long id, Event event);

    void deleteEvent(Long id);
}
