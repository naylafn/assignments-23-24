package assignments.assignment2;

import java.util.ArrayList;

public class User {
    private String nama;
    private String nomorTelepon;
    private String email;
    private String lokasi;
    private String role;
    private ArrayList<Order> orderHistory = new ArrayList<>();

    public User(String nama, String nomorTelepon, String email, String lokasi, String role){
        this.nama = nama;
        this.nomorTelepon = nomorTelepon;
        this.email = email;
        this.lokasi = lokasi;
        this.role = role;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
    public String getNama() {
        return nama;
    }
    public void setNomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }
    public String getNomorTelepon() {
        return nomorTelepon;
    }
    public void setEmail(String email) {
        this.email = email;
    }public String getEmail() {
        return email;
    }public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }public String getLokasi() {
        return lokasi;
    }public void setOrderHistory(ArrayList<Order> orderHistory) {
        this.orderHistory = orderHistory;
    }public ArrayList<Order> getOrderHistory() {
        return orderHistory;
    }public void setRole(String role) {
        this.role = role;
    }public String getRole() {
        return role;
    }
}
