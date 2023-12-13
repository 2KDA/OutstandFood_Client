package android.outstandfood_client.models;

import java.util.ArrayList;

public class HistoryModel {
    private Ordered ordered;
    private ArrayList<ListDetail> listDetail;

    public HistoryModel(Ordered ordered, ArrayList<ListDetail> listDetail) {
        this.ordered = ordered;
        this.listDetail = listDetail;
    }

    public Ordered getOrdered() {
        return ordered;
    }

    public void setOrdered(Ordered ordered) {
        this.ordered = ordered;
    }

    public ArrayList<ListDetail> getListDetail() {
        return listDetail;
    }

    public void setListDetail(ArrayList<ListDetail> listDetail) {
        this.listDetail = listDetail;
    }
}
