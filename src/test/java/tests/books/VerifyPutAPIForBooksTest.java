package tests.books;

import models.Book;
import org.apache.http.HttpStatus;
import org.assertj.core.api.SoftAssertions;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseBooksApiTest;
import utils.BookHelper;

public class VerifyPutAPIForBooksTest extends BaseBooksApiTest {

    @Test(description = "Verify that we can update an existing book")
    public void updateExistingBookTest() {
        var model = BookHelper.getBookWithRandomValues();

        var postResponse = client.createBook(model);
        Assert.assertEquals(postResponse.getStatusCode(), HttpStatus.SC_OK, "Book should be created");
        var createdBook = postResponse.as(Book.class);

        var newBook = BookHelper.getBookWithRandomValues();

        var putResponse = client.updateBook(createdBook.getId(), newBook);
        var updatedBook = putResponse.as(Book.class);

        SoftAssertions.assertSoftly(as -> {
            as.assertThat(putResponse.getStatusCode())
                    .as(STR."Status Code should be equal to \{HttpStatus.SC_OK}")
                    .isEqualTo(HttpStatus.SC_OK);
            as.assertThat(createdBook)
                    .as(STR."Book should be updated: \{updatedBook}")
                    .isNotEqualTo(updatedBook);
        });
    }

    @Test(description = "Verify that updating a non-existent book fails")
    public void updateNonExistentBookTest() {
        var model = BookHelper.getBookWithRandomValues();

        var postResponse = client.createBook(model);
        Assert.assertEquals(postResponse.getStatusCode(), HttpStatus.SC_OK, "Book should be created");
        var createdBook = postResponse.as(Book.class);

        var newBook = BookHelper.getBookWithRandomValues();

        var putResponse = client.updateBook(createdBook.getId(), newBook);
        var updatedBook = putResponse.as(Book.class);

        SoftAssertions.assertSoftly(as -> {
            as.assertThat(putResponse.getStatusCode())
                    .as(STR."Status Code should be equal to \{HttpStatus.SC_OK}")
                    .isEqualTo(HttpStatus.SC_OK);
            as.assertThat(createdBook)
                    .as(STR."Book should be updated: \{updatedBook}")
                    .isNotEqualTo(updatedBook);
        });
    }


    @Test(description = "Verify that updating only one field of the book is successful")
    public void updateSingleFieldTest() {
        var booksList = client.getBooksResponse().jsonPath().getList("", Book.class);
        var randomBookId = BookHelper.getRandomBookId(booksList);

        var existingBook = client.getBookByIdResponse(randomBookId).as(Book.class);

        var newTitle = STR."\{faker.book().title()}sadasdsad";

        var updatedModel = Book.builder()
                .id(existingBook.getId())
                .title(newTitle)
                .pageCount(existingBook.getPageCount())
                .description(existingBook.getDescription())
                .excerpt(existingBook.getExcerpt())
                .publishDate(existingBook.getPublishDate())
                .build();

        var putResponse = client.updateBook(existingBook.getId(), updatedModel);
        var updatedBook = putResponse.as(Book.class);

        SoftAssertions.assertSoftly(as -> {
            as.assertThat(putResponse.getStatusCode())
                    .as(STR."Status Code should be equal to \{HttpStatus.SC_OK}")
                    .isEqualTo(HttpStatus.SC_OK);
            as.assertThat(updatedBook.getTitle())
                    .as(STR."Book Title should be updated: \{newTitle}")
                    .isEqualTo(newTitle);
            as.assertThat(updatedBook)
                    .as("Book should be updated and not equal to previous version")
                    .isNotEqualTo(existingBook);
        });
    }

    @Test(description = "Verify that updating a book with negative ID fails")
    public void updateBookWithNegativeIdTest() {
        var book = BookHelper.getBookWithRandomValues();
        var negativeId = Integer.parseInt(STR."-\{faker.number().numberBetween(1, 5)}");

        var putResponse = client.updateBook(negativeId, book);

        Assert.assertEquals(putResponse.getStatusCode(), HttpStatus.SC_BAD_REQUEST,
                STR."Status Code should be equal to: \{HttpStatus.SC_BAD_REQUEST}");
    }
}
