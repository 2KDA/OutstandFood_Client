package android.outstandfood_client.models;

public class Ordered {
    private String _id;

    private User id_user;

    private Payment id_payment;
    private AddressModel id_address;

    private Double total_price;
    private Boolean pay_status;
    private String date;
    private String delivery_status;

    public Ordered(String _id, User id_user, Payment id_payment, AddressModel id_address, Double total_price, Boolean pay_status, String date, String delivery_status) {
        this._id = _id;
        this.id_user = id_user;
        this.id_payment = id_payment;
        this.id_address = id_address;
        this.total_price = total_price;
        this.pay_status = pay_status;
        this.date = date;
        this.delivery_status = delivery_status;
    }

    public AddressModel getId_address() {
        return id_address;
    }

    public void setId_address(AddressModel id_address) {
        this.id_address = id_address;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public User getId_user() {
        return id_user;
    }

    public void setId_user(User id_user) {
        this.id_user = id_user;
    }

    public Payment getId_payment() {
        return id_payment;
    }

    public void setId_payment(Payment id_payment) {
        this.id_payment = id_payment;
    }

    public Double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Double total_price) {
        this.total_price = total_price;
    }

    public Boolean getPay_status() {
        return pay_status;
    }

    public void setPay_status(Boolean pay_status) {
        this.pay_status = pay_status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDelivery_status() {
        return delivery_status;
    }

    public void setDelivery_status(String delivery_status) {
        this.delivery_status = delivery_status;
    }
}
