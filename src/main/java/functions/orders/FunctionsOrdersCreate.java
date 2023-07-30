package functions.orders;

import java.util.ArrayList;
import api.orders.OrdersCreate;
import io.restassured.response.Response;
import models.request.OrdersCreateRequestModel;

public class FunctionsOrdersCreate extends OrdersCreate {
    public Response getOrdersCreate(ArrayList<String> ingredients, String token) {
        OrdersCreateRequestModel requestModel = new OrdersCreateRequestModel(ingredients);
        return token == null ? requestOrdersCreate(requestModel) : requestOrdersCreate(requestModel, token);
    }
}