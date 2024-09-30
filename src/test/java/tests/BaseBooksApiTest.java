package tests;

import client.BookClient;

public class BaseBooksApiTest extends BaseApiTest<BookClient> {
    public BaseBooksApiTest() {
        client = new BookClient();
    }
}
