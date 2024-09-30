package tests.books;

import models.Book;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseBooksApiTest;

public class VerifyPostAPIForBooksTest extends BaseBooksApiTest {
    @Test(description = "Verify new book creation")
    public void verifyCreateBookTest() {
        var book = Book.builder()
                .id(faker.number().numberBetween(100, 200))
                .build();

        var createdBook = client.createBook(book);
        Assert.assertNotNull(createdBook, "The book was not created");
        Assert.assertEquals(book.getTitle(), createdBook.getTitle(), "Book titles do not match");
    }
}
