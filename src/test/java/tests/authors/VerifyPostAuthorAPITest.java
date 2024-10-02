package tests.authors;

import models.Author;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;
import tests.BaseAuthorsApiTest;
import utils.provider.author.AuthorDataProvider;

public class VerifyPostAuthorAPITest extends BaseAuthorsApiTest {

    @Test(description = "Verify that we can add a new author", dataProvider = "createAuthorDataProvider",
            dataProviderClass = AuthorDataProvider.class)
    public void createAuthorTest(Author author, int expectedStatusCode, String assertMessage) {
        var postResponse = client.createAuthor(author);
        var createdAuthor = postResponse.as(Author.class);

        SoftAssertions.assertSoftly(as -> {
            as.assertThat(postResponse.getStatusCode())
                    .as(assertMessage)
                    .isEqualTo(expectedStatusCode);
            as.assertThat(createdAuthor)
                    .as("Author should be created")
                    .isNotNull();
        });
    }
}
