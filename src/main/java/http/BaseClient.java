package http;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import config.ConfigReader;

import static io.restassured.RestAssured.given;

public class BaseClient {
    private static final String URL = ConfigReader.getBaseUrl();

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
                .header("Content-Type", "application/json")
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
