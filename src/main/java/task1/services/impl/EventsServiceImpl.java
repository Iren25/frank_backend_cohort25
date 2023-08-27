package task1.services.impl;

import de.ait.shop.models.User;
import task1.models.Event;
import task1.repositories.EventsRepository;
import task1.services.EventsService;

import java.time.LocalDate;
import java.util.List;

public class EventsServiceImpl implements EventsService {
    private final EventsRepository eventsRepository;

    public EventsServiceImpl(EventsRepository eventsRepository) {
        this.eventsRepository = eventsRepository;
    }

    public Event addEvent(String title,  LocalDate startDate, LocalDate endDate) {
        if (title == null || title.equals("") || title.equals(" ")) {
            throw new IllegalArgumentException("Название не может быть пустым");
        }

        if (endDate.isBefore(startDate)) {
            throw new RuntimeException("Дата начала не может быть позднее даты завершения");
        }

        Event event = new Event(title, startDate, endDate); // создаем пользователя

        eventsRepository.save(event); // сохраняем пользователя в хранилище

        return event; // возвращаем пользователя как результат
    }

    @Override
    public List<Event> getAllEvents() {
        return eventsRepository.findAll();
    }
}
