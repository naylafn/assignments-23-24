package main.java.assignments.assignment2;

public class Menu {
    private String namaMakanan;
    private double harga;

    public Menu(String namaMakanan, double harga){
        this.namaMakanan = namaMakanan;
        this.harga = harga;
    }

    public void setNamaMakanan(String namaMakanan) {
        this.namaMakanan = namaMakanan;
    }
    public String getNamaMakanan() {
        return namaMakanan;
    }
    public void setHarga(double harga) {
        this.harga = harga;
    }
    public double getHarga() {
        return harga;
    }
}
