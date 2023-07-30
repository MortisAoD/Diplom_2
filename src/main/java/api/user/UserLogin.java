package api.user;

import api.Request;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserLogin extends Request {
    private final static String method = "/auth/login";

    @Step("request POST | method '/auth/login'")
    public Response requestUserLogin(Object model) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(model)
                .when()
                .post(method);
    }
}