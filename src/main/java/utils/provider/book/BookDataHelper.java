package utils.provider.book;

import models.Book;
import org.apache.http.HttpStatus;
import utils.BookHelper;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

public class BookDataHelper {

    private BookDataHelper() {
    }

    public static Object[] getValidBookData(String message) {
        return new Object[]{
                BookHelper.getBookWithRandomValues(),
                HttpStatus.SC_OK,
                message
        };
    }

    public static Object[] getEmptyValuesBookData(String message) {
        return new Object[]{
                BookHelper.getBookWithEmptyValues(),
                SC_BAD_REQUEST,
                message
        };
    }

    public static Object[] getEmptyPublishDateBookData(String message) {
        return new Object[]{
                Book.builder().publishDate(null).build(),
                SC_BAD_REQUEST,
                message
        };
    }

    public static Object[] getNegativeIdBookData(String message) {
        return new Object[]{
                Book.builder().id(-1).build(),
                SC_BAD_REQUEST,
                message
        };
    }

    public static Object[] getNegativePageCountBookData(String message) {
        return new Object[]{
                Book.builder().pageCount(-1).build(),
                SC_BAD_REQUEST,
                message
        };
    }
}
