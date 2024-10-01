package utils.provider;

import models.Book;
import org.apache.http.HttpStatus;
import org.testng.annotations.DataProvider;
import utils.BookHelper;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

public class BookDataProvider {
    private BookDataProvider() {
    }

    @DataProvider(name = "createBookDataProvider")
    public static Object[][] createBookDataProvider() {
        return new Object[][]{
                {
                        BookHelper.getBookWithRandomValues(),
                        HttpStatus.SC_OK,
                        "Book with valid values should be created"
                },
                {
                        BookHelper.getBookWithEmptyValues(),
                        SC_BAD_REQUEST,
                        "Book with empty values shouldn't be created"
                },
                {
                        Book.builder().publishDate(null)
                                .build(),
                        SC_BAD_REQUEST,
                        "Error should be displayed if publish date is empty"
                },
                {
                        Book.builder().id(-1).build(),
                        SC_BAD_REQUEST,
                        "Book with negative Id shouldn't be created"
                },
                {
                        Book.builder().pageCount(-1).build(),
                        SC_BAD_REQUEST,
                        "Book with negative Page Count shouldn't be created"
                }
        };
    }

    @DataProvider(name = "updateBookDataProvider")
    public static Object[][] updateBookDataProvider() {
        return new Object[][]{
                {
                        BookHelper.getBookWithRandomValues(),
                        HttpStatus.SC_OK,
                        "Book with valid values should be updated"
                },
                {
                        BookHelper.getBookWithEmptyValues(),
                        SC_BAD_REQUEST,
                        "Book with empty values shouldn't be updated"
                },
                {
                        Book.builder().publishDate(null)
                                .build(),
                        SC_BAD_REQUEST,
                        "Error should be displayed if publish date is empty"
                },
                {
                        Book.builder().id(-1).build(),
                        SC_BAD_REQUEST,
                        "Book with negative Id shouldn't be updated"
                },
                {
                        Book.builder().pageCount(-1).build(),
                        SC_BAD_REQUEST,
                        "Book with negative Page Count shouldn't be updated"
                }
        };
    }
}
