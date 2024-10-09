package utils;

import com.github.javafaker.Faker;
import http.BookClient;
import io.restassured.response.Response;
import models.Book;

import java.util.List;

public class BookHelper {

    private static final BookClient BOOK_CLIENT = new BookClient();

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

    public static List<Book> getBooksList(Response response) {
        return response.jsonPath()
                .getList("", Book.class);
    }
    public static List<Book> getBooksList() {
        return BOOK_CLIENT.getBooksResponse().jsonPath()
                .getList("", Book.class);
    }

    public static Book getBookById() {
        var randomBookId = BookHelper.getRandomBookId(getBooksList());
        return BOOK_CLIENT.getBookByIdResponse(randomBookId)
                .as(Book.class);
    }
}
