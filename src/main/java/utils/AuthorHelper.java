package utils;

import com.github.javafaker.Faker;
import http.AuthorClient;
import io.restassured.response.Response;
import models.Author;

import java.util.List;

public class AuthorHelper {

    private static final AuthorClient AUTHOR_CLIENT = new AuthorClient();


    private AuthorHelper() {
    }

    public static Author getAuthorWithRandomValidValues() {
        return Author.builder().build();
    }

    public static Author getAuthorWithEmptyValues() {
        return Author.builder()
                .id(0)
                .idBook(0)
                .firstName(null)
                .lastName(null)
                .build();
    }

    public static int getRandomAuthorId(List<Author> authorsList) {
        return authorsList.get(new Faker().number().numberBetween(0, authorsList.size())).getId();
    }

    public static List<Author> getAuthorsList(Response response) {
        return response.jsonPath()
                .getList("", Author.class);
    }

    public static List<Author> getAuthorsList() {
        return AUTHOR_CLIENT.getAuthorsResponse().jsonPath()
                .getList("", Author.class);
    }
}
