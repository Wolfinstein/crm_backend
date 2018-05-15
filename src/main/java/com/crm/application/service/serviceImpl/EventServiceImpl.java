package com.crm.application.service.serviceImpl;

import com.crm.application.model.Event;
import com.crm.application.repository.EventRepository;
import com.crm.application.repository.UserRepository;
import com.crm.application.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Event> findAllEvent() {
        return eventRepository.findAll();
    }

    @Override
    public Optional<Event> getEventById(Long id) {
        return Optional.ofNullable(eventRepository.findById(id));
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
