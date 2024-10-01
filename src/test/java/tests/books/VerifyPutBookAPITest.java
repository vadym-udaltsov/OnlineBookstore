package tests.books;

import models.Book;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseBooksApiTest;
import utils.BookHelper;
import utils.provider.BookDataProvider;

public class VerifyPutBookAPITest extends BaseBooksApiTest {

    @Test(description = "Verify that we can update an existing book", dataProvider = "updateBookDataProvider",
            dataProviderClass = BookDataProvider.class)
    public void updateExistingBookTest(Book book, int expectedStatusCode, String message) {
        var booksList = client.getBooksResponse().jsonPath()
                .getList("", Book.class);
        var randomId = BookHelper.getRandomBookId(booksList);

        var putResponse = client.updateBook(randomId, book);

        Assert.assertEquals(putResponse.getStatusCode(), expectedStatusCode, message);
    }

    @Test(description = "Verify updating a non-existent book")
    public void updateNonExistentBookTest() {
        var authorsList = client.getBooksResponse().jsonPath().getList("", Book.class);
        var lastId = authorsList.stream()
                .map(Book::getId)
                .max(Long::compare)
                .orElseThrow();
        var book = BookHelper.getBookWithRandomValues();

        var putResponse = client.updateBook(lastId + faker.number().numberBetween(100, 200), book);

        Assert.assertEquals(putResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND, "Book doesn't exist");
    }


    @Test(description = "Verify updating a book by negative ID")
    public void updateBookByNegativeIdTest() {
        var book = BookHelper.getBookWithRandomValues();
        var negativeId = Integer.parseInt(STR."-\{faker.number().numberBetween(1, 5)}");

        var putResponse = client.updateBook(negativeId, book);

        Assert.assertEquals(putResponse.getStatusCode(), HttpStatus.SC_BAD_REQUEST,
                "Cannot update book by negative Id");
    }
}
