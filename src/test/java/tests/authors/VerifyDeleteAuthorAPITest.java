package tests.authors;

import enums.ErrorMessages;
import models.Author;
import models.errors.ErrorResponse;
import org.apache.http.HttpStatus;
import org.assertj.core.api.SoftAssertions;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseAuthorsApiTest;
import utils.AuthorHelper;

public class VerifyDeleteAuthorAPITest extends BaseAuthorsApiTest {

    @Test(description = "Verify that we can delete an existing author")
    public void deleteExistingAuthorTest() {
        var authorsList = client.getAuthorsResponse().jsonPath().getList("", Author.class);
        var existenceAuthor = client.getAuthorByIdResponse(AuthorHelper.getRandomAuthorId(authorsList))
                .as(Author.class);

        var deleteResponse = client.deleteAuthor(existenceAuthor.getId());
        Assert.assertEquals(deleteResponse.getStatusCode(), HttpStatus.SC_OK, "Author should be deleted");

        var checkResponse = client.getAuthorByIdResponse(existenceAuthor.getId());
        var errorModel = checkResponse.as(ErrorResponse.class);

        SoftAssertions.assertSoftly(as -> {
            as.assertThat(checkResponse.getStatusCode())
                    .as(STR."Code Status should be equal to: \{HttpStatus.SC_NOT_FOUND}")
                    .isEqualTo(HttpStatus.SC_NOT_FOUND);
            as.assertThat(errorModel.getTitle())
                    .as("Author should be deleted")
                    .isEqualTo(ErrorMessages.NOT_FOUND_ERROR_MESSAGE.getMessage());
        });
    }

    @Test(description = "Verify that we can delete an created author")
    public void deleteCreatedAuthorTest() {
        var model = AuthorHelper.getAuthorWithRandomValues();

        var postResponse = client.createAuthor(model);
        Assert.assertEquals(postResponse.getStatusCode(), HttpStatus.SC_OK, "Author should be created");
        var createdAuthor = postResponse.as(Author.class);

        var deleteResponse = client.deleteAuthor(createdAuthor.getId());
        Assert.assertEquals(deleteResponse.getStatusCode(), HttpStatus.SC_OK, "Author should be deleted");

        var checkResponse = client.getAuthorByIdResponse(createdAuthor.getId());
        var errorModel = checkResponse.as(ErrorResponse.class);

        SoftAssertions.assertSoftly(as -> {
            as.assertThat(checkResponse.getStatusCode())
                    .as(STR."Code Status should be equal to: \{HttpStatus.SC_NOT_FOUND}")
                    .isEqualTo(HttpStatus.SC_NOT_FOUND);
            as.assertThat(errorModel.getTitle())
                    .as("Author should be deleted")
                    .isEqualTo(ErrorMessages.NOT_FOUND_ERROR_MESSAGE.getMessage());
        });
    }

    @Test(description = "Verify trying to delete a non-existent author")
    public void deleteNonExistentAuthorTest() {
        var authorsList = client.getAuthorsResponse().jsonPath().getList("", Author.class);
        var lastId = authorsList.stream()
                .map(Author::getId)
                .max(Long::compare)
                .orElseThrow();

        var deleteResponse = client.deleteAuthor(lastId + faker.number().numberBetween(100, 200));

        Assert.assertEquals(deleteResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND,
                "Cannot delete author with non-existent Id");
    }

    @Test(description = "Verify trying to delete a author with negative id")
    public void deleteAuthorWithNegativeIdTest() {
        var negativeId = Integer.parseInt(STR."-\{faker.number().numberBetween(1, 5)}");
        var deleteResponse = client.deleteAuthor(negativeId);

        Assert.assertEquals(deleteResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND,
                "Cannot delete author with negative Id");
    }

    @Test(description = "Verify trying to delete a author after it has already been deleted")
    public void deleteAlreadyDeletedAuthorTest() {
        var authorsList = client.getAuthorsResponse().jsonPath().getList("", Author.class);
        var existenceAuthor = client.getAuthorByIdResponse(AuthorHelper.getRandomAuthorId(authorsList))
                .as(Author.class);

        var deleteResponse = client.deleteAuthor(existenceAuthor.getId());
        Assert.assertEquals(deleteResponse.getStatusCode(), HttpStatus.SC_NO_CONTENT, "Author should be deleted");

        var secondDeleteResponse = client.deleteAuthor(existenceAuthor.getId());

        Assert.assertEquals(secondDeleteResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND,
                "Cannot delete book if author has already been deleted");

    }
}
