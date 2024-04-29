package assignments.assignment3.systemCLI;

import java.util.Scanner;
import assignments.assignment2.MainMenu;
import assignments.assignment3.User;

public class CustomerSystemCLI extends UserSystemCLI{
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

    void handleBuatPesanan(){
        MainMenu.handleBuatPesanan();
    }

    void handleCetakBill(){
        MainMenu.handleCetakBill();
    }

    void handleLihatMenu(){
        MainMenu.handleLihatMenu();
    }

    void handleBayarBill(){
        // TODO: Implementasi method untuk handle ketika customer ingin melihat menu
    }

    void handleUpdateStatusPesanan(){
        MainMenu.handleUpdateStatusPesanan();
    }

    void handleCekSaldo(){
        // TODO: Implementasi method untuk handle ketika customer ingin mengecek saldo yang dimiliki
    }
}
