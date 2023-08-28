package task1.repositories.impl;

import de.ait.shop.models.User;
import task1.models.Event;
import task1.repositories.EventsRepository;

import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class EventsRepositoryFileImpl implements EventsRepository {

    private final String fileName;

    private Long generatedId = 1L;

    public EventsRepositoryFileImpl(String fileName) {
        this.fileName = fileName;
    }


    @Override
    public Event findByID(Long id) {
        return null;
    }

    @Override
    public List<Event> findAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            return reader.lines()
                    .map(line -> line.split("\\|"))
                    .map(parsed -> new Event(Long.parseLong(parsed[0]), parsed[1], LocalDate.parse(parsed[2]), LocalDate.parse(parsed[3])))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalArgumentException("Проблемы с чтением из файла: " + e.getMessage());
        }
    }

    @Override
    public void save(Event event) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {

            event.setId(generatedId);

            writer.write(event.getId() + "|" + event.getTitle() + "|" + event.getStartDate() + "|" + event.getEndDate());
            writer.newLine(); // создаем новую строку

        } catch (IOException e) {
            throw new IllegalArgumentException("Проблемы с записью в файл: " + e.getMessage());
        }
        generatedId++;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void update(Event model) {

    }

    @Override
    public Event findOneByTitle(String title) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) { // открываем файл для чтения
            return reader.lines() // получаем все строки из файла
                    .map(line -> line.split("\\|")) // разбиваем каждую строку на массив из нескольких строк по разделителю
                    .filter(parsed -> parsed[1].equals(title))
                    .findFirst()
                    .map(parsed -> new Event(Long.parseLong(parsed[0]), parsed[1], LocalDate.parse(parsed[2]), LocalDate.parse(parsed[3])))
                    .orElse(null);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
