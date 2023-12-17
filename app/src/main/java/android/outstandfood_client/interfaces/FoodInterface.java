package android.outstandfood_client.interfaces;

import android.outstandfood_client.data.CartModel;
import android.outstandfood_client.models.HistoryModel;
import android.outstandfood_client.models.Product;
import android.outstandfood_client.models.Rating;
import android.outstandfood_client.models.User;

public interface FoodInterface {
    void showFood(String id,String name);
    void addFood(Product product);
    void DetailFood(Product product);

    void addRating(User user);
}
