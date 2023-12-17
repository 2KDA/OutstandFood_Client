package android.outstandfood_client.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddressModel implements Serializable {
    @SerializedName("_id")
    private String _id;

    @SerializedName("id_user")
    private String id_user;

    @SerializedName("address")
    private String address;

    @SerializedName("phone")
    private String phone;

    public AddressModel(String _id, String id_user, String address, String phone) {
        this._id = _id;
        this.id_user = id_user;
        this.address = address;
        this.phone = phone;
    }


    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getUserId() {
        return id_user;
    }

    public void setUserId(String id_user) {
        this.id_user = id_user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @NonNull
    @Override
    public String toString() {
        return address;
    }
}