package task1.services;

import task1.models.Event;

import java.time.LocalDate;
import java.util.List;

public interface EventsService {
    Event addEvent(String title, LocalDate startDate, LocalDate endDate);

    List<Event> getAllEvents();
}
