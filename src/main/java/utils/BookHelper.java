package utils;

import com.github.javafaker.Faker;
import models.Book;

import java.util.List;

public abstract class BookHelper {

    private BookHelper() {}

    public static Integer getRandomID(List<Book> booksList) {
        var idsList = booksList.stream()
                .map(Book::getId)
                .toList();
        return idsList.get(new Faker().number().numberBetween(0, idsList.size()));
    }
}
