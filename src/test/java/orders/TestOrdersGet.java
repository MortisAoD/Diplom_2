package orders;

import models.response.orders.auth.OrdersGetResponseModel;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import io.restassured.response.Response;
import org.junit.runners.Parameterized.Parameters;

import java.util.ArrayList;
import functions.user.FunctionsUserCreate;
import io.qameta.allure.junit4.DisplayName;
import functions.orders.FunctionsOrdersGet;
import models.response.user.UserResponseModel;
import functions.orders.FunctionsOrdersCreate;

import static functions.Utility.checkStatusCode;
import static functions.Utility.deserialize;
import static functions.user.FunctionsUserDelete.getUserDelete;

@RunWith(Parameterized.class)
public class TestOrdersGet extends FunctionsOrdersGet {

    @Before
    public void domain() {
        apiEndPoint();
        getCreateUser();
        getCreateOrder();
    }

    private final String name;
    private final String email;
    private final String password;
    private final String ingredient;

    public TestOrdersGet(String name, String email, String password, String ingredient) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.ingredient = ingredient;
    }

    @Parameters(name = "Тестовые данные: {0},{1},{2},{3}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {"UserQATest", "testUserQA@yandex.ru", "2unique1P@sw0rd", "61c0c5a71d1f82001bdaaa72"}
        };
    }

    private UserResponseModel responseCreate;
    private OrdersGetResponseModel responseGet;

    FunctionsUserCreate userCreate = new FunctionsUserCreate();
    FunctionsOrdersCreate ordersCreate = new FunctionsOrdersCreate();

    public void getCreateUser(){
        Response response = userCreate.getUserCreate(name, email, password);
        responseCreate = deserialize(response.getBody().asString(), UserResponseModel.class);
        checkStatusCode(response,200);
    }

    public void getCreateOrder(){
        Response response = ordersCreate.getOrdersCreate(ingredients(), responseCreate.getAccessToken());
        checkStatusCode(response,200);
    }

    public ArrayList<String> ingredients(){
        ArrayList<String> addiIngredient = new ArrayList<>();
        addiIngredient.add(ingredient);
        return addiIngredient;
    }

    @Test
    @DisplayName("Получение заказа пользователя - пользователь авторизован, проверка поля[success]")
    public void orderGetSuccess() {
        Response response = getOrders(responseCreate.getAccessToken());
        responseGet = deserialize(response.getBody().asString(), OrdersGetResponseModel.class);
        checkStatusCode(response,200);
        Assert.assertTrue(responseGet.success);
    }

    @Test
    @DisplayName("Получение заказов конкретного пользователя - авторизованный пользователь, проверка поля[name]")
    public void orderGetName() {
        Response response = getOrders(responseCreate.getAccessToken());
        responseGet = deserialize(response.getBody().asString(), OrdersGetResponseModel.class);
        checkStatusCode(response,200);
        Assert.assertEquals("Spicy бургер", responseGet.orders.get(0).name);
    }

    @Test
    @DisplayName("Получение заказов конкретного пользователя - авторизованный пользователь, проверка поля[name]")
    public void orderGetStatus() {
        Response response = getOrders(responseCreate.getAccessToken());
        responseGet = deserialize(response.getBody().asString(), OrdersGetResponseModel.class);
        checkStatusCode(response,200);
        Assert.assertEquals("done", responseGet.orders.get(0).status);
    }

    @Test
    @DisplayName("Получение заказов конкретного пользователя - авторизованный пользователь, проверка поля[name]")
    public void orderGetIngredients() {
        Response response = getOrders(responseCreate.getAccessToken());
        responseGet = deserialize(response.getBody().asString(), OrdersGetResponseModel.class);
        checkStatusCode(response,200);
        Assert.assertEquals(ingredient, responseGet.orders.get(0).ingredients.get(0));
    }

    @Test
    @DisplayName("Получение заказов конкретного пользователя - авторизованный пользователь, проверка поля[name]")
    public void orderGetError() {
        Response response = getOrders(null);
        checkStatusCode(response,401);
        Assert.assertTrue(response.getBody().asString().contains("You should be authorised"));
    }

    @After
    public void userDelete() {
        getUserDelete(responseCreate.getAccessToken());
    }
}