package ru.sber.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sber.config.ProjectConfig;
import ru.sber.models.Book;
import ru.sber.services.LibraryServiceInterface;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        var libraryService = context.getBean(LibraryServiceInterface.class);

        List books = List.of(
                new Book("Оно", "Стивен Кинг"),
                new Book("Шерлок Холмс", "Артур Конан Дойль")
        );

        libraryService.addBooks(books);
        libraryService.getListOfBooks().forEach((book) -> System.out.println(book.getTitle()));
    }
}
