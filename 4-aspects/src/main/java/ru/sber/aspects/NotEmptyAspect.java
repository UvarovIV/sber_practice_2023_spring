package ru.sber.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import ru.sber.exceptions.ArgumentIsNullException;
import ru.sber.exceptions.CollectionIsEmptyException;
import ru.sber.exceptions.StringIsEmptyException;

import java.util.Collection;
import java.util.logging.Logger;

@Aspect
public class NotEmptyAspect {
    private Logger logger = Logger.getLogger(NotEmptyAspect.class.getName());

    /**
     * Аспект для проверки аргументов методов на NULL и пустые значения
     */
    @Before("@annotation(NotEmpty) && (args(java.util.Collection<?>,..) || args(String,..))" )
    public void checkArguments(JoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();

        for (Object argument: args) {
            if (argument == null) {
                logger.severe("Аргумент является null");
                throw new ArgumentIsNullException("Argument is NULL");
            } else if (argument instanceof Collection<?>) {
                if (((Collection<?>) argument).isEmpty()) {
                    logger.severe("Коллекция пуста");
                    throw new CollectionIsEmptyException("Collection is empty");
                }
            } else if (argument instanceof String) {
                if (((String) argument).isEmpty()) {
                    logger.severe("Строка пуста");
                    throw new StringIsEmptyException("String is empty");
                }
            }
        }

        logger.info("Все аргументы норм");

    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
