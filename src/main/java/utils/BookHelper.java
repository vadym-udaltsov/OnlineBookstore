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
    public static Book getBookWithEmptyValues() {
        return Book.builder()
                .id(0)
                .title(null)
                .description(null)
                .pageCount(0)
                .excerpt(null)
                .build();
    }

    public static int getRandomBookId(List<Book> booksList) {
        return booksList.get(new Faker().number().numberBetween(0, booksList.size())).getId();
    }
}
