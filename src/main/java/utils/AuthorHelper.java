package utils;

import com.github.javafaker.Faker;
import models.Author;

import java.util.List;

public class AuthorHelper {

    private AuthorHelper() {
    }

    public static Author getAuthorWithRandomValues() {
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
}
