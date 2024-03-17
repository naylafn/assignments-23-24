package main.java.assignments.assignment2;

import java.util.ArrayList;

public class Order {
    private String orderID;
    private String tanggalPemesanan;
    private int biayaOngkosKirim;
    private Restaurant restaurant;
    private ArrayList<Menu> items;
    boolean orderFinished;

    public Order(String orderId, String tanggal, int ongkir, Restaurant resto, ArrayList<Menu> items){
        this.orderID = orderId;
        this.tanggalPemesanan = tanggal;
        this.biayaOngkosKirim = ongkir;
        this.restaurant = resto;
        this.items = new ArrayList<>();
    }
    
    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
    public String getOrderID() {
        return orderID;
    }
    public void setTanggalPemesanan(String tanggalPemesanan) {
        this.tanggalPemesanan = tanggalPemesanan;
    }
    public String getTanggalPemesanan() {
        return tanggalPemesanan;
    }
    public void setBiayaOngkosKirim(int biayaOngkosKirim) {
        this.biayaOngkosKirim = biayaOngkosKirim;
    }
    public int getBiayaOngkosKirim() {
        return biayaOngkosKirim;
    }
    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
    public Restaurant getRestaurant() {
        return restaurant;
    }
    public ArrayList<Menu> getItems() {
        return items;
    }
    public void setItems(ArrayList<Menu> items) {
        this.items = items;
    }

}
