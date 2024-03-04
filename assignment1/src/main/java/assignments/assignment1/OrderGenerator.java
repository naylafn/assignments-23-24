package assignments.assignment1;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.text.*;
import java.util.Scanner;
public class OrderGenerator {
    private static final Scanner input = new Scanner(System.in);

    /* 
    Anda boleh membuat method baru sesuai kebutuhan Anda
    Namun, Anda tidak boleh menghapus ataupun memodifikasi return type method yang sudah ada.
    */

    /*
     * Method  ini untuk menampilkan menu
     */
    public static void showMenu(){
        System.out.println(">>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.err.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
        System.out.println();
        System.out.println("Pilih menu:");
        System.err.println("1. Generate Order ID");
        System.out.println("2. Generate Bill");
        System.out.println("3. Keluar");
    }

    public static String generateOrderID(String namaRestoran, String tanggalOrder, String noTelepon) {
        String orderID = "";
        orderID += namaRestoran.substring(0,4).toUpperCase() + tanggalOrder.replaceAll("/", "") + noTeleponCalculation(noTelepon);
        String checkSumDigit = checkSumCalculation(orderID);
        orderID += checkSumDigit;

        return orderID;
    }

    public static String generateBill(String OrderID, String lokasi){
        String ongkir = "";
        String bill = "";

        switch (lokasi.toUpperCase()) {
            case "P":
                ongkir = "10.000";
                break;
            case "U":
                ongkir = "20.000";
                break;
            case "T":
                ongkir = "35.000";
                break;
            case "S":
                ongkir = "40.000";
                break;
            case "B":
                ongkir = "60.000";
                break;
        }

        bill = "Bill:\n" + 
        "Order ID: " + OrderID +
        "\nTanggal Pemesanan: " + OrderID.substring(4, 6) + "/" + OrderID.substring(6,8) + "/" + OrderID.substring(8, 12) +
        "\nLokasi Pengiriman: " + lokasi.toUpperCase() +
        "\nBiaya Ongkos Kirim: Rp " + ongkir + "\n"; 

        return bill;
    }

    public static void main(String[] args) {
        String namaRestoran;
        String tanggalOrder = "";
        String noTelepon;
        boolean isInt = false;
        
        showMenu();
        System.out.print("Pilihan Menu: ");
        int choice = input.nextInt();
        switch (choice) {
            case 1:
            input.nextLine();
            do { 
                System.out.print("\nNama Restoran: ");
                namaRestoran = input.nextLine();

                if (namaRestoran.length() < 4){
                    System.out.println("Nama restoran tidak valid!");
                    continue;
                }

                System.out.print("Tanggal pemesanan: ");
                tanggalOrder = input.nextLine();
                if(tanggalOrder.length() != 10 || tanggalOrder.charAt(2) != '/' || tanggalOrder.charAt(5) != '/'){
                    System.out.println("Tanggal pemesanan dalam format DD/MM/YYYY!");
                    continue;
                }

                String tanggal = tanggalOrder.substring(0, 2);
                String bulan = tanggalOrder.substring(3, 5);
                String tahun = tanggalOrder.substring(6);
                String[] arrayTanggal = {tanggal, bulan, tahun};

                for (String element : arrayTanggal) {
                    try {
                        Integer.parseInt(element);
                        isInt = true;
                       } catch (NumberFormatException e) {
                        isInt = false;
                       }    
                }
                if(isInt == false){
                    System.out.println("Tanggal pemesanan dalam format DD/MM/YYYY!");
                    continue;
                }

                System.out.print("No. Telepon: ");
                noTelepon = input.nextLine();
                for (char digit : noTelepon.toCharArray()) {
                    try {
                        Integer.parseInt(Character.toString(digit));
                        isInt = true;
                    } catch(NumberFormatException e){
                        isInt = false;
                        break;
                    }    
                }
                if(isInt == false){
                    System.out.println("Harap masukkan nomor telepon dalam bentuk bilangan bulat positif.");
                    continue;
                } else if (isInt == true){
                    System.out.println("Order ID " + generateOrderID(namaRestoran, tanggalOrder, noTelepon) + " diterima!"); ;
                }
            } while (isInt == false || tanggalOrder.length() != 10 || namaRestoran.length() < 4);
                break;

            case 2:
            String orderID;
            String lokasi = "";
            boolean isValid = false;
            input.nextLine();
            do {
                System.out.print("\nOrder ID: ");
                orderID = input.nextLine();

                if(orderID.length() < 16){
                    isValid = false;
                    System.out.println("Order ID 16 karakter");
                }else if(!checkSumCalculation(orderID.substring(0, 14)).equals(orderID.substring(14))){
                    isValid = false;
                    System.out.println("Silahkan masukkan order ID yang valid!");
                } else {
                    isValid = true;
                }
            }while(!isValid);

            do {
                System.out.println("Lokasi Pengiriman: ");
                lokasi = input.nextLine().toUpperCase();
                if(lokasi == "P" || lokasi == "U" || lokasi == "T" || lokasi == "S" || lokasi == "B"){
                    isValid = true;
                } else {
                    isValid = false;
                    System.out.println("Harap masukkan lokasi pengiriman yang ada pada jangkauan!");
                }
            }while(!isValid);
            generateBill(orderID, lokasi);
            default:
                break;
        }
        }
        
    public static int noTeleponCalculation(String noTelepon){
        int jumlah = 0;
        for (char digit :noTelepon.toCharArray()) {
            jumlah += (int) digit - '0';    // Kurangi dengan '0' untuk convert ASCII character ke numeric
        }
        int result = jumlah % 100;
        if(result < 10){
            result *= 10;
        }
        return result;
    }

    public static String checkSumCalculation(String orderID){
        boolean isInt = false;
        int index = 0;
        int checkSum1 = 0;
        int checkSum2 = 0;
        String checkSum = "";
        orderID = orderID.substring(0, 14);

        for (char character : orderID.toCharArray()) {
            try {
                Integer.parseInt(Character.toString(character));
                isInt = true;
            } catch (NumberFormatException e){
                isInt = false;
            }

            if(index % 2 == 0){
                if(isInt == true){
                    checkSum1 += (int) character - '0';
                } else if(isInt == false){
                    checkSum1 += (int) character - 55;
                }
            } else if (index % 2 == 1){
                if(isInt == true){
                    checkSum2 += (int) character - '0';
                } else if(isInt == false){
                    checkSum2 += (int) character - 55;
                }
            }
            index += 1;
        }

        checkSum1 %= 36;
        checkSum2 %= 36;

        if(checkSum1 >= 10){
            checkSum += (char)(checkSum1 + 55); 
            checkSum += checkSum2;
        } else if(checkSum2 >= 10){
            checkSum += checkSum1; 
            checkSum += (char)(checkSum2 + 55);
        } else{
            checkSum += (char)checkSum1 + (char)checkSum2;
        }
        System.out.println(checkSum);
        return checkSum;

    }
        
    }
