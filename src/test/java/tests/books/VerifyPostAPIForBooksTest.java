package tests.books;

import io.qameta.allure.Description;
import models.Book;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseBooksApiTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VerifyPostAPIForBooksTest extends BaseBooksApiTest {

    @Test
    @Description("New book creation test")
    public void testCreateBook() {
        var book = Book.builder()
                .title(faker.book().title())
                .description(faker.lorem().sentence())
                .pageCount(faker.number().numberBetween(100, 500))
                .excerpt(faker.lorem().sentence())
                .publishDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
                .build();

        var createdBook = client.createBook(book);
        Assert.assertNotNull(createdBook, "The book was not created");
        Assert.assertEquals(book.getTitle(), createdBook.getTitle(), "Book titles do not match");
    }
}
