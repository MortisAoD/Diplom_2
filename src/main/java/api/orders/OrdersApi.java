package api.orders;

import api.Request;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrdersApi extends Request {
    private final static String METHOD_CREATE = "/orders";
    private final static String METHOD_GET = "/orders";

    @Step("request POST | method '/orders' [without token]")
    public Response requestOrdersCreate(Object model) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(model)
                .when()
                .post(METHOD_CREATE);
    }

    @Step("request POST | method '/orders' [with token]")
    public Response requestOrdersCreate(Object model, String token) {
        return given().auth()
                .oauth2(token.replace("Bearer ", ""))
                .header("Content-type", "application/json")
                .and()
                .body(model)
                .when()
                .post(METHOD_CREATE);
    }

    @Step("request GET | method '/orders' [without token]")
    public Response requestOrdersGet() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get(METHOD_GET);
    }

    @Step("request GET | method '/orders' [with token]")
    public Response requestOrdersGet(String token) {
        return given().auth()
                .oauth2(token.replace("Bearer ", ""))
                .header("Content-type", "application/json")
                .when()
                .get(METHOD_GET);
    }
}