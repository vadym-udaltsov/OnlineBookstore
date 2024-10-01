package tests.authors;

import models.Author;
import org.apache.http.HttpStatus;
import org.assertj.core.api.SoftAssertions;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseAuthorsApiTest;
import utils.AuthorHelper;
import utils.JsonUtils;

public class VerifyPutAuthorAPITest extends BaseAuthorsApiTest {

    @Test(description = "Verify that we can update an existing author")
    public void updateExistingAuthorTest() {
        var model = AuthorHelper.getAuthorWithRandomValues();

        var postResponse = client.createAuthor(model);
        Assert.assertEquals(postResponse.getStatusCode(), HttpStatus.SC_OK, "Author should be created");
        var createdAuthor = postResponse.as(Author.class);

        var newAuthor = AuthorHelper.getAuthorWithRandomValues();

        var putResponse = client.updateAuthor(createdAuthor.getId(), newAuthor);
        var updatedAuthor = putResponse.as(Author.class);

        SoftAssertions.assertSoftly(as -> {
            as.assertThat(putResponse.getStatusCode())
                    .as(STR."Status Code should be equal to \{HttpStatus.SC_OK}")
                    .isEqualTo(HttpStatus.SC_OK);
            as.assertThat(createdAuthor)
                    .as(STR."Author should be updated: \{updatedAuthor}")
                    .isNotEqualTo(updatedAuthor);
        });
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


    @Test(description = "Verify updating only one field of the author is successful")
    public void updateSingleFieldTest() {
        var authorsList = client.getAuthorsResponse().jsonPath().getList("", Author.class);
        var randomAuthorId = AuthorHelper.getRandomAuthorId(authorsList);

        var existingAuthor = client.getAuthorByIdResponse(randomAuthorId).as(Author.class);

        var newFirstName = STR."New \{faker.name().firstName()}";

        var updatedModel = Author.builder()
                .id(existingAuthor.getId())
                .idBook(existingAuthor.getIdBook())
                .firstName(newFirstName)
                .lastName(existingAuthor.getLastName())
                .build();

        var putResponse = client.updateAuthor(existingAuthor.getId(), updatedModel);
        var updatedAuthor = client.getAuthorByIdResponse(existingAuthor.getId()).as(Author.class);

        SoftAssertions.assertSoftly(as -> {
            as.assertThat(putResponse.getStatusCode())
                    .as(STR."Status Code should be equal to \{HttpStatus.SC_OK}")
                    .isEqualTo(HttpStatus.SC_OK);
            as.assertThat(updatedAuthor.getFirstName())
                    .as(STR."Author First Name should be updated: \{newFirstName}")
                    .isEqualTo(newFirstName);
            as.assertThat(updatedAuthor)
                    .as("Author should be updated in the DB")
                    .isEqualTo(existingAuthor);
        });
    }

    @Test(description = "Verify updating a author by negative ID")
    public void updateAuthorByNegativeIdTest() {
        var model = AuthorHelper.getAuthorWithRandomValues();
        var negativeId = Integer.parseInt(STR."-\{faker.number().numberBetween(1, 5)}");

        var putResponse = client.updateAuthor(negativeId, model);

        Assert.assertEquals(putResponse.getStatusCode(), HttpStatus.SC_BAD_REQUEST,
                "Cannot update author by negative Id");
    }

    @Test(description = "Verify updating a author with negative ID")
    public void updateAuthorWithNegativeIdTest() {
        var model = AuthorHelper.getAuthorWithRandomValues();
        var authorsList = JsonUtils.jsonToObjectsList(client.getAuthorsResponse(), Author.class);
        var randomId = AuthorHelper.getRandomAuthorId(authorsList);

        var negativeId = Integer.parseInt(STR."-\{faker.number().numberBetween(1, 5)}");
        model.setId(negativeId);

        var putResponse = client.updateAuthor(randomId, model);

        Assert.assertEquals(putResponse.getStatusCode(), HttpStatus.SC_BAD_REQUEST,
                "Cannot update author with negative Id");
    }

    @Test(description = "Verify updating a author with negative Book ID")
    public void updateAuthorWithNegativeBookIdTest() {
        var model = AuthorHelper.getAuthorWithRandomValues();
        var authorsList = JsonUtils.jsonToObjectsList(client.getAuthorsResponse(), Author.class);
        var randomId = AuthorHelper.getRandomAuthorId(authorsList);

        var negativeId = Integer.parseInt(STR."-\{faker.number().numberBetween(1, 5)}");
        model.setIdBook(negativeId);

        var putResponse = client.updateAuthor(randomId, model);

        Assert.assertEquals(putResponse.getStatusCode(), HttpStatus.SC_BAD_REQUEST,
                "Cannot update author with negative  Book Id");
    }
}
