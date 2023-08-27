package task1.repositories.impl;

import task1.models.Event;
import task1.repositories.EventsRepository;

import java.util.ArrayList;
import java.util.List;

public class EventsRepositoryListImpl implements EventsRepository {

    private final List<Event> events = new ArrayList<>();
    private Long generatedId = 1L;

    @Override
    public Event findByID(Long id) {
        return null;
    }

    @Override
    public List<Event> findAll() {
        return null;
    }

    @Override
    public void save(Event event) {
        events.add(event);
        event.setId(generatedId);
        generatedId++;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void update(Event model) {

    }
}
