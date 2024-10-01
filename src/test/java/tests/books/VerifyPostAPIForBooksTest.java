package tests.books;

import models.Book;
import models.errors.BookErrorResponse;
import org.apache.http.HttpStatus;
import org.assertj.core.api.SoftAssertions;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseBooksApiTest;
import utils.BookHelper;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

public class VerifyPostAPIForBooksTest extends BaseBooksApiTest {

    private final String VALIDATION_ERROR_MESSAGE = "One or more validation errors occurred.";
    private final String DATE_ERROR_MESSAGE = "The JSON value could not be converted to System.DateTime.";

    @Test(description = "Verify that we can create a new book")
    public void createBookTest() {
        var book = BookHelper.getBookWithRandomValues();

        var postResponse = client.createBook(book);
        var createdBook = postResponse.as(Book.class);

        SoftAssertions.assertSoftly(as -> {
            as.assertThat(postResponse.getStatusCode())
                    .as(STR."Code Status should be equal to: \{HttpStatus.SC_OK}")
                    .isEqualTo(HttpStatus.SC_OK);
            as.assertThat(createdBook)
                    .as("Created book should not be null")
                    .isNotNull();
            as.assertThat(createdBook.getId())
                    .as("Created book should have a valid ID")
                    .isNotZero();
            as.assertThat(createdBook.getTitle())
                    .as("Created book should have the correct title")
                    .isEqualTo(book.getTitle());
        });
    }

    @Test(description = "Verify that creating a book with missing fields except Date")
    public void createBookWithMissingFieldsExceptDateTest() {
        var incompleteBook = Book.builder()
                .id(0)
                .title(null)
                .description(null)
                .pageCount(0)
                .excerpt(null)
                .build();

        var postResponse = client.createBook(incompleteBook);

        Assert.assertEquals(postResponse.getStatusCode(), SC_BAD_REQUEST, "Book shouldn't have empty fields");
    }

    @Test(description = "Verify that creating a book with missing date field")
    public void createBookWithMissingDateFieldTest() {
        var incompleteBook = Book.builder()
                .publishDate(null)
                .build();

        var postResponse = client.createBook(incompleteBook);
        var errorModel = postResponse.as(BookErrorResponse.class);

        SoftAssertions.assertSoftly(as -> {
            as.assertThat(postResponse.getStatusCode())
                    .as(STR."Code Status should be equal to: \{SC_BAD_REQUEST}")
                    .isEqualTo(SC_BAD_REQUEST);
            as.assertThat(errorModel.getTitle())
                    .as("Publish Date cannot be Empty")
                    .isEqualTo(VALIDATION_ERROR_MESSAGE);
            as.assertThat(errorModel.getDateError().getPublishDate().getFirst())
                    .as("Publish Date cannot be Empty")
                    .contains(DATE_ERROR_MESSAGE);
        });
    }

    @Test(description = "Verify that creating a book with a negative page count fails")
    public void createBookWithNegativePageCountTest() {
        var invalidBook = BookHelper.getBookWithRandomValues();
        var negativeNumber = Integer.parseInt(STR."-\{faker.number().numberBetween(1, 5)}");

        invalidBook.setId(negativeNumber);
        invalidBook.setPageCount(negativeNumber);

        var postResponse = client.createBook(invalidBook);

        Assert.assertEquals(postResponse.getStatusCode(), HttpStatus.SC_BAD_REQUEST,
                STR."Status Code should be equal to: \{HttpStatus.SC_BAD_REQUEST}");
    }
}
