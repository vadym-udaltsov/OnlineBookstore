package tests.books;

import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseBooksApiTest;

public class VerifyGetAPIForBooksTest extends BaseBooksApiTest {

    @Test(description = "Test for getting a list of all books")
    public void testVerifyGetAPIForBooks() {
        getAllBooks();
        getBooks();
    }

    @Step("Get")
    public void getAllBooks() {
        var booksList = client.getBooksList();
        Assert.assertFalse(booksList.isEmpty(), "Books List should not be empty");
    }
    @Step("Get books")
    public void getBooks() {
        var booksList = client.getBooksList();
        Assert.assertFalse(booksList.isEmpty(), "Books List should not be empty");
    }

}
