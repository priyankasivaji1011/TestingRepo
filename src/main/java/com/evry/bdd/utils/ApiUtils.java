package com.evry.bdd.utils;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;
public class ApiUtils {

    public static Response post(String endpoint, String jsonBody) {
        return given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post(endpoint);
    }

    public static Response get(String endpoint) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get(endpoint);
    }
    //test
}
