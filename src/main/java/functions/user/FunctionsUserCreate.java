package functions.user;

import api.user.UserCreate;
import io.restassured.response.Response;
import models.request.UserCreateRequestModel;

public class FunctionsUserCreate extends UserCreate {
    public Response getUserCreate(String name, String email, String password) {
        UserCreateRequestModel requestModel = new UserCreateRequestModel(name, email, password);
        return requestUserCreate(requestModel);
    }
}