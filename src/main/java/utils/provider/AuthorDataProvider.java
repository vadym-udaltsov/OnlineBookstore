package utils.provider;

import models.Author;
import org.apache.http.HttpStatus;
import org.testng.annotations.DataProvider;
import utils.AuthorHelper;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

public class AuthorDataProvider {
    private AuthorDataProvider() {
    }

    @DataProvider(name = "createAuthorDataProvider")
    public static Object[][] createBookDataProvider() {
        return new Object[][]{
                {
                        AuthorHelper.getAuthorWithRandomValues(),
                        HttpStatus.SC_OK,
                        "Author with valid values should be created"
                },
                {
                        AuthorHelper.getAuthorWithEmptyValues(),
                        SC_BAD_REQUEST,
                        "Author with empty values shouldn't be created"
                },
                {
                        Author.builder().id(0).build(),
                        SC_BAD_REQUEST,
                        "Author with empty ID shouldn't be created"
                },
                {
                        Author.builder().idBook(0).build(),
                        SC_BAD_REQUEST,
                        "Author with empty Book ID shouldn't be created"
                },
                {
                        Author.builder().id(-1).build(),
                        SC_BAD_REQUEST,
                        "Author with negative ID shouldn't be created"
                },
                {
                        Author.builder().idBook(-1).build(),
                        SC_BAD_REQUEST,
                        "Author with negative Book ID shouldn't be created"
                }
        };
    }

    @DataProvider(name = "updateAuthorDataProvider")
    public static Object[][] updateBookDataProvider() {
        return new Object[][]{
                {
                        AuthorHelper.getAuthorWithRandomValues(),
                        HttpStatus.SC_OK,
                        "Author with valid values should be updated"
                },
                {
                        AuthorHelper.getAuthorWithEmptyValues(),
                        SC_BAD_REQUEST,
                        "Author with empty values shouldn't be updated"
                },
                {
                        Author.builder().id(0).build(),
                        SC_BAD_REQUEST,
                        "Author with empty ID shouldn't be updated"
                },
                {
                        Author.builder().idBook(0).build(),
                        SC_BAD_REQUEST,
                        "Author with empty Book ID shouldn't be updated"
                },
                {
                        Author.builder().id(-1).build(),
                        SC_BAD_REQUEST,
                        "Author with negative ID shouldn't be updated"
                },
                {
                        Author.builder().idBook(-1).build(),
                        SC_BAD_REQUEST,
                        "Author with negative Book ID shouldn't be updated"
                }
        };
    }
}
