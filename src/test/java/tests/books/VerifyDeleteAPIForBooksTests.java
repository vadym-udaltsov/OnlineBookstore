package tests.books;

import io.qameta.allure.Description;
import models.Book;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseBooksApiTest;

public class VerifyDeleteAPIForBooksTests extends BaseBooksApiTest {
    @Test
    @Description("Test for deleting a book")
    public void testDeleteBook() {
        var idsList = client.getBooksList()
                .stream()
                .map(Book::getId)
                .toList();
        var randomBook = client.getBookById(faker.number().numberBetween(1, idsList.size() - 1));
        var randomBookId = randomBook.getId();
        client.deleteBook(randomBookId);
        var newBook = client.getBookById(randomBookId);
        Assert.assertNotEquals(randomBook, newBook, "Previous book should be deleted");
    }
}
