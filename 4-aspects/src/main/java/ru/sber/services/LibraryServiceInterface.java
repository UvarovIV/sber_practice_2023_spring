package ru.sber.services;

import ru.sber.models.Book;

import java.util.List;
import java.util.logging.Logger;

public interface LibraryServiceInterface {

    public void findBook (String title);
    public void addBooks(List<Book> books);
    public List<Book> getListOfBooks();
    public void setLogger(Logger logger);
}
