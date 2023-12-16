package android.outstandfood_client.models;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {
    private String _id;
    private String name;
    private int price;
    private String describe;
    private List<String> imageDetail;
    private String image;
    private int _v;
    private int quantity;
    private Category id_category;

    private String category_name;

    public Product(String _id, String name, int price, String describe, List<String> imageDetail, String image, int _v, int quantity, Category id_category, String category_name) {
        this._id = _id;
        this.name = name;
        this.price = price;
        this.describe = describe;
        this.imageDetail = imageDetail;
        this.image = image;
        this._v = _v;
        this.quantity = quantity;
        this.id_category = id_category;
        this.category_name = category_name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public List<String> getImageDetail() {
        return imageDetail;
    }

    public void setImageDetail(List<String> imageDetail) {
        this.imageDetail = imageDetail;
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

    public int get_v() {
        return _v;
    }

    public void set_v(int _v) {
        this._v = _v;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Category getId_category() {
        return id_category;
    }

    public void setId_category(Category id_category) {
        this.id_category = id_category;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
