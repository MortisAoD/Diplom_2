package functions.user;

import api.user.UserUpdate;
import io.restassured.response.Response;
import models.request.UserUpdateRequestModel;

public class FunctionsUserUpdate extends UserUpdate {
    public Response getUserUpdate(String name, String email, String token){
        UserUpdateRequestModel requestModel = new UserUpdateRequestModel(name,email);
        return token == null ? requestUserUpdate(requestModel) : requestUserUpdate(requestModel, token);
    }
}