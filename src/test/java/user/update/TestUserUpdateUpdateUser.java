package user.update;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import io.restassured.response.Response;
import org.junit.runners.Parameterized.Parameters;

import functions.user.FunctionsUserUpdate;
import functions.user.FunctionsUserCreate;
import io.qameta.allure.junit4.DisplayName;
import models.response.user.UserResponseModel;
import models.response.user.UserErrorResponseModel;
import models.response.user.UserUpdateResponseModel;

import static functions.Utility.*;
import static functions.user.FunctionsUserDelete.getUserDelete;

@RunWith(Parameterized.class)
public class TestUserUpdateUpdateUser extends FunctionsUserUpdate {

    @Before
    public void domain() {
        apiEndPoint();
        getUserCreate();
    }

    private final String name;
    private final String email;
    private final String password;

    private final String updName;
    private final String updEmail;

    public TestUserUpdateUpdateUser(String name, String email, String password, String updName, String updEmail) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.updName = updName;
        this.updEmail = updEmail;
    }

    @Parameters(name = "Тестовые данные: {0},{1},{2},{3},{4}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {"testsUsers", "testUsers@yandex.ru", "uniqueP@sw0rds", "updateUsers", "updateUsers@yandex.ru"}
        };
    }

    private UserResponseModel responseCreate;
    private UserUpdateResponseModel responseUpdate;

    FunctionsUserCreate userCreate = new FunctionsUserCreate();

    public void getUserCreate(){
        Response response = userCreate.getUserCreate(name, email, password);
        responseCreate = deserialize(response.getBody().asString(), UserResponseModel.class);
        checkStatusCode(response,200);
    }

    @Test
    @DisplayName("Обновление пользователя - проверка обновления поля [name]")
    public void userUpdateCheckName() {
        Response response = getUserUpdate(updName, email, responseCreate.getAccessToken());
        responseUpdate = deserialize(response.getBody().asString(), UserUpdateResponseModel.class);
        checkStatusCode(response,200);
        Assert.assertEquals(updName, responseUpdate.user.name);
    }

    @Test
    @DisplayName("Обновление пользователя - проверка обновления поля [email]")
    public void userUpdateCheckEmail() {
        Response response = getUserUpdate(name, updEmail, responseCreate.getAccessToken());
        responseUpdate = deserialize(response.getBody().asString(), UserUpdateResponseModel.class);
        checkStatusCode(response,200);
        Assert.assertEquals(updEmail.toLowerCase(), responseUpdate.user.email.toLowerCase());
    }

    @Test
    @DisplayName("Обновление пользователя - проверка обновления поля [email]")
    public void userUpdateCheckWithOutAuth() {
        Response response = getUserUpdate(updName, updEmail, null);
        UserErrorResponseModel responseError = deserialize(response.getBody().asString(), UserErrorResponseModel.class);
        checkStatusCode(response,401);
        Assert.assertEquals("You should be authorised", responseError.message);
    }

    @After
    public void userDelete() {
        getUserDelete(responseCreate.getAccessToken());
    }
}