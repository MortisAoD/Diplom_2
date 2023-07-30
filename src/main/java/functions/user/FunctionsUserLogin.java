package functions.user;

import api.user.UserLogin;
import io.restassured.response.Response;
import models.request.UserLoginRequestModel;

public class FunctionsUserLogin extends UserLogin {
    public Response getUserLogin(String email, String password){
        UserLoginRequestModel requestModel = new UserLoginRequestModel(email,password);
        return requestUserLogin(requestModel);
    }
}