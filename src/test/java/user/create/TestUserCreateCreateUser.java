package user.create;

import io.restassured.response.Response;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import functions.user.FunctionsUserApi;
import io.qameta.allure.junit4.DisplayName;
import models.response.user.UserResponseModel;

import static functions.Utility.checkStatusCode;
import static functions.Utility.fromJsonString;

@RunWith(Parameterized.class)
public class TestUserCreateCreateUser extends FunctionsUserApi {

    private final String name;
    private final String email;
    private final String password;
    private UserResponseModel responseModel;

    public TestUserCreateCreateUser(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Parameters(name = "Тестовые данные: {0},{1},{2}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {"testsUser", "testUser@yandex.ru", "uniqueP@sw0rd"}
        };
    }

    private void getUserCreate() {
        Response response = getUserCreate(name, email, password);
        responseModel = fromJsonString(response.getBody().asString(), UserResponseModel.class);
        checkStatusCode(response,200);
    }

    @Before
    public void domain() {
        apiEndPoint();
        getUserCreate();
    }

    @Test
    @DisplayName("Создание пользователя - проверка поля [name]")
    public void userCreateCheckName() {
        Assert.assertEquals(name, responseModel.getUser().name);
    }

    @Test
    @DisplayName("Создание пользователя - проверка поля [email]")
    public void userCreateCheckEmail() {
        Assert.assertEquals(email.toLowerCase(), responseModel.getUser().email.toLowerCase());
    }

    @Test
    @DisplayName("Создание пользователя - проверка поля [success]")
    public void userCreateCheckStatusResult() {
        Assert.assertTrue(responseModel.success);
    }

    @Test
    @DisplayName("Создание пользователя - проверка содержания [accessToken]")
    public void userCreateCheckAccessToken() {
        Assert.assertFalse(responseModel.getAccessToken().isEmpty());
    }

    @Test
    @DisplayName("Создание пользователя - проверка содержания [refreshToken]")
    public void userCreateCheckRefreshToken() {
        Assert.assertFalse(responseModel.getRefreshToken().isEmpty());
    }

    @After
    public void userDelete() {
        getUserDelete(responseModel.getAccessToken());
    }
}