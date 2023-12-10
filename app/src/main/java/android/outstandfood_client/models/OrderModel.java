package android.outstandfood_client.models;

import java.util.ArrayList;

public class OrderModel {
    private String id_user;
    private Boolean pay_status;
    private String id_address;
    private String method;
    private ArrayList<Integer> quantity;
    private ArrayList<String> id_product;

    public OrderModel() {
    }

    public OrderModel(String id_user, Boolean pay_status, String address, String method, ArrayList<Integer> quantity, ArrayList<String> id_product) {
        this.id_user = id_user;
        this.pay_status = pay_status;
        this.id_address = address;
        this.method = method;
        this.quantity = quantity;
        this.id_product = id_product;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public Boolean getPay_status() {
        return pay_status;
    }

    public void setPay_status(Boolean pay_status) {
        this.pay_status = pay_status;
    }

    public String getAddress() {
        return id_address;
    }

    public void setAddress(String address) {
        this.id_address = address;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public ArrayList<Integer> getQuantity() {
        return quantity;
    }

    public void setQuantity(ArrayList<Integer> quantity) {
        this.quantity = quantity;
    }

    public ArrayList<String> getId_product() {
        return id_product;
    }

    public void setId_product(ArrayList<String> id_product) {
        this.id_product = id_product;
    }
}
