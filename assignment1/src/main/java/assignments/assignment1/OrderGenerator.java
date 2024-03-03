package assignments.assignment1;

// import java.time.LocalDate;
// import java.time.format.DateTimeFormatter;
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

    /*
     * Method ini digunakan untuk membuat ID
     * dari nama restoran, tanggal order, dan nomor telepon
     * 
     * @return String Order ID dengan format sesuai pada dokumen soal
     */
    public static String generateOrderID(String namaRestoran, String tanggalID, int digitTelepon) {
        // TODO:Lengkapi method ini sehingga dapat mengenerate Order ID sesuai ketentuan
        String orderID = "";
        boolean isInt = false;
        int index = 0;
        int checkSum1 = 0;
        int checkSum2 = 0;

        orderID = namaRestoran.substring(0,4).toUpperCase() + tanggalID + digitTelepon;

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

        System.out.println(checkSum1);
        System.out.println(checkSum2);

        checkSum1 %= 36;
        checkSum2 %= 36;
        System.out.println((char)checkSum1);
        System.out.println(checkSum2);

        if(checkSum1 >= 10){
            orderID += (char)(checkSum1 + 55); 
            orderID += checkSum2;
        } else if(checkSum2 >= 10){
            orderID += checkSum1; 
            orderID += (char)(checkSum2 + 55);
        } else{
            orderID += (char)checkSum1 + (char)checkSum2;
        }

        System.out.println(orderID);
        return orderID;
    }


    /*
     * Method ini digunakan untuk membuat bill
     * dari order id dan lokasi
     * 
     * @return String Bill dengan format sesuai di bawah:
     *          Bill:
     *          Order ID: [Order ID]
     *          Tanggal Pemesanan: [Tanggal Pemesanan]
     *          Lokasi Pengiriman: [Kode Lokasi]
     *          Biaya Ongkos Kirim: [Total Ongkos Kirim]
     */
    public static String generateBill(String OrderID, String lokasi){
        // TODO:Lengkapi method ini sehingga dapat mengenerate Bill sesuai ketentuan
        return "Bill";
    }

    public static void main(String[] args) {
        // TODO: Implementasikan program sesuai ketentuan yang diberikan
        String namaRestoran;
        String tanggalOrder;
        String noTelepon;
        String tanggalID = "";
        int digitTelepon = 0;

        showMenu();
        System.out.print("Pilihan Menu: ");
        int choice = input.nextInt();
        switch (choice) {
            case 1:
                boolean isInt = false;
                input.nextLine();
                do { 
                    System.out.print("Nama Restoran: ");
                    namaRestoran = input.nextLine();

                    if (namaRestoran.length() < 4){
                        System.out.println("Nama restoran tidak valid!");
                    }
                } while (namaRestoran.length() < 4);
                do {
                    System.out.print("Tanggal Pemesanan: ");
                    tanggalOrder = input.nextLine();
                    String tanggal = tanggalOrder.substring(0, 2);
                    String bulan = tanggalOrder.substring(3, 5);
                    String tahun = tanggalOrder.substring(6);
                    String[] arrayTanggal = {tanggal, bulan, tahun};
                    for (String element : arrayTanggal) {
                        try {
                            Integer.parseInt(element);
                            isInt = true;
                            tanggalID = tanggal + bulan + tahun;
                           } catch (NumberFormatException e) {
                            isInt = false;
                           }    
                    }
                    if(isInt == false || tanggalOrder.length() != 10){
                        System.out.println("Tanggal pemesanan dalam format DD/MM/YYYY!");
                    }
                } while(isInt == false || tanggalOrder.length() != 10);
                do {
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
                    } else if (isInt == true){
                        digitTelepon += noTeleponCalculation(noTelepon);
                    }
                } while (isInt == false);
                
                generateOrderID(namaRestoran, tanggalID, digitTelepon);

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
        
    }
