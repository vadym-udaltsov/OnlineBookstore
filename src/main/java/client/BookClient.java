package client;

import io.restassured.response.Response;
import models.Book;

import java.util.List;

import static java.lang.StringTemplate.STR;

public class BookClient {
    private final OnlineBookstoreClient client;
    private final static String BOOKS_ENDPOINT = "Books";

    public BookClient() {
        client = new OnlineBookstoreClient();
    }

    public List<Book> getBooksList() {
        return client.get(BOOKS_ENDPOINT)
                .jsonPath()
                .getList("", Book.class);
    }

    public Book getBookById(int id) {
        return client.get(STR."\{BOOKS_ENDPOINT}/\{id}").as(Book.class);
    }

    public Book createBook(Book book) {
        return client.post(BOOKS_ENDPOINT, book, Book.class);
    }

    public Book updateBook(int id, Book book) {
        return client.put(STR."\{BOOKS_ENDPOINT}/\{id}", book, Book.class);

    }

    public Response deleteBook(int id) {
        return client.delete(STR."\{BOOKS_ENDPOINT}/\{id}");
    }
}
