package assignments.assignment1;
import java.util.Scanner;

public class OrderGenerator {
    private static final Scanner input = new Scanner(System.in);

    public static void showMenu(){
        System.out.println("\n>>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
        System.out.println();
        System.out.println("Pilih menu:");
        System.out.println("1. Generate Order ID");
        System.out.println("2. Generate Bill");
        System.out.println("3. Keluar");
    }

    public static String generateOrderID(String namaRestoran, String tanggalOrder, String noTelepon) {
        String orderID = "";
        // Concatenate 14 character Order ID sesuai ketentuan
        orderID += namaRestoran.substring(0,4).toUpperCase() + tanggalOrder.replaceAll("/", "") + noTeleponCalculation(noTelepon);
        String checkSumDigit = checkSumCalculation(orderID);
        orderID += checkSumDigit;   // Menambahkan 2 digit shecksum

        return orderID;
    }

    public static String generateBill(String OrderID, String lokasi){
        String ongkir = "";
        String bill = "";

        // Harga ongkir setiap lokasi 
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

        // Prompt output Bill
        bill = "Bill:\n" + 
        "Order ID: " + OrderID +
        "\nTanggal Pemesanan: " + OrderID.substring(4, 6) + "/" + OrderID.substring(6,8) + "/" + OrderID.substring(8, 12) +
        "\nLokasi Pengiriman: " + lokasi.toUpperCase() +
        "\nBiaya Ongkos Kirim: Rp " + ongkir + "\n"; 

        return bill;
    }

        // Method mencari 2 digit untuk order ID dari nomor telepon
        public static String noTeleponCalculation(String noTelepon){
            int jumlah = 0;
            String result = "";
            for (char digit :noTelepon.toCharArray()) {
                jumlah += (int) digit - '0';    // Kurangi dengan '0' untuk convert ASCII character ke numeric
            }
            jumlah %= 100;
            if(jumlah < 10){    // Jika hasil jumlah satuan tambah kosong di depan
                result += "0" + jumlah;
            } else {
                result += jumlah;
            }
            return result;
        }
    
        // Method menghitung 2 digit checksum
        public static String checkSumCalculation(String orderID){
            boolean isInt = false;
            int index = 0;
            int checkSum1 = 0;  // Genap
            int checkSum2 = 0;  // Ganjil
            String checkSum = "";
            orderID = orderID.substring(0, 14); // Yang dicek adalah 14 digit pertama
    
            for (char character : orderID.toCharArray()) {  // Iterasi dan cek masing-masing digit
                try {   // Mencari tahu apakah digit tersebut integer
                    Integer.parseInt(Character.toString(character));
                    isInt = true;
                } catch (NumberFormatException e){
                    isInt = false;
                }
    
                // Checksum bilangan dengan index genap
                if(index % 2 == 0){
                    if(isInt == true){  // Jika integer ASCII value dikurangi dengan value '0' atau 48
                        checkSum1 += (int) character - '0';
                    } else if(isInt == false){  // Jika huruf ASCII value dikurangi 55
                        checkSum1 += (int) character - 55;
                    }
                // Checksum bilangan dengan index ganjil
                } else if (index % 2 == 1){
                    if(isInt == true){
                        checkSum2 += (int) character - '0';
                    } else if(isInt == false){
                        checkSum2 += (int) character - 55;
                    }
                }
                index += 1; // Index counter
            }
    
            checkSum1 %= 36;
            checkSum2 %= 36;
    
            if(checkSum1 >= 10){    // Jika checksum >= 10, maka convert ke character huruf 
                checkSum += (char)(checkSum1 + 55); 
                checkSum += checkSum2;
            } else if(checkSum2 >= 10){
                checkSum += checkSum1; 
                checkSum += (char)(checkSum2 + 55);
            } else{
                checkSum += (char)checkSum1 + (char)checkSum2;
            }
            return checkSum;
    
        }
    
    public static void main(String[] args) {
        String namaRestoran;
        String tanggalOrder = "";
        String noTelepon;
        boolean isInt = false;
        boolean isRunning = true;

        while (isRunning){
            showMenu();
            System.out.print("Pilihan Menu: ");
            int choice = input.nextInt();
            switch (choice) {
                // Case Generate Order ID
                case 1:
                input.nextLine();
                do { 
                    System.out.print("\nNama Restoran: ");
                    namaRestoran = input.nextLine();
                    namaRestoran.replaceAll(" ", "");   // Menghilangkan whitespace dalam namaRestoran
                    if (namaRestoran.length() < 4){ // Jika nama restoran kurang dari 4
                        System.out.println("Nama restoran tidak valid!");
                        continue;
                    }
    
                    System.out.print("Tanggal pemesanan: ");
                    tanggalOrder = input.nextLine();
                    // Validasi sesuai format DD/MM/YYYY
                    if(tanggalOrder.length() != 10 || tanggalOrder.charAt(2) != '/' || tanggalOrder.charAt(5) != '/'){
                        System.out.println("Tanggal pemesanan dalam format DD/MM/YYYY!");
                        continue;
                    }
                    // Memisah tanggal, bulan, tahun untuk cek apakah bertipe integer
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
                    if(isInt == false){ // Jika isi tanggal, bulan, atau tahun bukan bertipe integer
                        System.out.println("Tanggal pemesanan dalam format DD/MM/YYYY!");
                        continue;
                    }
    
                    System.out.print("No. Telepon: ");
                    noTelepon = input.nextLine();
                    // Mengubah input no telepon menjadi character array
                    for (char digit : noTelepon.toCharArray()) {        // Iterasi tiap digit input
                        try {   // Pastikan tiap digit integer 
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
                    } else if (isInt == true){  // Jika semua input sudah valid memanggil method generateOrderID()
                        System.out.println("Order ID " + generateOrderID(namaRestoran, tanggalOrder, noTelepon) + " diterima!"); ;
                    }
                } while (isInt == false || tanggalOrder.length() != 10 || namaRestoran.length() < 4);
                    break;

                // Case Generate Bill
                case 2:
                String orderID;
                String lokasi = "";
                boolean isValid = false;
                input.nextLine();

                do {
                    System.out.print("\nOrder ID: ");
                    orderID = input.nextLine().toUpperCase();   
                    // Validasi input order ID
                    if(orderID.length() < 16){
                        isValid = false;
                        System.out.println("Order ID minimal 16 karakter");
                        continue;
                    }else if(!checkSumCalculation(orderID.substring(0, 14)).equals(orderID.substring(14))){ // Validasi checksum
                        isValid = false;
                        System.out.println("Silahkan masukkan order ID yang valid!");
                        continue;
                    } else {
                        isValid = true;
                    }

                    System.out.print("Lokasi Pengiriman: ");
                    lokasi = input.nextLine().toUpperCase();
                    // Lokasi input harus sesuai dengan yang ada di tabel
                    if(lokasi.equals("P") || lokasi.equals("U") || lokasi.equals("T") || lokasi.equals("S") || lokasi.equals("B")){
                        isValid = true;
                        continue;
                    } else {
                        isValid = false;
                        System.out.println("Harap masukkan lokasi pengiriman yang ada pada jangkauan!\n");
                        continue;
                    }
                }while(!isValid);
                // Jika semua input sudah valid memanggil method generateBill()
                System.out.println(generateBill(orderID, lokasi));
                break;

                // Case keluar
                case 3 :
                System.out.println("Terima kasih telah menggunakan DepeFood!");
                    isRunning = false;
                    break;
                default:
                    break;
                }
            }
        }
                
    }