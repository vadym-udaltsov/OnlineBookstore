package tests.authors;

import enums.ErrorMessages;
import http.BookClient;
import models.Author;
import models.Book;
import models.errors.ErrorResponse;
import org.apache.http.HttpStatus;
import org.assertj.core.api.SoftAssertions;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseAuthorsApiTest;
import utils.AuthorHelper;
import utils.BookHelper;

import java.util.stream.IntStream;

public class VerifyGetAuthorAPITest extends BaseAuthorsApiTest {

    @Test(description = "Verify that we can get list with all authors")
    public void getAuthorsListTest() {
        var response = client.getAuthorsResponse();
        var deserializedAuthorsList = response.jsonPath()
                .getList("", Author.class);

        SoftAssertions.assertSoftly(as -> {
            as.assertThat(response.getStatusCode())
                    .as(STR."Code Status should be equal to: \{HttpStatus.SC_OK}")
                    .isEqualTo(HttpStatus.SC_OK);
            as.assertThat(deserializedAuthorsList)
                    .as("Authors List should not be empty")
                    .isNotEmpty();
        });
    }

    @Test(description = "Verify authors list is sorted by ID")
    public void verifyThatAuthorsListIsSortedTest() {
        var response = client.getAuthorsResponse();
        var deserializedAuthorsList = response.jsonPath()
                .getList("", Author.class);

        var isSorted = IntStream.range(0, deserializedAuthorsList.size() - 1)
                .allMatch(i -> deserializedAuthorsList.get(i).getId() <= deserializedAuthorsList.get(i + 1).getId());

        SoftAssertions.assertSoftly(as -> {
            as.assertThat(response.getStatusCode())
                    .as(STR."Code Status should be equal to: \{HttpStatus.SC_OK}")
                    .isEqualTo(HttpStatus.SC_OK);
            as.assertThat(isSorted)
                    .as("Authors List should be sorted by ID number")
                    .isTrue();
        });
    }

    @Test(description = "Check that all authors have the required fields")
    public void isEachFieldExistsInAuthorModelTest() {
        var response = client.getAuthorsResponse();
        var deserializedAuthorsList = response.jsonPath()
                .getList("", Author.class);

        deserializedAuthorsList.forEach(author -> SoftAssertions.assertSoftly(as -> {
            as.assertThat(author.getId())
                    .as("Id shouldn't be equal to zero")
                    .isNotZero();
            as.assertThat(author.getIdBook())
                    .as("Book Id shouldn't be equal to zero")
                    .isNotZero();
            as.assertThat(author.getFirstName())
                    .as("First Name shouldn't be empty")
                    .isNotEmpty();
            as.assertThat(author.getLastName())
                    .as("Last Name shouldn't be empty")
                    .isNotEmpty();
        }));
    }

    @Test(description = "Verify that we can get author by Id")
    public void getAuthorByIdTest() {
        var response = client.getAuthorsResponse();
        var deserializedAuthorsList = response.jsonPath()
                .getList("", Author.class);

        var randomAuthorId = AuthorHelper.getRandomAuthorId(deserializedAuthorsList);

        var authorByIdResponse = client.getAuthorByIdResponse(randomAuthorId);
        var deserializedAuthor = authorByIdResponse.as(Author.class);

        SoftAssertions.assertSoftly(as -> {
            as.assertThat(response.getStatusCode())
                    .as(STR."Code Status should be equal to: \{HttpStatus.SC_OK}")
                    .isEqualTo(HttpStatus.SC_OK);
            as.assertThat(deserializedAuthor)
                    .as(STR."Author with ID:\{randomAuthorId} should be exist")
                    .isNotNull();
            as.assertThat(deserializedAuthor.getId())
                    .as("Author should have id")
                    .isEqualTo(randomAuthorId);
            as.assertThat(deserializedAuthor.getIdBook())
                    .as("Book Id shouldn't be equal to zero")
                    .isNotZero();
        });
    }

