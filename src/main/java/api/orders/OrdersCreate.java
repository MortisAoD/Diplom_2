package api.orders;

import api.Request;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrdersCreate extends Request {
    private final static String method = "/orders";

    @Step("request POST | method '/orders' [without token]")
    public Response requestOrdersCreate(Object model) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(model)
                .when()
                .post(method);
    }

    @Step("request POST | method '/orders' [with token]")
    public Response requestOrdersCreate(Object model, String token) {
        return given().auth()
                .oauth2(token.replace("Bearer ", ""))
                .header("Content-type", "application/json")
                .and()
                .body(model)
                .when()
                .post(method);
    }
}