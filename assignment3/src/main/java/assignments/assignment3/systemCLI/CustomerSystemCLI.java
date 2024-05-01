package assignments.assignment3.systemCLI;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import assignments.assignment2.*;
import assignments.assignment3.User;
import assignments.assignment3.payment.CreditCardPayment;
import assignments.assignment3.payment.DebitPayment;
import assignments.assignment3.payment.DepeFoodPaymentSystem;
import assignments.assignment3.MainMenu;

public class CustomerSystemCLI extends UserSystemCLI{
    Scanner input = new Scanner(System.in);

    // Override method abstract dari Class Abstract (UserSystemCLI)
    @Override
    public boolean handleMenu(int choice){
        switch(choice){
            case 1 -> handleBuatPesanan();
            case 2 -> handleCetakBill();
            case 3 -> handleLihatMenu();
            case 4 -> handleBayarBill();
            case 5 -> handleCekSaldo();
            case 6 -> {
                return false;
            }
            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
        }
        return true;
    }

    // Override method abstract dari Class Abstract (UserSystemCLI)
    @Override
    public void displayMenu() {
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Buat Pesanan");
        System.out.println("2. Cetak Bill");
        System.out.println("3. Lihat Menu");
        System.out.println("4. Bayar Bill");
        System.out.println("5. Cek Saldo");
        System.out.println("6. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    // Method yang digunakan beberapa diambil dari TP2 dan Solusi TP2
    private void handleBuatPesanan(){
        User userLoggedIn = MainMenu.getUserLoggedIn();
        ArrayList<Restaurant> restoList = MainMenu.getRestoList();

        System.out.println("--------------Buat Pesanan----------------");
        while (true) {
            System.out.print("Nama Restoran: ");
            String restaurantName = input.nextLine().trim();
            Restaurant restaurant = null;
            for (Restaurant restaurantInList : restoList) {
                if(restaurantName.equalsIgnoreCase(restaurantInList.getNama().trim())){
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
            if(!MainMenu.validateRequestPesanan(restaurant, listMenuPesananRequest)){
                System.out.println("Mohon memesan menu yang tersedia di Restoran!");
                continue;
            };
            Order order = new Order(
                    OrderGenerator.generateOrderID(restaurantName.toUpperCase(), tanggalPemesanan, userLoggedIn.getNomorTelepon()),
                    tanggalPemesanan, 
                    OrderGenerator.calculateDeliveryCost(userLoggedIn.getLokasi()), 
                    restaurant, 
                    MainMenu.getMenuRequest(restaurant, listMenuPesananRequest));
            System.out.printf("Pesanan dengan ID %s diterima!", order.getOrderId());
            System.out.println();
            userLoggedIn.addOrderHistory(order);
            return;
        }
    }

    private static void handleCetakBill(){
        User userLoggedIn = MainMenu.getUserLoggedIn();
        Scanner input = new Scanner(System.in);
        System.out.println("--------------Cetak Bill----------------");
        while (true) {
            System.out.print("Masukkan Order ID: ");
            String orderId = input.nextLine().trim();
            Order order = null;
            for (Order orderInList : userLoggedIn.getOrderHistory()) {
                if(orderId.equalsIgnoreCase(orderInList.getOrderId())){
                    order = orderInList;
                    break;
                }
            }

            if(order == null){
                System.out.println("Order ID tidak dapat ditemukan.\n");
                continue;
            }

            System.out.println("");
            System.out.print(MainMenu.outputBillPesanan(order));
            return;
        }
    }
    

    private void handleLihatMenu(){
        ArrayList<Restaurant> restoList = MainMenu.getRestoList();
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
        System.out.println();

    }

    private void handleBayarBill(){
        ArrayList<User> userList = MainMenu.getUserList();
        User userLoggedIn = MainMenu.getUserLoggedIn();
        DepeFoodPaymentSystem payment = userLoggedIn.getPayment();
        Order validOrder = null;
        System.out.println("\n--------------Bayar Bill----------------");
        System.out.print("Masukkan order ID: ");
        String inputOrderId = input.nextLine().toUpperCase().trim();
        for (User user : userList) {
            System.out.println("Order: " + (user.getOrderHistory()));
            if(!(user.getOrderHistory()).isEmpty()){
                for (Order order : (user.getOrderHistory())) {
                    if((order.getOrderId()).equalsIgnoreCase(inputOrderId)){
                        validOrder = order;
                        break;
                    }
                }
            }
        }
        if(validOrder == null){
            System.out.println("Order ID tidak dapat ditemukan.\n");
            return;
        } else if((validOrder.getOrderFinished())){
            System.out.println("Pesanan dengan ID ini sudah lunas!\n");
            return;
        }

        System.out.println(MainMenu.outputBillPesanan(validOrder));
        System.out.println("Opsi Pembayaran: ");
        System.out.println("1. Credit Card");
        System.out.println("2. Debit");
        System.out.print("Pilih Metode Pembayaran: ");
        int choice = input.nextInt();
        switch (choice) {
            case 1:
                if(payment instanceof CreditCardPayment){
                    payment = (CreditCardPayment) payment;
                    payment.processPayment((long)validOrder.getTotalHarga(), userLoggedIn, validOrder);
                } else {
                    System.out.println("User belum memiliki metode pembayaran ini!\n");
                }
                break;
            case 2:
                if(payment instanceof DebitPayment){
                    payment = (DebitPayment) payment;
                    payment.processPayment((long)validOrder.getTotalHarga(), userLoggedIn, validOrder);
                } else {
                    System.out.println("User belum memiliki metode pembayaran ini!\n");
                }
                break;
            default:
                System.out.println("Perintah tidak diketahui, silakan coba kembali");
                break;
        }
    }

    // Menganmbil atribut saldo milik user
    private void handleCekSaldo(){
        User userLoggedIn = MainMenu.getUserLoggedIn();
        System.out.println("Sisa saldo sebesar: Rp " + userLoggedIn.getSaldo());
    }
}
