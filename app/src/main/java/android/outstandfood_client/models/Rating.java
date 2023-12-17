package android.outstandfood_client.models;

import java.lang.reflect.Array;
import java.util.List;

public class Rating {
    private String _id;
    private String rating;
    private Object id_user;
    private Object id_product;

//    private String id_user;
//    private String id_product;
    private String user_username;
    private String user_name;
    private String product_name;

    public Rating(String _id, String rating, Object id_user, Object id_product, String user_username, String user_name, String product_name) {
        this._id = _id;
        this.rating = rating;
        this.id_user = id_user;
        this.id_product = id_product;
        this.user_username = user_username;
        this.user_name = user_name;
        this.product_name = product_name;
    }

//    public Rating(String _id, String rating, String id_user, String id_product, String user_username, String user_name, String product_name) {
//        this._id = _id;
//        this.rating = rating;
//        this.id_user = id_user;
//        this.id_product = id_product;
//        this.user_username = user_username;
//        this.user_name = user_name;
//        this.product_name = product_name;
//    }

    public Rating() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setId_product(Object id_product) {
        this.id_product = id_product;
    }

    public Object getId_user() {
        return id_user;
    }

    public void setId_user(Object id_user) {
        this.id_user = id_user;
    }

    public Object getId_product() {
        return id_product;
    }

    public String getUser_username() {
        return user_username;
    }

    public void setUser_username(String user_username) {
        this.user_username = user_username;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
}
