package task1;

import task1.controllers.EventController;
import task1.repositories.EventsRepository;
import task1.repositories.impl.EventsRepositoryFileImpl;
import task1.repositories.impl.EventsRepositoryListImpl;
import task1.services.EventsService;
import task1.services.impl.EventsServiceImpl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EventsRepository eventsRepositoryList = new EventsRepositoryListImpl();
        EventsRepository eventsRepositoryFile = new EventsRepositoryFileImpl("events.txt");
        EventsService eventsService = new EventsServiceImpl(eventsRepositoryFile);
        EventController eventController = new EventController(scanner, eventsService);

        boolean isRun = true;

        while (isRun) {
            String command = scanner.nextLine();

            switch (command) {
                case "/addEvent" ->
                        eventController.addEvent();
                case "/events" ->
                        eventController.getAllEvents();
                case "/exit" -> isRun = false;
            }
        }
    }
}
