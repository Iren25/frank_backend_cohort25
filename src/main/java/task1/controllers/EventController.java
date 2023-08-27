package task1.controllers;

import task1.models.Event;
import task1.services.EventsService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class EventController {

    private final Scanner scanner;

    private final EventsService eventsService;

    public EventController(Scanner scanner, EventsService eventsService) {
        this.scanner = scanner;
        this.eventsService = eventsService;
    }

    public void addEvent() {

        System.out.println("Введите название мероприятия");
        String title = scanner.nextLine();

        System.out.println("Введите дату начала");
        LocalDate startDate = LocalDate.parse(scanner.nextLine());

        System.out.println("Введите дату окончания");
        LocalDate endDate = LocalDate.parse(scanner.nextLine());

        Event event = eventsService.addEvent(title, startDate, endDate);

        System.out.println(event);
    }

    public void getAllEvents() { // метод для получения всех пользователей
        List<Event> events = eventsService.getAllEvents(); // запрашиваем у бизнес-логики всех пользователей

        System.out.println(events); // выводим в консоль
    }
}
