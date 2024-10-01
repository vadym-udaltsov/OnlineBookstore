package tests.books;

import enums.ErrorMessages;
import models.Book;
import models.errors.ErrorResponse;
import org.apache.http.HttpStatus;
import org.assertj.core.api.SoftAssertions;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseBooksApiTest;
import utils.BookHelper;

public class VerifyDeleteBookAPITest extends BaseBooksApiTest {

    @Test(description = "Verify that we can delete an existing book")
    public void deleteExistingBookTest() {
        var booksList = client.getBooksResponse().jsonPath().getList("", Book.class);
        var existenceBook = client.getBookByIdResponse(BookHelper.getRandomBookId(booksList))
                .as(Book.class);

        var deleteResponse = client.deleteBook(existenceBook.getId());
        Assert.assertEquals(deleteResponse.getStatusCode(), HttpStatus.SC_OK, "Book should be deleted");

        var checkResponse = client.getBookByIdResponse(existenceBook.getId());
        var errorModel = checkResponse.as(ErrorResponse.class);

        SoftAssertions.assertSoftly(as -> {
            as.assertThat(checkResponse.getStatusCode())
                    .as(STR."Code Status should be equal to: \{HttpStatus.SC_NOT_FOUND}")
                    .isEqualTo(HttpStatus.SC_NOT_FOUND);
            as.assertThat(errorModel.getTitle())
                    .as("Book should be deleted")
                    .isEqualTo(ErrorMessages.NOT_FOUND_ERROR_MESSAGE.getMessage());
        });
    }

    @Test(description = "Verify that we can delete an created book")
    public void deleteCreatedBookTest() {
        var model = BookHelper.getBookWithRandomValues();

        var postResponse = client.createBook(model);
        Assert.assertEquals(postResponse.getStatusCode(), HttpStatus.SC_OK, "Book should be created");
        var createdBook = postResponse.as(Book.class);

        var deleteResponse = client.deleteBook(createdBook.getId());
        Assert.assertEquals(deleteResponse.getStatusCode(), HttpStatus.SC_OK, "Book should be deleted");

        var checkResponse = client.getBookByIdResponse(createdBook.getId());
        var errorModel = checkResponse.as(ErrorResponse.class);

        SoftAssertions.assertSoftly(as -> {
            as.assertThat(checkResponse.getStatusCode())
                    .as(STR."Code Status should be equal to: \{HttpStatus.SC_NOT_FOUND}")
                    .isEqualTo(HttpStatus.SC_NOT_FOUND);
            as.assertThat(errorModel.getTitle())
                    .as("Book should be deleted")
                    .isEqualTo(ErrorMessages.NOT_FOUND_ERROR_MESSAGE.getMessage());
        });
    }

    @Test(description = "Verify trying to delete a non-existent book")
    public void deleteNonExistentBookTest() {
        var booksList = client.getBooksResponse().jsonPath().getList("", Book.class);
        var lastId = booksList.stream()
                .map(Book::getId)
                .max(Long::compare)
                .orElseThrow();

        var deleteResponse = client.deleteBook(lastId + faker.number().numberBetween(100, 200));

        Assert.assertEquals(deleteResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND,
                "Cannot delete book with non-existent Id");
    }

    @Test(description = "Verify trying to delete a book with negative id")
    public void deleteBookWithNegativeIdTest() {
        var negativeId = Integer.parseInt(STR."-\{faker.number().numberBetween(1, 5)}");
        var deleteResponse = client.deleteBook(negativeId);

        Assert.assertEquals(deleteResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND,
                "Cannot delete book with negative Id");
    }

    @Test(description = "Verify trying to delete a book after it has already been deleted")
    public void deleteAlreadyDeletedBookTest() {
        var booksList = client.getBooksResponse().jsonPath().getList("", Book.class);
        var existenceBook = client.getBookByIdResponse(BookHelper.getRandomBookId(booksList))
                .as(Book.class);

        var deleteResponse = client.deleteBook(existenceBook.getId());
        Assert.assertEquals(deleteResponse.getStatusCode(), HttpStatus.SC_NO_CONTENT, "Book should be deleted");

        var secondDeleteResponse = client.deleteBook(existenceBook.getId());

        Assert.assertEquals(secondDeleteResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND,
                "Cannot delete book if book has already been deleted");

    }
}
