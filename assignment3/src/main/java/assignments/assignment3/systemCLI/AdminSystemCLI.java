package assignments.assignment3.systemCLI;

import java.util.ArrayList;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.Scanner;
import assignments.assignment2.*;
import assignments.assignment3.User;
import assignments.assignment3.MainMenu;

public class AdminSystemCLI extends UserSystemCLI{
    private static ArrayList<Restaurant> restoList = MainMenu.getRestoList();
    private Scanner input = new Scanner(System.in);

    @Override
    public boolean handleMenu(int command){
        switch(command){
            case 1 -> handleTambahRestoran();
            case 2 -> handleHapusRestoran();
            case 3 -> {return false;}
            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
        }
        return true;
    }

    @Override
    public void displayMenu() {
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Tambah Restoran");
        System.out.println("2. Hapus Restoran");
        System.out.println("3. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    protected void handleTambahRestoran(){
        System.out.println("--------------Tambah Restoran---------------");
        Restaurant restaurant = null;
        String namaRestaurant = "";
        boolean isValid = true;
        while (restaurant == null || !isValid) {
            namaRestaurant = "";
            System.out.print("Masukkan nama restoran: ");
            namaRestaurant = input.nextLine().trim();
            if(!restoList.isEmpty()){
                for (Restaurant restaurantInList : restoList) {
                    if(namaRestaurant.equalsIgnoreCase(restaurantInList.getNama())){
                        isValid = false;
                        System.out.println("Restoran dengan nama " + namaRestaurant + " sudah pernah terdaftar. Mohon masukkan nama yang berbeda");
                        break;
                    }
                }
            } 
            if(namaRestaurant.length()<4){
                isValid = false;
                System.out.println("Nama Restoran tidak valid! Minimal 4 karakter diperlukan.");
            }
        }
        restaurant = new Restaurant(namaRestaurant);
        restaurant = MainMenu.handleTambahMenuRestaurant(restaurant);
        restoList.add(restaurant);
        System.out.print("Restaurant "+restaurant.getNama()+" Berhasil terdaftar.\n" );
    }

    protected void handleHapusRestoran(){
        boolean isValid = false;
        while(!isValid){
            System.out.println("\n---------Hapus Restoran---------");
            System.out.print("Nama Restoran: ");
            String nama = input.nextLine();
    
            int index = 0;
            for (Restaurant restaurant : restoList) {
                if(nama.equalsIgnoreCase(restaurant.getNama())){
                    restoList.remove(index);    // Menghapus restaurant
                    System.out.println("Restoran berhasil dihapus.");
                    isValid = true;
                    break;
                }
                ++index;    // Index counter
            }
            if(!isValid){
                System.out.println("Restoran tidak terdaftar pada sistem.");
            }
        }
    }
}
