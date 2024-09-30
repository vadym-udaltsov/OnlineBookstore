package tests.books;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseBooksApiTest;

public class VerifyPutAPIForBooksTest extends BaseBooksApiTest {

    @Test
    @Description("Test updating an existing book")
    public void testUpdateBook() {
        var faker = new Faker();
        var bookToUpdate = client.getBookById(1);
        var randomTitle = faker.book().title();
        bookToUpdate.setTitle(randomTitle);

        var updatedBook = client.updateBook(1, bookToUpdate);
        Assert.assertEquals(updatedBook.getTitle(), randomTitle, "Book titles do not match after update");
    }
}
