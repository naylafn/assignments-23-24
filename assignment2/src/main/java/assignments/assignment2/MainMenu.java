package assignments.assignment2;

import java.util.ArrayList;
import java.util.List;
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

                    isValid = false; // Reset isValid
                    for (User user : userList) {
                        // Mencari nama user di userList
                        if(nama.trim().equalsIgnoreCase(user.getNama().trim()) && noTelp.trim().equals(user.getNomorTelepon().trim())){
                            isValid = true;
                            System.out.println("Selamat Datang " + user.role + "!");
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

                if(userLoggedIn.role == "Customer"){
                    while (isLoggedIn){
                        menuCustomer();
                        int commandCust = input.nextInt();
                        input.nextLine();

                        switch(commandCust){
                            case 1 -> handleBuatPesanan(userLoggedIn);
                            case 2 -> handleCetakBill(userLoggedIn);
                            case 3 -> handleLihatMenu();
                            case 4 -> handleUpdateStatusPesanan(userLoggedIn);
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
            // Mencari user yang valid sesuai nama dan nomor telepon yang diberikan
            if(nama.trim().equalsIgnoreCase(user.getNama().trim()) && nomorTelepon.trim().equals(user.getNomorTelepon().trim())){
                validUser = user; 
                break;
            }
        }
        return validUser;   // Mengembalikan object User yang valid
    }

    public static void handleBuatPesanan(User validUser){
        System.out.println("--------------Buat Pesanan----------------");
        while (true) {
            System.out.print("Nama Restoran: ");
            String restaurantName = input.nextLine().trim();
            Restaurant restaurant = null;
            for (Restaurant restaurantInList : restoList) {
                if(restaurantName.equalsIgnoreCase(restaurantName)){
                    restaurant = restaurantInList;
                }
            }
            if(restaurant == null){
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }
            System.out.print("Tanggal Pemesanan (DD/MM/YYYY): ");
            String tanggalPemesanan = input.nextLine().trim();
            if(!OrderGenerator.validateDate(tanggalPemesanan)){
                System.out.println("Masukkan tanggal sesuai format (DD/MM/YYYY)");
                System.out.println();
                continue;
            }
            System.out.print("Jumlah Pesanan: ");
            int jumlahPesanan = Integer.parseInt(input.nextLine().trim());
            System.out.println("Order: ");
            List<String> listMenuPesananRequest = new ArrayList<>();
            for(int i=0; i < jumlahPesanan; i++){
                listMenuPesananRequest.add(input.nextLine().trim());
            }
            if(!validateRequestPesanan(restaurant, listMenuPesananRequest)){
                System.out.println("Mohon memesan menu yang tersedia di Restoran!");
                continue;
            };
            Order order = new Order(
                    OrderGenerator.generateOrderID(restaurantName, tanggalPemesanan, validUser.getNomorTelepon()),
                    tanggalPemesanan, 
                    OrderGenerator.calculateDeliveryCost(validUser.getLokasi()), 
                    restaurant, 
                    MainMenu.getMenuRequest(restaurant, listMenuPesananRequest));
            System.out.printf("Pesanan dengan ID %s diterima!", order.getOrderId());
            System.out.println();
            validUser.addOrderHistory(order);
            return;
        }

    }

    public static void handleCetakBill(User validUser){
        Boolean isValid = false;
        Order validOrder = null;

        while(!isValid){
            System.out.println("\n---------------Cetak Bill---------------");
            System.out.print("Masukkan Order ID: ");
            String orderID = input.nextLine();
            // Mencari order ID di orderHistory
            for (Order order : validUser.getOrderHistory()) {
                if(orderID.trim().equalsIgnoreCase(order.getOrderId())){
                    validOrder = order;
                    isValid = true;
                    break;
                }
            }
    
            if(!isValid){
                System.out.println("Order ID tidak dapat ditemukan!\n");
            }
        }
        // Mengambil data private yang diperlukan dengan getter
        String orderID = validOrder.getOrderId();
        String tanggalOrder = validOrder.getTanggal();
        String namaResto = (validOrder.getRestaurant()).getNama();
        String lokasi = validUser.getLokasi();
        boolean orderFinished = validOrder.getOrderFinished();
        String status;

        if(!orderFinished){
            status = "Not Finished";
        } else {
            status = "Finished";
        }
        String pesanan = "";
        for (Menu order : validOrder.getItems()) {
            pesanan += "\n- " + order.getNamaMakanan() + " " + order.getHarga();
        }
        int ongkir = validOrder.getOngkir();
        // Output bill
        System.out.println("\nBill:"+
        "\nOrder ID: " + orderID +
        "\nTanggal Pemesanan: " + tanggalOrder +
        "\nRestaurant: " + namaResto +
        "\nLokasi Pengiriman: " + lokasi +
        "\nStatus Pengiriman: " + status +
        "\nPesanan: " + pesanan +
        "\nBiaya Ongkos Kirim: Rp " + ongkir);
    }

    public static void handleLihatMenu(){
        boolean isValid = false;
        Restaurant validResto = null;
        while(!isValid){
            System.out.println("\n---------------Lihat Menu---------------");
            System.out.print("Nama Restoran: ");
            String namaResto = input.nextLine();
            // Mencari namaResto dalam restoList
            for (Restaurant restaurant : restoList) {
                if(namaResto.trim().equalsIgnoreCase(restaurant.getNama())){
                    validResto = restaurant;
                    isValid = true;
                    break;
                }
            }
            if(!isValid){
                System.out.println("Restoran tidak terdaftar dalam sistem.\n");
            }
        }
        String menu = "";
        int index = 0;
        // Keluaran menu resto
        for (Menu item : validResto.getMenu()) {
            ++index;
            menu += "\n" + index + ". " + item.getNamaMakanan() + " " + item.getHarga();
        }
        System.out.println("\nPesanan: " + menu);
    }

    public static void handleUpdateStatusPesanan(User validUser){
        boolean isValid = false;
        Order validOrder = null;
        while(!isValid){
            System.out.println("\n-------------------Update Pesanan-------------------");
            System.out.print("Order ID: ");
            String orderID = input.nextLine();
            // Mencari order ID dari orderHistory milik user
            for (Order order : validUser.getOrderHistory()) {
                if(orderID.trim().equalsIgnoreCase(order.getOrderId())){
                    validOrder = order;
                    isValid = true;
                    break;
                }
            }
            if(!isValid){
                System.out.println("Order ID tidak dapat ditemukan.");
                continue;
            }

            validOrder.setOrderFinished(true);
            System.out.println("Status: Finished");

        }
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
            // Conditionals
            if(nama.length()<4){
                System.out.println("Nama restoran tidak valid!");
            } else if (restoList.isEmpty()){    // Jika restoList masih kososng
                validInput = true;
                break;
            } else if (!restoList.isEmpty()){   // Jika restoList tidak kosong
                for (Restaurant restaurant : restoList) {
                    if (nama.equalsIgnoreCase(restaurant.getNama())){   // Nama resto tidak boleh sama dengan yang sudah ada
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
                String[] isiMenu = makananInput.split(" "); // Split makananInput

                String makanan = "";
                for (String string : isiMenu) { // Mencari String harga
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
                // Menambahkan newMenu dengan object Menu baru
                newMenu.add(new Menu(makanan, harga));
            }
        }
        // Membuat object Restaurant baru
        Restaurant newResto = new Restaurant(nama, null, 0);
        restoList.add(newResto);    // Tambah resto baru ke restoList
        System.out.println("Restaurant "  + nama + " berhasil terdaftar.");
    }   

    public static void handleHapusRestoran(){
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

    public static Menu[] getMenuRequest(Restaurant restaurant, List<String> listMenuPesananRequest){
        Menu[] menu = new Menu[listMenuPesananRequest.size()];
        for(int i=0;i<menu.length;i++){
            for(Menu existMenu : restaurant.getMenu()){
                if(existMenu.getNamaMakanan().equals(listMenuPesananRequest.get(i))){
                    menu[i] = existMenu;
                }
            }
        }
        return menu;
    }

    public static boolean validateRequestPesanan(Restaurant restaurant, List<String> listMenuPesananRequest){
        return listMenuPesananRequest.stream().allMatch(pesanan -> 
            restaurant.getMenu().stream().anyMatch(menu -> menu.getNamaMakanan().equals(pesanan))
        );
    }

}
