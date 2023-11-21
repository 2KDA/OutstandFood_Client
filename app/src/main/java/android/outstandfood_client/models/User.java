package android.outstandfood_client.models;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String _id;
    private String username;
    private String name;
    private String password;
    private String role;
    private String image;
    private String phone;
    private boolean isActive;

    public User(String _id, String username, String name, String password, String role, String image, String phone, boolean isActive) {
        this._id = _id;
        this.username = username;
        this.name = name;
        this.password = password;
        this.role = role;
        this.image = image;
        this.phone = phone;
        this.isActive = isActive;

    }

    public User() {
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

    protected User(Parcel in) {
        username = in.readString();
        password = in.readString();
        _id = in.readString();
        name = in.readString();
        phone = in.readString();
        role = in.readString();
        image = in.readString();
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
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(_id);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(role);
        dest.writeString(image);
        dest.writeInt(isActive ? 1 : 0); // Ghi giá trị boolean dưới dạng int
    }
}