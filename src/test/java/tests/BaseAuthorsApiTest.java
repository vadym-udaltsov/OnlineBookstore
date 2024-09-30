package tests;

import client.AuthorClient;

public class BaseAuthorsApiTest extends BaseApiTest<AuthorClient> {
    public BaseAuthorsApiTest() {
        client = new AuthorClient();
    }
}
