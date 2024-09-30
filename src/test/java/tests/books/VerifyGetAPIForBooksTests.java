package tests.books;

import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseBooksApiTest;

public class VerifyGetAPIForBooksTests extends BaseBooksApiTest {

    @Test
    @Description("Test for getting a list of all books")
    public void testGetBooks() {
        var booksList = client.getBooksList();
        Assert.assertFalse(booksList.isEmpty(), "Books List should not be empty");
    }

}
