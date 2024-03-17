package main.java.assignments.assignment2;

import java.util.ArrayList;

public class Restaurant {
    private String nama;
    private ArrayList<Menu> menu;

    public Restaurant(String nama, ArrayList<Menu> menu){
        this.nama = nama;
        this.menu = menu;
    }
    
    public void setNama(String nama) {
        this.nama = nama;
    }
    public String getNama() {
        return nama;
    }
    public void setMenu(ArrayList<Menu> menu) {
        this.menu = menu;
    }
    public ArrayList<Menu> getMenu() {
        return menu;
    }
}
