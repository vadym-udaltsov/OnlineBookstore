package utils.provider.author;

import org.testng.annotations.DataProvider;

public class AuthorDataProvider {
    private static final String CREATE_MESSAGE_PREFIX = "Author with %s values shouldn't be created";
    private static final String UPDATE_MESSAGE_PREFIX = "Author with %s values shouldn't be updated";

    private AuthorDataProvider() {
    }

    private static Object[][] getAuthorModelsForDataProvider(String actionPrefix) {
        return new Object[][]{
                AuthorDataHelper.getValidAuthorData(actionPrefix.replace("n't", " should be")),
                AuthorDataHelper.getEmptyValuesAuthorData(String.format(actionPrefix, "empty")),
                AuthorDataHelper.getEmptyIdAuthorData(String.format(actionPrefix, "empty ID")),
                AuthorDataHelper.getEmptyBookIdAuthorData(String.format(actionPrefix, "empty Book ID")),
                AuthorDataHelper.getNegativeIdAuthorData(String.format(actionPrefix, "negative ID")),
                AuthorDataHelper.getNegativeBookIdAuthorData(String.format(actionPrefix, "negative Book ID"))
        };
    }

    @DataProvider(name = "createAuthorDataProvider")
    public static Object[][] createAuthorDataProvider() {
        return getAuthorModelsForDataProvider(CREATE_MESSAGE_PREFIX);
    }

    @DataProvider(name = "updateAuthorDataProvider")
    public static Object[][] updateAuthorDataProvider() {
        return getAuthorModelsForDataProvider(UPDATE_MESSAGE_PREFIX);
    }
}
