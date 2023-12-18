package android.outstandfood_client.models;

import java.io.Serializable;
import java.util.List;

public class ProductOrdered implements Serializable {
    private String _id;
    private String name;
    private int price;
    private String image;
    private String describe;
    private int __v;
    private int quantity;
    private String id_category;

    private String category_name;

    private List<String> imageDetail;

    public ProductOrdered(String _id, String name, int price, String image, String describe, int __v, int quantity, String id_category, String category_name, List<String> imageDetail) {
        this._id = _id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.describe = describe;
        this.__v = __v;
        this.quantity = quantity;
        this.id_category = id_category;
        this.category_name = category_name;
        this.imageDetail = imageDetail;
    }

    public List<String> getImageDetail() {
        return imageDetail;
    }

    public void setImageDetail(List<String> imageDetail) {
        this.imageDetail = imageDetail;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getId_category() {
        return id_category;
    }

    public void setId_category(String id_category) {
        this.id_category = id_category;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
