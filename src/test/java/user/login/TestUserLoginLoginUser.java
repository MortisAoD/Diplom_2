package user.login;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import io.restassured.response.Response;
import org.junit.runners.Parameterized.Parameters;

import functions.user.FunctionsUserApi;
import models.response.user.UserResponseModel;
import io.qameta.allure.junit4.DisplayName;

import static functions.Utility.checkStatusCode;
import static functions.Utility.deserialize;

@RunWith(Parameterized.class)
public class TestUserLoginLoginUser extends FunctionsUserApi {

    private final String name;
    private final String email;
    private final String password;
    private UserResponseModel responseLogin;
    private UserResponseModel responseCreate;
    FunctionsUserApi userCreate = new FunctionsUserApi();

    private TestUserLoginLoginUser(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Parameters(name = "Тестовые данные: {0},{1},{2}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {"testsUsers", "testUsers@yandex.ru", "uniqueP@sw0rds"}
        };
    }

    private void getUserLogin(){
        Response response = userCreate.getUserCreate(name, email, password);
        checkStatusCode(response,200);
        responseCreate = deserialize(response.getBody().asString(), UserResponseModel.class);
    }

    @Before
    public void domain() {
        apiEndPoint();
        getUserLogin();
    }

    @Test
    @DisplayName("Авторизация пользователя - авторизация пользователя")
    public void userLoginCheckAuth() {
        Response response = getUserLogin(email, password);
        responseLogin = deserialize(response.getBody().asString(), UserResponseModel.class);
        checkStatusCode(response,200);
        Assert.assertTrue(responseLogin.success);
    }

    @Test
    @DisplayName("Авторизация пользователя - авторизация с неверным [email]")
    public void userLoginCheckUnValidEmail() {
        Response response = getUserLogin(email + "/", password);
        responseLogin = deserialize(response.getBody().asString(), UserResponseModel.class);
        checkStatusCode(response,401);
        Assert.assertFalse(responseLogin.success);
    }

    @Test
    @DisplayName("Авторизация пользователя - авторизация с неверным [password]")
    public void userLoginCheckUnValidPassword() {
        Response response = getUserLogin(email, password + "/");
        responseLogin = deserialize(response.getBody().asString(), UserResponseModel.class);
        checkStatusCode(response,401);
        Assert.assertFalse(responseLogin.success);
    }

    @After
    public void userDelete() {
        getUserDelete(responseCreate.getAccessToken());
    }
}