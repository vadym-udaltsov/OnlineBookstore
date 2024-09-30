package tests;

import client.AuthorClient;
import com.github.javafaker.Faker;

public class BaseAuthorsApiTest extends BaseApiTest<AuthorClient> {
    public BaseAuthorsApiTest() {
        client = new AuthorClient();
        faker = new Faker();
    }
}
