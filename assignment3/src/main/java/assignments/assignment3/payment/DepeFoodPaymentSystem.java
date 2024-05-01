package assignments.assignment3.payment;
import assignments.assignment2.Order;

public interface DepeFoodPaymentSystem {
    public abstract void processPayment(long amount, Order order);
}
