package models.request;

import java.util.ArrayList;

public class OrdersCreateRequestModel {

    public ArrayList<String> ingredients;

    public OrdersCreateRequestModel(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }
}