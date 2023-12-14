package android.outstandfood_client.models;

import java.io.Serializable;

public class Category implements Serializable {
    private String _id;
    private String name;
    private String describe;

    public Category(String _id, String name, String describe) {
        this._id = _id;
        this.name = name;
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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
