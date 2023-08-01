package functions;

import com.google.gson.Gson;
import io.restassured.response.Response;

public class Utility {
    public static String toJsonString(Object modelClass) {
        Gson gson = new Gson();
        return gson.toJson(modelClass);
    }

    public static <T> T fromJsonString(String json, Class<T> modelClass) {
        Gson gson = new Gson();
        return gson.fromJson(json, modelClass);
    }

    public static void checkStatusCode(Response response, Integer code){
        response.then().statusCode(code);
    }
}