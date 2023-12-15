package android.outstandfood_client.view.screen.fragment.home;

public class Food {
    int id;
    int id_loai;
    String avt;
    String name;
    double khoangCach;
    double danhGia;
    int soNguoiDanhGia;
    double gia;
    double phiShip;


    public Food(String avt, String name, double khoangCach, double danhGia, int soNguoiDanhGia, double gia, double phiShip) {
        this.avt = avt;
        this.name = name;
        this.khoangCach = khoangCach;
        this.danhGia = danhGia;
        this.soNguoiDanhGia = soNguoiDanhGia;
        this.gia = gia;
        this.phiShip = phiShip;
    }

    public int getId_loai() {
        return id_loai;
    }

    public void setId_loai(int id_loai) {
        this.id_loai = id_loai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvt() {
        return avt;
    }

    public void setAvt(String avt) {
        this.avt = avt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getKhoangCach() {
        return khoangCach;
    }

    public void setKhoangCach(double khoangCach) {
        this.khoangCach = khoangCach;
    }

    public double getDanhGia() {
        return danhGia;
    }

    public void setDanhGia(double danhGia) {
        this.danhGia = danhGia;
    }

    public int getSoNguoiDanhGia() {
        return soNguoiDanhGia;
    }

    public void setSoNguoiDanhGia(int soNguoiDanhGia) {
        this.soNguoiDanhGia = soNguoiDanhGia;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public double getPhiShip() {
        return phiShip;
    }

    public void setPhiShip(double phiShip) {
        this.phiShip = phiShip;
    }
}
