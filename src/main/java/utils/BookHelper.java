package utils;

import com.github.javafaker.Faker;
import models.Book;

import java.util.List;

public class BookHelper {

    private BookHelper() {
    }

    public static Book getBookWithRandomValues() {
        return Book.builder().build();
    }

    public static int getRandomBookId(List<Book> booksList) {
        return booksList.get(new Faker().number().numberBetween(0, booksList.size())).getId();
    }
}
