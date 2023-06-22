package ru.sber;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.sber.aspects.NotEmptyAspect;
import ru.sber.config.ProjectConfig;
import ru.sber.exceptions.ArgumentIsNullException;
import ru.sber.exceptions.StringIsEmptyException;
import ru.sber.models.Book;
import ru.sber.services.LibraryServiceInterface;

import java.util.List;
import java.util.logging.Logger;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ProjectConfig.class })
public class LibraryServiceTests {

    private Logger serviceLogger;
    private Logger notEmptyLogger;

    @Autowired
    private NotEmptyAspect notEmptyAspect;
    @Autowired
    private LibraryServiceInterface libraryService;

    @BeforeEach
    public void before() {
        this.notEmptyLogger = mock(Logger.class);
        notEmptyAspect.setLogger(notEmptyLogger);

        this.serviceLogger = mock(Logger.class);
        libraryService.setLogger(serviceLogger);
    }

    @Test
    @DisplayName("Тестирование метода поиска книги с пустой строкой")
    public void testFindBooksMethodWithEmptyString() {
        Assertions.assertThrows(StringIsEmptyException.class, () -> {
            libraryService.findBook("");
        });
        verify(notEmptyLogger).severe("Строка пуста");
    }

    @Test
    @DisplayName("Тестирование метода поиска книги с нормальными аргументами")
    public void testFindBooksMethod() {
        libraryService.findBook("Метро 2033");
        verify(notEmptyLogger).info("Все аргументы норм");
    }

    @Test
    @DisplayName("Тестирование метода добавления книги в список с нормальными аргументами")
    public void testAddBooksMethod() {
        List books = List.of(
                new Book("Метро 2033", "Глуховский Д."),
                new Book("Метро 2033", "Глуховский Д."),
                new Book("Метро 2033", "Глуховский Д."),
                new Book("Метро 2033", "Глуховский Д.")
        );

        libraryService.addBooks(books);
        verify(notEmptyLogger).info("Все аргументы норм");
        verify(serviceLogger).info("Книги добавлены");

    }

    @Test
    @DisplayName("Тестирование метода добавления книги в список с NULL аргументом")
    public void testAddBooksMethodWithNullArgument() {

        Assertions.assertThrows(ArgumentIsNullException.class, () -> {
            libraryService.addBooks(null);
        });
        verify(notEmptyLogger).severe("Аргумент является null");

    }

    @Test
    @DisplayName("Тестирование метода получения списка книг")
    public void testGetListOfBooksMethod() {
        libraryService.getListOfBooks();
        verify(serviceLogger).info("Возвращаем список имеющихся книг");
    }
}
