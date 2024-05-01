package assignments.assignment3.payment;

import assignments.assignment3.*;
import assignments.assignment2.Order;

public class DebitPayment implements DepeFoodPaymentSystem{
    final double MINIMUM_TOTAL_PRICE = 50000;
    User userLoggedIn = MainMenu.getUserLoggedIn();

    @Override
    public void processPayment(long amount, Order order) {
        if(amount<MINIMUM_TOTAL_PRICE){
            System.out.println("Jumlah pesanan < 50000 mohon menggunakan metode pembayaran yang lain\n");
            return;
        } else if (userLoggedIn.getSaldo() < amount){
            System.out.println("Saldo tidak mencukupi, mohon menggunakan metode pembayaran yang lain.\n");
            return;
        }
        userLoggedIn.setSaldo(userLoggedIn.getSaldo() - amount);
        order.setOrderFinished(true);
        System.out.println("Berhasil Membayar Bill sebesar Rp " + amount);
    }
}
