package api.user;

import api.Request;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserApi extends Request {
    private final static String METHOD_REGISTER = "/auth/register";
    private final static String METHOD_DELETE = "/auth/user";
    private final static String METHOD_LOGIN = "/auth/login";
    private final static String METHOD_UPDATE = "/auth/user";

    @Step("request POST | method '/auth/register'")
    public Response requestUserCreate(Object model) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(model)
                .when()
                .post(METHOD_REGISTER);
    }

    @Step("request DELETE | method '/auth/user' [with token]")
    public static void requestDelete(String token) {
        given().auth()
                .oauth2(token.replace("Bearer ", ""))
                .header("Content-type", "application/json")
                .when()
                .delete(METHOD_DELETE);
    }

    @Step("request DELETE | method '/auth/user' [without token]")
    public static void requestDelete() {
        given()
                .header("Content-type", "application/json")
                .when()
                .delete(METHOD_DELETE);
    }

    @Step("request POST | method '/auth/login'")
    public Response requestUserLogin(Object model) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(model)
                .when()
                .post(METHOD_LOGIN);
    }

    @Step("request PATCH | method '/auth/user' [with token]")
    public Response requestUserUpdate(Object model, String token) {
        return given().auth()
                .oauth2(token.replace("Bearer ", ""))
                .header("Content-type", "application/json")
                .and()
                .body(model)
                .when()
                .patch(METHOD_UPDATE);
    }

    @Step("request PATCH | method '/auth/user' [without token]")
    public Response requestUserUpdate(Object model) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(model)
                .when()
                .patch(METHOD_UPDATE);
    }
}
