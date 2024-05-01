package assignments.assignment3.payment;
import assignments.assignment2.Order;
import assignments.assignment3.*;

public class CreditCardPayment implements DepeFoodPaymentSystem{
    final double TRANSACTION_FEE_PERCENTAGE = 0.02;

    @Override
    public void processPayment(long amount, User user, Order order) {
        long totalFee = (long)(amount*TRANSACTION_FEE_PERCENTAGE);
        user.setSaldo((long)(user.getSaldo() - (amount + totalFee)));
        order.setOrderFinished(true);
        (order.getRestaurant()).setSaldo((long)(amount + totalFee));
        System.out.println("Berhasil Membayar Bill sebesar Rp " + amount + " dengan biaya transaksi sebesar Rp " + amount*TRANSACTION_FEE_PERCENTAGE);
    }
}
