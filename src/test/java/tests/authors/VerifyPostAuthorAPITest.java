package tests.authors;

import client.BookClient;
import models.Author;
import models.Book;
import org.apache.http.HttpStatus;
import org.assertj.core.api.SoftAssertions;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseAuthorsApiTest;
import utils.AuthorHelper;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

public class VerifyPostAuthorAPITest extends BaseAuthorsApiTest {

    @Test(description = "Verify that we can add a new author")
    public void createAuthorTest() {
        var model = AuthorHelper.getAuthorWithRandomValues();

        var postResponse = client.createAuthor(model);
        var createdAuthor = postResponse.as(Author.class);

        SoftAssertions.assertSoftly(as -> {
            as.assertThat(postResponse.getStatusCode())
                    .as(STR."Code Status should be equal to: \{HttpStatus.SC_OK}")
                    .isEqualTo(HttpStatus.SC_OK);
            as.assertThat(createdAuthor)
                    .as("Created author should not be null")
                    .isNotNull();
            as.assertThat(createdAuthor.getId())
                    .as("Created author should have a valid ID")
                    .isNotZero();
            as.assertThat(createdAuthor.getIdBook())
                    .as("Created author should have a valid Book Id")
                    .isNotZero();
            as.assertThat(createdAuthor.getFirstName())
                    .as("Created author should have First Name")
                    .isNotEmpty();
            as.assertThat(createdAuthor.getLastName())
                    .as("Created author should have Last Name")
                    .isNotEmpty();
        });
    }

    @Test(description = "Verify that creating a book with missing fields")
    public void createAuthorWithMissingFieldsTest() {
        var invalidAuthor = Author.builder()
                .id(0)
                .idBook(0)
                .firstName(null)
                .lastName(null)
                .build();

        var postResponse = client.createAuthor(invalidAuthor);

        Assert.assertEquals(postResponse.getStatusCode(), SC_BAD_REQUEST, "Author shouldn't have empty fields");
    }

    @Test(description = "Verify that creating a book with a negative Id")
    public void createAuthorWithNegativeIdTest() {
        var invalidAuthor = AuthorHelper.getAuthorWithRandomValues();
        var negativeNumber = Integer.parseInt(STR."-\{faker.number().numberBetween(1, 5)}");

        invalidAuthor.setId(negativeNumber);

        var postResponse = client.createAuthor(invalidAuthor);

        Assert.assertEquals(postResponse.getStatusCode(), HttpStatus.SC_BAD_REQUEST,
                "Should not be able to create an author with negative Id");
    }

    @Test(description = "Verify that creating a book with a negative Book Id")
    public void createAuthorWithNegativeBookIdTest() {
        var invalidAuthor = AuthorHelper.getAuthorWithRandomValues();
        var negativeNumber = Integer.parseInt(STR."-\{faker.number().numberBetween(1, 5)}");

        invalidAuthor.setIdBook(negativeNumber);

        var postResponse = client.createAuthor(invalidAuthor);

        Assert.assertEquals(postResponse.getStatusCode(), HttpStatus.SC_BAD_REQUEST,
                "Should not be able to create an author with negative Book Id");
    }

    @Test(description = "Verify that creating a book with a non-existence Book Id")
    public void createAuthorWithNonExistenceBookIdTest() {
        var booksList = new BookClient().getBooksResponse().jsonPath().getList("", Book.class);
        var lastId = booksList.stream()
                .map(Book::getId)
                .max(Long::compare)
                .orElseThrow();

        var invalidAuthor = AuthorHelper.getAuthorWithRandomValues();
        invalidAuthor.setIdBook(lastId + faker.number().numberBetween(100, 200));

        var postResponse = client.createAuthor(invalidAuthor);

        Assert.assertEquals(postResponse.getStatusCode(), HttpStatus.SC_BAD_REQUEST,
                "Should not be able to create an author with non-existence Book Id");
    }
}
