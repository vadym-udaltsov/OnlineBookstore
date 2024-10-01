package tests.books;

import models.Book;
import models.errors.BookErrorResponse;
import org.apache.http.HttpStatus;
import org.assertj.core.api.SoftAssertions;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseBooksApiTest;
import utils.BookHelper;
import utils.JsonUtils;

import java.util.stream.IntStream;

public class VerifyGetAPIForBooksTest extends BaseBooksApiTest {
    @Test(description = "Verify that we can get list with all books")
    public void getBooksListTest() {
        var response = client.getBooksResponse();
        var deserializedBooksList = JsonUtils.jsonToObjectsList(response, Book.class);

        SoftAssertions.assertSoftly(as -> {
            as.assertThat(response.getStatusCode())
                    .as(STR."Code Status should be equal to: \{HttpStatus.SC_OK}")
                    .isEqualTo(HttpStatus.SC_OK);
            as.assertThat(deserializedBooksList)
                    .as("Books List should not be empty")
                    .isNotEmpty();
        });
    }

    @Test(description = "Verify books list is sorted by ID")
    public void verifyThatBooksListIsSortedTest() {
        var response = client.getBooksResponse();
        var deserializedBooksList = JsonUtils.jsonToObjectsList(response, Book.class);

        var isSorted = IntStream.range(0, deserializedBooksList.size() - 1)
                .allMatch(i -> deserializedBooksList.get(i).getId() <= deserializedBooksList.get(i + 1).getId());

        SoftAssertions.assertSoftly(as -> {
            as.assertThat(response.getStatusCode())
                    .as(STR."Code Status should be equal to: \{HttpStatus.SC_OK}")
                    .isEqualTo(HttpStatus.SC_OK);
            as.assertThat(isSorted)
                    .as("Books List should be sorted by ID number")
                    .isTrue();
        });
    }

    @Test(description = "Check that all books have the required fields")
    public void isEachFieldExistsInBookModelTest() {
        var response = client.getBooksResponse();
        var deserializedBooksList = JsonUtils.jsonToObjectsList(response, Book.class);

        deserializedBooksList.forEach(book -> SoftAssertions.assertSoftly(as -> {
            as.assertThat(book.getId())
                    .as("Id shouldn't be equal to zero")
                    .isNotZero();
            as.assertThat(book.getTitle())
                    .as("Title shouldn't be empty")
                    .isNotEmpty();
            as.assertThat(book.getDescription())
                    .as("Description shouldn't be empty")
                    .isNotEmpty();
            as.assertThat(book.getPageCount())
                    .as("Page Count shouldn't be equal to zero")
                    .isNotZero();
            as.assertThat(book.getExcerpt())
                    .as("Excerpt shouldn't be empty")
                    .isNotEmpty();
            as.assertThat(book.getPublishDate())
                    .as("Publish Date shouldn't be empty")
                    .isNotEmpty();
        }));
    }

    @Test(description = "Verify that we can get book by ID")
    public void getBookByIdTest() {
        var response = client.getBooksResponse();
        var deserializedBooksList = JsonUtils.jsonToObjectsList(response, Book.class);

        var randomBookId = BookHelper.getRandomBookId(deserializedBooksList);

        var bookByIdResponse = client.getBookByIdResponse(randomBookId);
        var deserializedBook = bookByIdResponse.as(Book.class);

        SoftAssertions.assertSoftly(as -> {
            as.assertThat(response.getStatusCode())
                    .as(STR."Code Status should be equal to: \{HttpStatus.SC_OK}")
                    .isEqualTo(HttpStatus.SC_OK);
            as.assertThat(deserializedBook)
                    .as(STR."Book with ID:\{randomBookId} should be exist")
                    .isNotNull();
            as.assertThat(deserializedBook.getId())
                    .as("Book should have id")
                    .isEqualTo(randomBookId);
            as.assertThat(deserializedBook.getTitle())
                    .as("Book should contain valid title")
                    .isNotEqualToIgnoringCase("Not Found");
        });
    }

    @Test(description = "Verify that we cannot get book by non-existent ID")
    public void getBookByIdNonExistentIdTest() {
        var nonExistentBookResponse = client.getBookByIdResponse(faker.number().numberBetween(8888, 9999));
        var errorModel = nonExistentBookResponse.as(BookErrorResponse.class);

        SoftAssertions.assertSoftly(as -> {
            as.assertThat(nonExistentBookResponse.getStatusCode())
                    .as(STR."Code Status should be equal to: \{HttpStatus.SC_NOT_FOUND}")
                    .isEqualTo(HttpStatus.SC_NOT_FOUND);
            as.assertThat(errorModel.getTitle())
                    .as("Book should have id")
                    .isEqualTo("Not Found");
        });
    }

    @Test(description = "Verify that we cannot get deleted book")
    public void getDeletedBook() {
        var model = BookHelper.getBookWithRandomValues();

        var postResponse = client.createBook(model);
        Assert.assertEquals(postResponse.getStatusCode(), HttpStatus.SC_OK, "Book should be created");
        var createdBook = postResponse.as(Book.class);

        var deleteResponse = client.deleteBook(createdBook.getId());
        Assert.assertEquals(deleteResponse.getStatusCode(), HttpStatus.SC_OK, "Book should be deleted");

        var checkResponse = client.getBookByIdResponse(createdBook.getId());
        var errorModel = checkResponse.as(BookErrorResponse.class);

        SoftAssertions.assertSoftly(as -> {
            as.assertThat(checkResponse.getStatusCode())
                    .as(STR."Code Status should be equal to: \{HttpStatus.SC_NOT_FOUND}")
                    .isEqualTo(HttpStatus.SC_NOT_FOUND);
            as.assertThat(errorModel.getTitle())
                    .as("Book should be deleted")
                    .isEqualTo("Not Found");
        });
    }

}
