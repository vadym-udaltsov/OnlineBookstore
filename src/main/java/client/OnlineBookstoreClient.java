package client;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;

import static config.Properties.API_VERSION;
import static config.Properties.BASE_URL;
import static io.restassured.RestAssured.given;

public class OnlineBookstoreClient {
    private static final String URL = BASE_URL.concat(API_VERSION);
    private static final String CONTENT_TYPE = "application/json";

    static AllureRestAssured allureFilter = new AllureRestAssured()
            .setRequestAttachmentName("API call")
            .setResponseAttachmentName("Response");


    public Response get(String endpoint) {
        return given()
                .filter(allureFilter)
                .baseUri(URL)
                .when()
                .get(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public <T> T post(String endpoint, T body, Class<T> clazz) {
        return given()
                .filter(allureFilter)
                .baseUri(URL)
                .header("Content-Type", CONTENT_TYPE)
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .log().all()
                .extract()
                .as(clazz);
    }

    public <T> T put(String endpoint, T body, Class<T> clazz) {
        return given()
                .filter(allureFilter)
                .baseUri(URL)
                .header("Content-Type", CONTENT_TYPE)
                .body(body)
                .when()
                .put(endpoint)
                .then()
                .log().all()
                .extract()
                .as(clazz);
    }

    public Response delete(String endpoint) {
        return given()
                .filter(allureFilter)
                .baseUri(URL)
                .when()
                .delete(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }
}
