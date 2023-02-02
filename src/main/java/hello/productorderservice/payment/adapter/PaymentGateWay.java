package hello.productorderservice.payment.adapter;

public interface PaymentGateWay {
    void execute(int totalPrice, String cardNumber);
}
