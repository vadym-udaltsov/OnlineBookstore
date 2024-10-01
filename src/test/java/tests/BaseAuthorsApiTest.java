package tests;

import http.AuthorClient;
import com.github.javafaker.Faker;

public class BaseAuthorsApiTest extends BaseApiTest<AuthorClient> {
    public BaseAuthorsApiTest() {
        client = new AuthorClient();
        faker = new Faker();
    }
}
