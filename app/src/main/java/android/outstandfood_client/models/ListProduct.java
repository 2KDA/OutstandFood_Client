package android.outstandfood_client.models;

import java.util.ArrayList;

public class ListProduct {
    private ArrayList<Product> product;

    private String msg;

    public ListProduct(ArrayList<Product> product, String msg) {
        this.product = product;
        this.msg = msg;
    }

    public ListProduct() {
    }

    public ArrayList<Product> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Product> product) {
        this.product = product;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
