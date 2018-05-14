package com.crm.application.service.serviceImpl;

import com.crm.application.repository.EventRepository;
import com.crm.application.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.crm.application.model.Event;
import com.crm.application.service.EventService;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Iterable<Event> findAllEvent() {
        return eventRepository.findAll();
    }

    @Override
    public Event getEventById(Long id) {
        return eventRepository.findById(id);
    }

    @Override
    public void saveEvent(Long id, Event event) {

        event.setUser(userRepository.findOne(id));
        eventRepository.save(event);
    }

    @Override
    public void updateEvent(long id, Event event) {
        Event temp = eventRepository.findById(id);
        temp.setStart(event.getStart());
        temp.setEnd(event.getEnd());
        temp.setTitle(event.getTitle());
        temp.setColor(event.getColor());
        eventRepository.save(temp);
    }


    @Override
    public void deleteEvent(Long id) {
        eventRepository.delete(id);
    }


}
