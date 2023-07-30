package api.orders;

import api.Request;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
public class OrdersGet extends Request {

    private final static String method = "/orders";

    @Step("request GET | method '/orders' [without token]")
    public Response requestOrdersGet() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get(method);
    }

    @Step("request GET | method '/orders' [with token]")
    public Response requestOrdersGet(String token) {
        return given().auth()
                .oauth2(token.replace("Bearer ", ""))
                .header("Content-type", "application/json")
                .when()
                .get(method);
    }
}