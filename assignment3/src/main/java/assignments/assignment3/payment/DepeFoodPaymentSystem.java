package assignments.assignment3.payment;
import assignments.assignment3.User;
import assignments.assignment2.Order;

public interface DepeFoodPaymentSystem {

    public long processPayment(long saldo, long amount) throws Exception;
}
