package functions.user;

import api.user.UserApi;
import io.restassured.response.Response;
import models.request.UserCreateRequestModel;
import models.request.UserLoginRequestModel;
import models.request.UserUpdateRequestModel;

public class FunctionsUserApi extends UserApi {
    public static void getUserDelete(String token){
        if (token != null) {
            UserApi.requestDelete(token);
        } else {
            UserApi.requestDelete();
        }
    }

    public Response getUserCreate(String name, String email, String password) {
        UserCreateRequestModel requestModel = new UserCreateRequestModel(name, email, password);
        return requestUserCreate(requestModel);
    }

    public Response getUserLogin(String email, String password){
        UserLoginRequestModel requestModel = new UserLoginRequestModel(email,password);
        return requestUserLogin(requestModel);
    }

    public Response getUserUpdate(String name, String email, String token){
        UserUpdateRequestModel requestModel = new UserUpdateRequestModel(name,email);
        return token == null ? requestUserUpdate(requestModel) : requestUserUpdate(requestModel, token);
    }
}