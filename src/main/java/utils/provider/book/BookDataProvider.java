package utils.provider.book;

import org.testng.annotations.DataProvider;

public class BookDataProvider {
    private static final String CREATE_MESSAGE_PREFIX = "Book with %s values shouldn't be created";
    private static final String UPDATE_MESSAGE_PREFIX = "Book with %s values shouldn't be updated";

    private BookDataProvider() {
    }

    private static Object[][] getBookModelsForDataProvider(String actionPrefix) {
        return new Object[][]{
                BookDataHelper.getValidBookData(actionPrefix.replace("n't", " should be")),
                BookDataHelper.getEmptyValuesBookData(String.format(actionPrefix, "empty")),
                BookDataHelper.getEmptyPublishDateBookData("Error should be displayed if publish date is empty"),
                BookDataHelper.getNegativeIdBookData(String.format(actionPrefix, "negative Id")),
                BookDataHelper.getNegativePageCountBookData(String.format(actionPrefix, "negative Page Count"))
        };
    }

    @DataProvider(name = "createBookDataProvider")
    public static Object[][] createBookDataProvider() {
        return getBookModelsForDataProvider(CREATE_MESSAGE_PREFIX);
    }

    @DataProvider(name = "updateBookDataProvider")
    public static Object[][] updateBookDataProvider() {
        return getBookModelsForDataProvider(UPDATE_MESSAGE_PREFIX);
    }
}
