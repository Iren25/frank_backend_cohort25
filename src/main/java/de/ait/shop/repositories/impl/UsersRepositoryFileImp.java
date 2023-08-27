package de.ait.shop.repositories.impl;

import de.ait.shop.models.User;
import de.ait.shop.repositories.UsersRepository;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class UsersRepositoryFileImp implements UsersRepository {

    private final String fileName;

    private Long generatedId = 1L;

    public UsersRepositoryFileImp(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) { // открываем файл для чтения
            return reader.lines() // получаем все строки из файла
                    .map(line -> line.split("\\|")) // разбиваем каждую строку на массив из нескольких строк по разделителю
                    .map(parsed -> new User(Long.parseLong(parsed[0]), parsed[1], parsed[2]))// преобразовали в пользователя
                    .collect(Collectors.toList()); // преобразовали в список
        } catch (IOException e) { // если была ошибка с файлом, сообщаем об этом и останавливаем приложение
            throw new IllegalArgumentException("Проблемы с чтением из файла: " + e.getMessage());
        }
    }

    @Override
    public void save(User user) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) { // открываем файл для дозаписи

            user.setId(generatedId); // присваиваем id пользователю

            writer.write(user.getId() + "|" + user.getEmail() + "|" + user.getPassword()); // записываем его в файл
            writer.newLine(); // создаем новую строку

        } catch (IOException e) { // если была ошбка с файлом, сообщаем об этом и останавливаем приложение
            throw new IllegalArgumentException("Проблемы с записью в файл: " + e.getMessage());
        }
        // если до этого все прошло хорошо - генерируем новый id, который присвоим следующему пользователю
        generatedId++;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void update(User model) {

    }

    @Override
    public User findOneByEmail(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) { // открываем файл для чтения
           return reader.lines() // получаем все строки из файла
                    .map(line -> line.split("\\|")) // разбиваем каждую строку на массив из нескольких строк по разделителю
                    .filter(parsed -> parsed[1].equals(email)) // находим строку с таким же email
                    .findFirst() // берем первую строку, которая нам подошла
                    .map(parsed -> new User(Long.parseLong(parsed[0]), parsed[1], parsed[2]))// преобразовали в пользователя
                   .orElse(null);

        } catch (IOException e) { // если была ошибка с файлом, сообщаем об этом и останавливаем приложение
            throw new IllegalArgumentException("Проблемы с чтением из файла: " + e.getMessage());
        }
    }
}

