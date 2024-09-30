package tests.books;

import org.assertj.core.api.SoftAssertions;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseBooksApiTest;
import utils.BookHelper;
import utils.JsonObjectMapper;

import java.util.stream.IntStream;

public class VerifyGetAPIForBooksTest extends BaseBooksApiTest {

    @Test(description = "Verify that we can get list with all books")
    public void verifyGetBooksListTest() {
        var booksList = client.getBooksList();

        Assert.assertFalse(booksList.isEmpty(), "Books List should not be empty");
    }

    @Test(description = "Verify books list is sorted by ID")
    public void verifyThatBooksListIsSortedTest() {
        var booksList = client.getBooksList();

        var isSorted = IntStream.range(0, booksList.size() - 1)
                .allMatch(i -> booksList.get(i).getId() <= booksList.get(i + 1).getId());
        Assert.assertTrue(isSorted, "Books List should be sorted by ID number");
    }

    @Test(description = "Check that all books have the required fields")
    public void verifyThatEachFieldExistsInBookModelTest() {
        var booksList = client.getBooksList();

        var jsonObjectMapper = new JsonObjectMapper();
        booksList.forEach(book -> {
            var jsonBook = jsonObjectMapper.objectToJson(book);
            Assert.assertNotNull(jsonBook, "Book should be converted to JSON format");

            SoftAssertions.assertSoftly(as -> {
                as.assertThat(jsonBook.contains("id")).isTrue();
                as.assertThat(jsonBook.contains("title")).isTrue();
                as.assertThat(jsonBook.contains("description")).isTrue();
                as.assertThat(jsonBook.contains("pageCount")).isTrue();
                as.assertThat(jsonBook.contains("excerpt")).isTrue();
                as.assertThat(jsonBook.contains("publishDate")).isTrue();
            });
        });
    }

    @Test(description = "Verify that we can get book by ID")
    public void verifyGetBookByIdTest() {
        var booksList = client.getBooksList();

        var id = BookHelper.getRandomID(booksList);
        var book = client.getBookById(id);
        SoftAssertions.assertSoftly(as -> {
            as.assertThat(book)
                    .as(STR."Book with ID:\{id}should be exist")
                    .isNotNull();
            as.assertThat(book.getId())
                    .as("Book should have id")
                    .isEqualTo(id);
        });
    }
    @Test(description = "Verify that we cannot get book by non-existent ID")
    public void verifyGetBookByINonExistentIdTest() {
        var booksList = client.getBooksList();

        var notValidId = booksList.getLast().getId() + 100;
        var nonExistentBook = client.getBookById(notValidId);
        Assert.assertTrue(nonExistentBook.getTitle().equalsIgnoreCase("Not Found"),
                "Book shouldn't be found by non-existent ID");
    }

}
