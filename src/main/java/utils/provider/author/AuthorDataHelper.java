package utils.provider.author;

import models.Author;
import org.apache.http.HttpStatus;
import utils.AuthorHelper;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

public class AuthorDataHelper {

    private AuthorDataHelper() {
    }

    public static Object[] getValidAuthorData(String message) {
        return new Object[]{
                AuthorHelper.getAuthorWithRandomValues(),
                HttpStatus.SC_OK,
                message
        };
    }

    public static Object[] getEmptyValuesAuthorData(String message) {
        return new Object[]{
                AuthorHelper.getAuthorWithEmptyValues(),
                SC_BAD_REQUEST,
                message
        };
    }

    public static Object[] getEmptyIdAuthorData(String message) {
        return new Object[]{
                Author.builder().id(0).build(),
                SC_BAD_REQUEST,
                message
        };
    }

    public static Object[] getEmptyBookIdAuthorData(String message) {
        return new Object[]{
                Author.builder().idBook(0).build(),
                SC_BAD_REQUEST,
                message
        };
    }

    public static Object[] getNegativeIdAuthorData(String message) {
        return new Object[]{
                Author.builder().id(-1).build(),
                SC_BAD_REQUEST,
                message
        };
    }

    public static Object[] getNegativeBookIdAuthorData(String message) {
        return new Object[]{
                Author.builder().idBook(-1).build(),
                SC_BAD_REQUEST,
                message
        };
    }
}
