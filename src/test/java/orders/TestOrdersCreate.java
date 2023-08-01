package orders;

import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import io.restassured.response.Response;

import java.util.ArrayList;
import functions.user.FunctionsUserApi;
import io.qameta.allure.junit4.DisplayName;
import functions.orders.FunctionsOrdersApi;
import models.response.user.UserResponseModel;
import org.junit.runners.Parameterized.Parameters;
import models.response.orders.base.OrdersResponseModel;
import models.response.orders.auth.OrdersResponseAuthModel;

import static functions.Utility.*;
import static functions.user.FunctionsUserApi.getUserDelete;

@RunWith(Parameterized.class)
public class TestOrdersCreate extends FunctionsOrdersApi {

    @Before
    public void domain() {
        apiEndPoint();
    }

    private final String name;
    private final String email;
    private final String password;
    private final String ingredientMain;
    private final String ingredientSauce;
    private UserResponseModel responseUserCreate;
    private OrdersResponseAuthModel responseOrdersAuth;

    private TestOrdersCreate(String name, String email, String password, String ingredientMain, String ingredientSauce) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.ingredientMain = ingredientMain;
        this.ingredientSauce = ingredientSauce;
    }

    @Parameters(name = "Тестовые данные: {0},{1},{2},{3},{4}")
    public static Object[][] getTestData() {
        return new Object[][]{
            {"UserQATest", "testUserQA@yandex.ru", "2unique1P@sw0rd", "61c0c5a71d1f82001bdaaa72", "61c0c5a71d1f82001bdaaa76"}
        };
    }

    FunctionsUserApi userCreate = new FunctionsUserApi();

    private ArrayList<String> addIngredient() {
        ArrayList<String> ingredients = new ArrayList<>();
        ingredients.add(ingredientMain);
        ingredients.add(ingredientSauce);
        return ingredients;
    }

    private void getUserCreate() {
        Response response = userCreate.getUserCreate(name, email, password);
        responseUserCreate = deserialize(response.getBody().asString(), UserResponseModel.class);
        checkStatusCode(response,200);
    }

    @Test
    @DisplayName("Создание заказа - без авторизации")
    public void ordersCreateWithOutAuth() {
        Response response = getOrdersCreate(addIngredient(), null);
        OrdersResponseModel responseOrdersCreate = deserialize(response.getBody().asString(),OrdersResponseModel.class);

        checkStatusCode(response,200);
        Assert.assertEquals("Spicy минеральный бургер", responseOrdersCreate.name);
    }

    @Test
    @DisplayName("Проверка поля [ingredients.name]")
    public void ordersCreateCheckIngredients() {
        getUserCreate();
        Response response = getOrdersCreate(addIngredient(), responseUserCreate.getAccessToken());
        responseOrdersAuth = deserialize(response.getBody().asString(),OrdersResponseAuthModel.class);

        checkStatusCode(response,200);
        getUserDelete(responseUserCreate.getAccessToken());
        Assert.assertEquals("Соус Spicy-X", responseOrdersAuth.order.ingredients.get(0).name);
    }

    @Test
    @DisplayName("Проверка поля [order.name]")
    public void ordersCreateCheckOrder() {
        getUserCreate();
        Response response = getOrdersCreate(addIngredient(), responseUserCreate.getAccessToken());
        responseOrdersAuth = deserialize(response.getBody().asString(), OrdersResponseAuthModel.class);

        checkStatusCode(response,200);
        getUserDelete(responseUserCreate.getAccessToken());
        Assert.assertEquals("Spicy минеральный бургер", responseOrdersAuth.order.name);
    }

    @Test
    @DisplayName("Создание заказа - без ингредиентов")
    public void ordersCreateWithOutParams() {
        Response response = getOrdersCreate(new ArrayList<>(), null);
        checkStatusCode(response,400);
        Assert.assertTrue(response.getBody().asString().contains("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создание заказа - с неверным хешем ингредиентов")
    public void ordersCreateUnValidHash() {
        ArrayList<String> ingredients = new ArrayList<>();
        ingredients.add(ingredientMain + "/");
        Response response = getOrdersCreate(ingredients, null);
        checkStatusCode(response, 500);
        Assert.assertTrue(response.getBody().asString().contains("Internal Server Error"));
    }
}