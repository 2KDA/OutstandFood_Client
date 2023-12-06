package android.outstandfood_client.models;

public class Product {
    private String _id;
    private String name;
    private Double price;
    private String image;
    private int __v;
    private int quantity;
    private Category id_category;

    private String category_name;

    public Product(String _id, String name, Double price, String image, int __v, int quantity, Category id_category, String category_name) {
        this._id = _id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.__v = __v;
        this.quantity = quantity;
        this.id_category = id_category;
        this.category_name = category_name;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
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
