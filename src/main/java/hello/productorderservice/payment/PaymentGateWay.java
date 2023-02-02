package hello.productorderservice.payment;

interface PaymentGateWay {
    void execute(int totalPrice, String cardNumber);
}
