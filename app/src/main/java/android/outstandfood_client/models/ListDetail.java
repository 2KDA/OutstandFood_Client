package android.outstandfood_client.models;

public class ListDetail {
    private String _id;
    private ProductOrdered id_product;
    private Double quantity;

    private Double price;
    private Double total_price;
    private String id_order;

    public ListDetail(String _id, ProductOrdered id_product, Double quantity, Double price, Double total_price, String id_order) {
        this._id = _id;
        this.id_product = id_product;
        this.quantity = quantity;
        this.price = price;
        this.total_price = total_price;
        this.id_order = id_order;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public ProductOrdered getId_product() {
        return id_product;
    }

    public void setId_product(ProductOrdered id_product) {
        this.id_product = id_product;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Double total_price) {
        this.total_price = total_price;
    }

    public String getId_order() {
        return id_order;
    }

    public void setId_order(String id_order) {
        this.id_order = id_order;
    }
}
