package tests.books;

import models.Book;
import models.errors.ErrorResponse;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;
import tests.BaseBooksApiTest;
import utils.provider.book.BookDataProvider;

import static enums.ErrorMessages.PUBLISH_DATE_ERROR_MESSAGE;
import static enums.ErrorMessages.VALIDATION_ERROR_MESSAGE;

public class VerifyPostBookAPITest extends BaseBooksApiTest {

    @Test(description = "Verify that we can create a new book with Data", dataProvider = "createBookDataProvider",
            dataProviderClass = BookDataProvider.class)
    public void createBookTest(Book book, int expectedStatusCode, String message) {

        var postResponse = client.createBook(book);
        if (book.getPublishDate() == null) {
            var errorModel = postResponse.as(ErrorResponse.class);
            SoftAssertions.assertSoftly(as -> {
                as.assertThat(postResponse.getStatusCode())
                        .as(STR."Code Status should be equal to: \{expectedStatusCode}")
                        .isEqualTo(expectedStatusCode);
                as.assertThat(errorModel.getTitle())
                        .as("Error should have title with message")
                        .contains(VALIDATION_ERROR_MESSAGE.getMessage());
                as.assertThat(errorModel.getDateError().getPublishDate().getFirst())
                        .as("Publish Date cannot be Empty")
                        .contains(PUBLISH_DATE_ERROR_MESSAGE.getMessage());
            });
        } else {
            var createdBook = postResponse.as(Book.class);

            SoftAssertions.assertSoftly(as -> {
                as.assertThat(postResponse.getStatusCode())
                        .as(message)
                        .isEqualTo(expectedStatusCode);
                as.assertThat(createdBook)
                        .as("Book should be created")
                        .isNotNull();
            });
        }
    }
}
