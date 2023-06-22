package ru.sber.services;

import ru.sber.models.Book;

import java.util.List;
import java.util.logging.Logger;

/**
 * Сервис для библиотеки
 */
public interface LibraryServiceInterface {

    /**
     * Происходит поиск книги
     * @param title - название книги
     */
    public void findBook (String title);

    /**
     * Добавляет новые книги в список имеющихся
     * @param books - список добавляемых книг
     */
    public void addBooks(List<Book> books);

    /**
     * Возвращает список всех имеющихся книг
     * @return список всех имеющихся книг
     */
    public List<Book> getListOfBooks();

    /**
     * Добавляет логгер сервису
     * @param logger - логгер сервиса
     */
    public void setLogger(Logger logger);
}
