package android.outstandfood_client.interfaces;

import android.outstandfood_client.data.CartModel;
import android.outstandfood_client.models.Product;

public interface FoodInterface {
    void showFood(String id,String name);
    void addFood(Product product);

    void DetailFood(Product product);
}
