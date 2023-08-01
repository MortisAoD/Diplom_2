package functions.orders;

import java.util.ArrayList;
import api.orders.OrdersApi;
import io.restassured.response.Response;
import models.request.OrdersCreateRequestModel;

public class FunctionsOrdersApi extends OrdersApi {
    public Response getOrdersCreate(ArrayList<String> ingredients, String token) {
        OrdersCreateRequestModel requestModel = new OrdersCreateRequestModel(ingredients);
        return token == null ? requestOrdersCreate(requestModel) : requestOrdersCreate(requestModel, token);
    }

    public Response getOrders(String token) {
        return token == null ? requestOrdersGet() : requestOrdersGet(token);
    }
}