package functions.orders;

import api.orders.OrdersGet;
import io.restassured.response.Response;

public class FunctionsOrdersGet extends OrdersGet {
    public Response getOrders(String token) {
        return token == null ? requestOrdersGet() : requestOrdersGet(token);
    }
}