package client;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static utils.ConfigReader.getProperty;

public class BaseClient {
    private static final String URL = getProperty("base.url").concat(getProperty("api.version.endpoint"));
    private static final String CONTENT_TYPE = "application/json";

    static AllureRestAssured allureFilter = new AllureRestAssured()
            .setRequestAttachmentName("API call")
            .setResponseAttachmentName("Response");

    public Response get(String endpoint) {
        return executeRequestWithoutBody(endpoint, Method.GET);
    }

    public <T> Response post(String endpoint, T body) {
        return executeRequest(endpoint, body, Method.POST);
    }

    public <T> Response put(String endpoint, T body) {
        return executeRequest(endpoint, body, Method.PUT);
    }

    public Response delete(String endpoint) {
        return executeRequestWithoutBody(endpoint, Method.DELETE);
    }

    private <T> Response executeRequest(String endpoint, T body, Method method) {
        return given()
                .filter(allureFilter)
                .baseUri(URL)
                .header("Content-Type", CONTENT_TYPE)
                .body(body)
                .when()
                .request(method.name(), endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }

    private Response executeRequestWithoutBody(String endpoint, Method method) {
        return given()
                .filter(allureFilter)
                .baseUri(URL)
                .when()
                .request(method.name(), endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }
}
