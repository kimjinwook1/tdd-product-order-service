package hello.productorderservice.payment;

public class ConsolePaymentGateWayImpl implements PaymentGateWay {

    @Override
    public void execute(final int totalPrice, final String cardNumber) {
        System.out.println("결제 완료");
    }
}
