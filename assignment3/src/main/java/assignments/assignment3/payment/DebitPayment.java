package assignments.assignment3.payment;

import assignments.assignment3.User;
import assignments.assignment2.Order;

public class DebitPayment implements DepeFoodPaymentSystem{
    final double MINIMUM_TOTAL_PRICE = 50000;

    @Override
    public void processPayment(long amount, User user, Order order) {
        System.out.println("EMANG KOSONG? " + (user == null));
        if(amount<MINIMUM_TOTAL_PRICE){
            System.out.println("Jumlah pesanan < 50000 mohon menggunakan metode pembayaran yang lain\n");
            return;
        } else if (user.getSaldo() < amount){
            System.out.println("Saldo tidak mencukupi, mohon menggunakan metode pembayaran yang lain.\n");
            return;
        }
        (order.getRestaurant()).setSaldo(amount);
        user.setSaldo(user.getSaldo() - amount);
        order.setOrderFinished(true);
        System.out.println("Berhasil Membayar Bill sebesar Rp " + amount);
    }
}
