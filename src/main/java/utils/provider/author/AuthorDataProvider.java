package utils.provider.author;

import org.testng.annotations.DataProvider;

public class AuthorDataProvider {
    private AuthorDataProvider() {
    }

    @DataProvider(name = "createAuthorDataProvider")
    public static Object[][] createAuthorDataProvider() {
        return new Object[][]{
                AuthorDataHelper.getValidAuthorData("Author with valid values should be created"),
                AuthorDataHelper.getEmptyValuesAuthorData("Author with empty values shouldn't be created"),
                AuthorDataHelper.getEmptyIdAuthorData("Author with empty ID shouldn't be created"),
                AuthorDataHelper.getEmptyBookIdAuthorData("Author with empty Book ID shouldn't be created"),
                AuthorDataHelper.getNegativeIdAuthorData("Author with negative ID shouldn't be created"),
                AuthorDataHelper.getNegativeBookIdAuthorData("Author with negative Book ID shouldn't be created")
        };
    }

    @DataProvider(name = "updateAuthorDataProvider")
    public static Object[][] updateAuthorDataProvider() {
        return new Object[][]{
                AuthorDataHelper.getValidAuthorData("Author with valid values should be updated"),
                AuthorDataHelper.getEmptyValuesAuthorData("Author with empty values shouldn't be updated"),
                AuthorDataHelper.getEmptyIdAuthorData("Author with empty ID shouldn't be updated"),
                AuthorDataHelper.getEmptyBookIdAuthorData("Author with empty Book ID shouldn't be updated"),
                AuthorDataHelper.getNegativeIdAuthorData("Author with negative ID shouldn't be updated"),
                AuthorDataHelper.getNegativeBookIdAuthorData("Author with negative Book ID shouldn't be updated")
        };
    }
}
