package main.java.assignments.assignment2;

import java.util.ArrayList;
import java.util.Scanner;
// import assignments.assignment1.*;

public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    private static ArrayList<Restaurant> restoList;
    private static ArrayList<User> userList;

    public static void main(String[] args) {
        restoList = new ArrayList<>();
        boolean programRunning = true;
        initUser();

        while(programRunning){
            printHeader();
            startMenu();
            int command = input.nextInt();
            input.nextLine();

            if(command == 1){
                boolean isValid = false;
                String nama = "";
                String noTelp = "";

                while(!isValid){
                    System.out.println("\nSilakan Login:");
                    System.out.print("Nama: ");
                    nama = input.nextLine();
                    System.out.print("Nomor Telepon: ");
                    noTelp = input.nextLine();

                    isValid = false; // Reset isValid for each login attempt
                    for (User user : userList) {
                        if(nama.trim().equalsIgnoreCase(user.getNama().trim()) && noTelp.trim().equals(user.getNomorTelepon().trim())){
                            isValid = true;
                            break;
                        } else {
                            continue;
                        }
                    }
                    if (!isValid){
                        System.out.println("Pengguna dengan data tersebut tidak ditemukan!");
                    }
                }

                boolean isLoggedIn = true;
                User userLoggedIn;
                userLoggedIn = getUser(nama, noTelp);

                if(userLoggedIn.getRole() == "Customer"){
                    while (isLoggedIn){
                        menuCustomer();
                        int commandCust = input.nextInt();
                        input.nextLine();

                        switch(commandCust){
                            case 1 -> handleBuatPesanan();
                            case 2 -> handleCetakBill();
                            case 3 -> handleLihatMenu();
                            case 4 -> handleUpdateStatusPesanan();
                            case 5 -> isLoggedIn = false;
                            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
                        }
                    }
                }else{
                    while (isLoggedIn){
                        menuAdmin();
                        int commandAdmin = input.nextInt();
                        input.nextLine();

                        switch(commandAdmin){
                            case 1 -> handleTambahRestoran();
                            case 2 -> handleHapusRestoran();
                            case 3 -> isLoggedIn = false;
                            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
                        }
                    }
                }
            }else if(command == 2){
                programRunning = false;
            }else{
                System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
        System.out.println("\nTerima kasih telah menggunakan DepeFood ^___^");
    }

    public static User getUser(String nama, String nomorTelepon){
        User validUser = null;
        for (User user : userList) {
            if(nama.trim().equalsIgnoreCase(user.getNama().trim()) && nomorTelepon.trim().equals(user.getNomorTelepon().trim())){
                validUser = user; 
                break;
            }
        }
        return validUser;
    }

    public static void handleBuatPesanan(){
        String namaRestoran;
        String tanggalOrder;

        System.out.println("\n------------Buat Pesanan------------");
        boolean isValid = false;

        while(!isValid){
            System.out.print("Nama Restoran: ");
            namaRestoran = input.nextLine();
            if (!OrderGenerator.validateRestaurantName(namaRestoran)) {
                System.out.println("Nama Restoran tidak valid!\n");
                continue;
            }

            boolean found = false;
            Restaurant rightRestaurant = null;
            for (Restaurant restaurant : restoList) {
                if(namaRestoran.trim().equalsIgnoreCase(restaurant.getNama())){
                    found = true;
                    rightRestaurant = restaurant;
                    break;
                }
            }

            System.out.println(rightRestaurant.getNama());
            ArrayList<Menu> thisMenu = rightRestaurant.getMenu();
            if(thisMenu == null){
                System.out.println("KOSONG BJIRR");
            }

            for (Menu menu : thisMenu) {
                System.out.println(menu.getNamaMakanan());
            }

            if(!found){
                System.out.println("Restoran tidak terdaftar dalam sistem.\n");
                continue;
            }

            System.out.print("Tanggal Pemesanan: ");
            tanggalOrder = input.nextLine();

            if (!OrderGenerator.validateDate(tanggalOrder)) {
                System.out.println("Tanggal Pemesanan dalam format DD/MM/YYYY!\n");
                continue;
            }

            System.out.print("Jumlah Pesanan: ");
            String jumlahOrder = input.nextLine();

            if(!jumlahOrder.chars().allMatch(Character::isDigit)){
                System.out.println("Jumlah pesanan harus bilangan bulat.\n");
                continue;
            }

            ArrayList<Menu> daftarMakanan = rightRestaurant.getMenu();  // Get menu dari restoran yang dipilih
            ArrayList<Menu> orderedItems = new ArrayList<>();
            String orderMakanan;

            System.out.println("Order: ");
            boolean menuAvailable = false;
            for(int i = 0; i < Integer.parseInt(jumlahOrder); i++){
                orderMakanan = input.nextLine();

                // Mencari nama makanan dalam menu restoran
                menuAvailable = false;
                for (Menu menuMakanan : daftarMakanan) {
                    System.out.println("Menu: " + menuMakanan.getNamaMakanan());
                    System.out.println(menuMakanan.getNamaMakanan().equalsIgnoreCase(orderMakanan));
                    System.out.println(menuMakanan.getNamaMakanan().trim().equalsIgnoreCase(orderMakanan));
                    if(menuMakanan.getNamaMakanan().trim().equalsIgnoreCase(orderMakanan)){
                        System.out.println("UDAH SAMAAAA NJING");
                        orderedItems.add(menuMakanan);
                        menuAvailable = true;
                        break;
                    }
                }

            }

            isValid = true;
        }

        
    }

    public static void handleCetakBill(){
        // TODO: Implementasi method untuk handle ketika customer ingin cetak bill
    }

    public static void handleLihatMenu(){
        // TODO: Implementasi method untuk handle ketika customer ingin melihat menu
    }

    public static void handleUpdateStatusPesanan(){
        // TODO: Implementasi method untuk handle ketika customer ingin update status pesanan
    }

    public static void handleTambahRestoran(){
        String nama;
        int jumlahMakanan;
        double harga = 0.0;
        boolean validInput = false;

        do {
            System.out.println("\n---------Tambah Restoran---------");
            System.out.print("Nama: ");
            nama = input.nextLine();

            if(nama.length()<4){
                System.out.println("Nama restoran tidak valid!");
            } else if (restoList.isEmpty()){
                validInput = true;
                break;
            } else if (!restoList.isEmpty()){
                for (Restaurant restaurant : restoList) {
                    if (nama.equalsIgnoreCase(restaurant.getNama())){
                        validInput = false;
                        System.out.println("Restoran dengan nama " + nama + " telah terdaftar. \nMohon masukkan nama yang berbeda!\n");  
                        break;
                    } else {
                        validInput = true;
                    }
                }
            } 
        } while (nama.length() < 4 || !validInput);

        System.out.print("Jumlah Makanan: ");
        jumlahMakanan = input.nextInt();
        input.nextLine();

        ArrayList<Menu> newMenu = new ArrayList<>();

        for(int i = 0; i < jumlahMakanan; i++){
            validInput = false;
            while (!validInput) {
                String makananInput = input.nextLine();
                String[] isiMenu = makananInput.split(" ");

                String makanan = "";
                for (String string : isiMenu) {
                    if (string.matches("\\d+(\\.\\d+)?")){
                        harga = Double.parseDouble(string);
                        validInput = true;
                    } else {
                        makanan += string + " ";
                        validInput = false;  
                    }
                }

                if(!validInput){
                    System.out.println("Harga menu harus bilangan bulat!\n");
                    continue;
                }

                newMenu.add(new Menu(makanan, harga));
            }
        }
        Restaurant newResto = new Restaurant(nama, newMenu);
        System.out.println(newResto.getMenu());

        restoList.add(newResto);
        System.out.println("Restaurant "  + nama + " berhasil terdaftar.");

    //     for (Restaurant restaurant : restoList) {
    //         ArrayList<Menu> menu = restaurant.getMenu();
    //         System.out.println(restaurant.getNama());
    //         if(menu == null){
    //             System.out.println("KOSONGG");
    //         }
    //         for (Menu menu2 : menu) {
    //             System.out.println(menu2.getNamaMakanan());
    //         }
    //     }
    }   

    public static void handleHapusRestoran(){
        System.out.println("\n---------Hapus Restoran---------");
        System.out.print("Nama Restoran: ");
        String nama = input.nextLine();

        int index = 0;
        boolean restoFound = false;
        for (Restaurant restaurant : restoList) {
            if(nama.equalsIgnoreCase(restaurant.getNama())){
                restoList.remove(index);
                System.out.println("Restoran berhasil dihapus.");
                restoFound = true;
                break;
            } else {
                restoFound = false;
            }
            ++index;
        }

        if(!restoFound){
            System.out.println("Restoran tidak terdaftar pada sistem.");
        }
    }

    public static void initUser(){
       userList = new ArrayList<User>();
       userList.add(new User("Thomas N", "9928765403", "thomas.n@gmail.com", "P", "Customer"));
       userList.add(new User("Sekar Andita", "089877658190", "dita.sekar@gmail.com", "B", "Customer"));
       userList.add(new User("Sofita Yasusa", "084789607222", "sofita.susa@gmail.com", "T", "Customer"));
       userList.add(new User("Dekdepe G", "080811236789", "ddp2.gampang@gmail.com", "S", "Customer"));
       userList.add(new User("Aurora Anum", "087788129043", "a.anum@gmail.com", "U", "Customer"));

       userList.add(new User("Admin", "123456789", "admin@gmail.com", "-", "Admin"));
       userList.add(new User("Admin Baik", "9123912308", "admin.b@gmail.com", "-", "Admin"));
    }

    public static void printHeader(){
        System.out.println("\n>>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
    }

    public static void startMenu(){
        System.out.println("Selamat datang di DepeFood!");
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Login");
        System.out.println("2. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static void menuAdmin(){
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Tambah Restoran");
        System.out.println("2. Hapus Restoran");
        System.out.println("3. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static void menuCustomer(){
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Buat Pesanan");
        System.out.println("2. Cetak Bill");
        System.out.println("3. Lihat Menu");
        System.out.println("4. Update Status Pesanan");
        System.out.println("5. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }
}
