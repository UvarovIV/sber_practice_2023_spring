package ru.sber.services;

import org.springframework.stereotype.Service;
import ru.sber.aspects.NotEmpty;
import ru.sber.models.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class LibraryService implements LibraryServiceInterface {

    private Logger logger = Logger.getLogger(LibraryService.class.getName());
    private List<Book> listOfBooks = new ArrayList<>();

    public LibraryService() {
        listOfBooks.add(new Book("Метро 2033", "Глуховский Д."));
        listOfBooks.add(new Book("Метро 2034", "Глуховский Д."));
        listOfBooks.add(new Book("Метро 2035", "Глуховский Д."));
    }

    @NotEmpty
    public void findBook (String title) {

        logger.info("Книга найдена");
    }

    @NotEmpty
    public void addBooks(List<Book> books) {
        listOfBooks.addAll(books);
        logger.info("Книги добавлены");
    }

    @NotEmpty
    public List<Book> getListOfBooks() {
        logger.info("Возвращаем список имеющихся книг");
        return listOfBooks;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

}
