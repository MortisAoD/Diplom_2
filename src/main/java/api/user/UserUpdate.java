package api.user;

import api.Request;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserUpdate extends Request {
    private final static String method = "/auth/user";

    @Step("request PATCH | method '/auth/user' [with token]")
    public Response requestUserUpdate(Object model, String token) {
        return given().auth()
                .oauth2(token.replace("Bearer ", ""))
                .header("Content-type", "application/json")
                .and()
                .body(model)
                .when()
                .patch(method);
    }

    @Step("request PATCH | method '/auth/user' [without token]")
    public Response requestUserUpdate(Object model) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(model)
                .when()
                .patch(method);
    }
}