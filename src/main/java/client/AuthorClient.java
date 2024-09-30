package client;

import io.restassured.response.Response;
import models.Author;

import java.util.List;

public class AuthorClient {
    private final OnlineBookstoreClient client;
    private final static String AUTHORS_ENDPOINT = "Authors";

    public AuthorClient() {
        client = new OnlineBookstoreClient();
    }

    public List<Author> getAuthorsList() {
        return client.get(AUTHORS_ENDPOINT)
                .jsonPath()
                .getList("", Author.class);
    }

    public Author getAuthorById(int id) {
        return client.get(STR."\{AUTHORS_ENDPOINT}/\{id}").as(Author.class);
    }

    public Author addAuthor(Author author) {
        return client.post(AUTHORS_ENDPOINT, author, Author.class);
    }

    public Author updateAuthorInfo(int id, Author author) {
        return client.put(STR."\{AUTHORS_ENDPOINT}/\{id}", author, Author.class);

    }

    public Response deleteAuthor(int id) {
        return client.delete(STR."\{AUTHORS_ENDPOINT}/\{id}");
    }
}
