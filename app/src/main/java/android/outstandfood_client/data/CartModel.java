package android.outstandfood_client.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class CartModel {
    @PrimaryKey
    @NonNull
    private String id;

    private String nameFood;
    private Double priceFood;
    private int quantityFood;
    private String imgFood;
    public CartModel() {
        this.id = null;
    }

    public CartModel(@NonNull String id,String nameFood, Double priceFood, int quantityFood, String imgFood) {
        this.id = id;
        this.nameFood=nameFood;
        this.priceFood = priceFood;
        this.quantityFood = quantityFood;
        this.imgFood = imgFood;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getNameFood() {
        return nameFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public Double getPriceFood() {
        return priceFood;
    }

    public void setPriceFood(Double priceFood) {
        this.priceFood = priceFood;
    }

    public int getQuantityFood() {
        return quantityFood;
    }

    public void setQuantityFood(int quantityFood) {
        this.quantityFood = quantityFood;
    }

    public String getImgFood() {
        return imgFood;
    }

    public void setImgFood(String imgFood) {
        this.imgFood = imgFood;
    }

    @NonNull
    @Override
    public String toString() {
        return id+"";
    }
}

