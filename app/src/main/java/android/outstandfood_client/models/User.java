package android.outstandfood_client.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {
    @SerializedName("_id")
    private String _id;
    @SerializedName("username")
    private String username;
    @SerializedName("name")
    private String name;
    @SerializedName("password")
    private String password;
    @SerializedName("role")
    private String role;
    @SerializedName("userEmail")
    private String userEmail;
    @SerializedName("image")
    private String image;
    @SerializedName("phone")
    private String phone;
    @SerializedName("isActive")
    private boolean isActive;
    @SerializedName("deviceToken")
    private String deviceToken;

    public User(String _id, String username, String name, String password, String role, String userEmail, String image, String phone, boolean isActive, String deviceToken) {
        this._id = _id;
        this.username = username;
        this.name = name;
        this.password = password;
        this.role = role;
        this.userEmail = userEmail;
        this.image = image;
        this.phone = phone;
        this.isActive = isActive;
        this.deviceToken = deviceToken;
    }

    public User() {
    }

    public User( String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    protected User(Parcel in) {
        username = in.readString();
        password = in.readString();
        _id = in.readString();
        name = in.readString();
        phone = in.readString();
        role = in.readString();
        userEmail = in.readString();
        image = in.readString();
        deviceToken = in.readString();
        isActive = in.readInt() == 1; // Đọc giá trị boolean từ int
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(username);
        dest.writeString(name);
        dest.writeString(password);
        dest.writeString(role);
        dest.writeString(userEmail);
        dest.writeString(image);
        dest.writeString(phone);
        dest.writeInt(isActive ? 1 : 0);
        dest.writeString(deviceToken);
    }
}