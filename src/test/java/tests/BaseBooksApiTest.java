package tests;

import http.BookClient;
import com.github.javafaker.Faker;

public class BaseBooksApiTest extends BaseApiTest<BookClient> {
    public BaseBooksApiTest() {
        client = new BookClient();
        faker = new Faker();
    }
}
