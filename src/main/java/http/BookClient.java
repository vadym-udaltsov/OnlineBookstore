package http;

import io.restassured.response.Response;
import models.Book;

import static java.lang.StringTemplate.STR;

public class BookClient {
    private final BaseClient client;
    private final static String BOOKS_ENDPOINT = "/api/v1/Books";

    public BookClient() {
        client = new BaseClient();
    }

    public Response getBooksResponse() {
        return client.get(BOOKS_ENDPOINT);
    }

    public Response getBookByIdResponse(int id) {
        return client.get(STR."\{BOOKS_ENDPOINT}/\{id}");
    }

    public Response createBook(Book book) {
        return client.post(BOOKS_ENDPOINT, book);
    }

    public Response updateBook(int id, Book book) {
        return client.put(STR."\{BOOKS_ENDPOINT}/\{id}", book);

    }

    public Response deleteBook(int id) {
        return client.delete(STR."\{BOOKS_ENDPOINT}/\{id}");
    }
}