    @Test(description = "Verify that we can get authors list by Book Id")
    public void getAuthorByBookIdTest() {
        var bookClient = new BookClient();
        var bookResponse = bookClient.getBooksResponse();
        var booksList = bookResponse.jsonPath()
                .getList("", Book.class);
        var bookId = bookClient.getBookByIdResponse(BookHelper.getRandomBookId(booksList))
                .as(Book.class)
                .getId();

        var response = client.getAuthorsByBookIdResponse(bookId);
        var deserializedAuthorsList = response.jsonPath()
                .getList("", Author.class);

        var isEachAuthorContainBookId = deserializedAuthorsList.stream()
                .allMatch(a -> a.getIdBook() == bookId);
        var isEachIdUnique = deserializedAuthorsList.stream()
                .map(Author::getId)
                .distinct()
                .count() == deserializedAuthorsList.size();

        SoftAssertions.assertSoftly(as -> {
            as.assertThat(response.getStatusCode())
                    .as(STR."Code Status should be equal to: \{HttpStatus.SC_OK}")
                    .isEqualTo(HttpStatus.SC_OK);
            as.assertThat(isEachAuthorContainBookId)
                    .as(STR."Each author should have book selected book id: \{bookId}")
                    .isTrue();
            as.assertThat(isEachIdUnique)
                    .as("Each author should have unique id")
                    .isTrue();
        });
    }


    @Test(description = "Verify that we cannot get author by non-existent ID")
    public void getAuthorByIdNonExistentIdTest() {
        var nonExistentAuthorResponse = client.getAuthorByIdResponse(faker.number().numberBetween(8888, 9999));
        var errorModel = nonExistentAuthorResponse.as(ErrorResponse.class);

        SoftAssertions.assertSoftly(as -> {
            as.assertThat(nonExistentAuthorResponse.getStatusCode())
                    .as(STR."Code Status should be equal to: \{HttpStatus.SC_NOT_FOUND}")
                    .isEqualTo(HttpStatus.SC_NOT_FOUND);
            as.assertThat(errorModel.getTitle())
                    .as("Error should have message with text: Not Found")
                    .isEqualTo(ErrorMessages.NOT_FOUND_ERROR_MESSAGE.getMessage());
        });
    }

    @Test(description = "Verify that we cannot get author by non-existent Book ID")
    public void getAuthorByNonExistentBookIdTest() {
        var nonExistentAuthorResponse = client.getAuthorsByBookIdResponse(faker.number().numberBetween(8888, 9999));
        var authorsList = nonExistentAuthorResponse.jsonPath().getList("");

        SoftAssertions.assertSoftly(as -> {
            as.assertThat(nonExistentAuthorResponse.getStatusCode())
                    .as(STR."Code Status should be equal to: \{HttpStatus.SC_OK}")
                    .isEqualTo(HttpStatus.SC_OK);
            as.assertThat(authorsList)
                    .as("List should be empty")
                    .isEmpty();
        });
    }


    @Test(description = "Verify that we cannot get deleted author")
    public void getDeletedAuthor() {
        var author = AuthorHelper.getAuthorWithRandomValues();

        var postResponse = client.createAuthor(author);
        Assert.assertEquals(postResponse.getStatusCode(), HttpStatus.SC_OK, "Author should be created");
        var createdAuthor = postResponse.as(Author.class);

        var deleteResponse = client.deleteAuthor(createdAuthor.getId());
        Assert.assertEquals(deleteResponse.getStatusCode(), HttpStatus.SC_OK, "Author should be deleted");

        var getAuthorByIdResponse = client.getAuthorByIdResponse(createdAuthor.getId());
        var errorModel = getAuthorByIdResponse.as(ErrorResponse.class);

        SoftAssertions.assertSoftly(as -> {
            as.assertThat(getAuthorByIdResponse.getStatusCode())
                    .as(STR."Code Status should be equal to: \{HttpStatus.SC_NOT_FOUND}")
                    .isEqualTo(HttpStatus.SC_NOT_FOUND);
            as.assertThat(errorModel.getTitle())
                    .as("Author should be deleted")
                    .isEqualTo(ErrorMessages.NOT_FOUND_ERROR_MESSAGE.getMessage());
        });
    }

}
