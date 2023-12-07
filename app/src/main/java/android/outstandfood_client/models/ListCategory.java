package android.outstandfood_client.models;

import java.util.ArrayList;
import java.util.List;

public class ListCategory {
    private ArrayList<Category> category;
    private String msg;

    public ListCategory(ArrayList<Category> category, String msg) {
        this.category = category;
        this.msg = msg;
    }

    public ArrayList<Category> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<Category> category) {
        this.category = category;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
