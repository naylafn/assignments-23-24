package assignments.assignment3.payment;
import assignments.assignment2.Order;
import assignments.assignment3.*;

public class CreditCardPayment implements DepeFoodPaymentSystem{
    final double TRANSACTION_FEE_PERCENTAGE = 0.02;
    User userLoggedIn = MainMenu.getUserLoggedIn();

    @Override
    public void processPayment(long amount, Order order) {
        userLoggedIn.setSaldo((long)(userLoggedIn.getSaldo() - (amount*TRANSACTION_FEE_PERCENTAGE)));
        order.setOrderFinished(true);
        System.out.println("Berhasil Membayar Bill sebesar Rp " + amount + " dengan biaya transaksi sebesar Rp " + amount*TRANSACTION_FEE_PERCENTAGE);
    }
}
