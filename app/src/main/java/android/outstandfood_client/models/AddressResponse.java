package android.outstandfood_client.models;

import android.location.Address;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddressResponse {
    private List<AddressModel> address;
    private String msg;

    // getters và setters
    public List<AddressModel> getAddress() {
        return address;
    }

    public void setAddress(List<AddressModel> address) {
        this.address = address;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

