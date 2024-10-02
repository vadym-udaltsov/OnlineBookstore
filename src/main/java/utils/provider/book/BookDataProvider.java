package utils.provider.book;

import org.testng.annotations.DataProvider;

public class BookDataProvider {
    private BookDataProvider() {
    }

    @DataProvider(name = "createBookDataProvider")
    public static Object[][] createBookDataProvider() {
        return new Object[][]{
                BookDataHelper.getValidBookData("Book with valid values should be created"),
                BookDataHelper.getEmptyValuesBookData("Book with empty values shouldn't be created"),
                BookDataHelper.getEmptyPublishDateBookData("Error should be displayed if publish date is empty"),
                BookDataHelper.getNegativeIdBookData("Book with negative Id shouldn't be created"),
                BookDataHelper.getNegativePageCountBookData("Book with negative Page Count shouldn't be created"),
        };
    }

    @DataProvider(name = "updateBookDataProvider")
    public static Object[][] updateBookDataProvider() {
        return new Object[][]{
                BookDataHelper.getValidBookData("Book with valid values should be updated"),
                BookDataHelper.getEmptyValuesBookData("Book with empty values shouldn't be updated"),
                BookDataHelper.getEmptyPublishDateBookData("Error should be displayed if publish date is empty"),
                BookDataHelper.getNegativeIdBookData("Book with negative Id shouldn't be updated"),
                BookDataHelper.getNegativePageCountBookData("Book with negative Page Count shouldn't be updated"),
        };
    }
}
