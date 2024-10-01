package tests.authors;

import models.Author;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseAuthorsApiTest;
import utils.AuthorHelper;
import utils.provider.AuthorDataProvider;

public class VerifyPutAuthorAPITest extends BaseAuthorsApiTest {

    @Test(description = "Verify that we can update an existing author", dataProvider = "updateAuthorDataProvider",
            dataProviderClass = AuthorDataProvider.class)
    public void updateExistingAuthorTest(Author author, int expectedStatusCode, String message) {
        var authorsList = client.getAuthorsResponse().jsonPath()
                .getList("", Author.class);
        var randomId = AuthorHelper.getRandomAuthorId(authorsList);

        var putResponse = client.updateAuthor(randomId, author);

        Assert.assertEquals(putResponse.getStatusCode(), expectedStatusCode, message);
    }

    @Test(description = "Verify updating a non-existent author")
    public void updateNonExistentAuthorTest() {
        var authorsList = client.getAuthorsResponse().jsonPath().getList("", Author.class);
        var lastId = authorsList.stream()
                .map(Author::getId)
                .max(Long::compare)
                .orElseThrow();
        var model = AuthorHelper.getAuthorWithRandomValues();

        var putResponse = client.updateAuthor(lastId + faker.number().numberBetween(100, 200), model);

        Assert.assertEquals(putResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND, "Author doesn't exist");
    }

    @Test(description = "Verify updating a author by negative ID")
    public void updateAuthorByNegativeIdTest() {
        var author = AuthorHelper.getAuthorWithRandomValues();
        var negativeId = Integer.parseInt(STR."-\{faker.number().numberBetween(1, 5)}");

        var putResponse = client.updateAuthor(negativeId, author);

        Assert.assertEquals(putResponse.getStatusCode(), HttpStatus.SC_BAD_REQUEST,
                "Cannot update author by negative Id");
    }
}
