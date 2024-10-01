package client;

import io.restassured.response.Response;
import models.Author;

public class AuthorClient {
    private final BaseClient client;
    private final static String AUTHORS_ENDPOINT = "Authors";

    public AuthorClient() {
        client = new BaseClient();
    }

    public Response getAuthorsResponse() {
        return client.get(AUTHORS_ENDPOINT);
    }
    public Response getAuthorsByBookIdResponse(int bookId) {
        return client.get(AUTHORS_ENDPOINT.concat(STR."/authors/books/\{bookId}"));
    }

    public Response getAuthorByIdResponse(int id) {
        return client.get(STR."\{AUTHORS_ENDPOINT}/\{id}");
    }

    public Response createAuthor(Author author) {
        return client.post(AUTHORS_ENDPOINT, author);
    }

    public Response updateAuthor(int id, Author author) {
        return client.put(STR."\{AUTHORS_ENDPOINT}/\{id}", author);

    }

    public Response deleteAuthor(int id) {
        return client.delete(STR."\{AUTHORS_ENDPOINT}/\{id}");
    }
}
