package hello.productorderservice.payment;

import org.springframework.stereotype.Component;

@Component
public class ConsolePaymentGateWayImpl implements PaymentGateWay {

    @Override
    public void execute(final int totalPrice, final String cardNumber) {
        System.out.println("결제 완료");
    }
}
