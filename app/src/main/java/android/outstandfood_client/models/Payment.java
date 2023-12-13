package android.outstandfood_client.models;

public class Payment {
    private String _id;
    private String method;

    public Payment(String _id, String method) {
        this._id = _id;
        this.method = method;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
