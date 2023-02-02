package hello.productorderservice.payment;

import hello.productorderservice.order.Order;
import hello.productorderservice.product.DiscountPolicy;
import hello.productorderservice.product.Product;
import org.springframework.stereotype.Component;

@Component
class PaymentAdapter implements PaymentPort {
    private final PaymentGateWay paymentGateway;
    private final PaymentRepository paymentRepository;

    public PaymentAdapter(final PaymentGateWay paymentGateway, final PaymentRepository paymentRepository) {
        this.paymentGateway = paymentGateway;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Order getOrder(final Long orderId) {
        return new Order(new Product("상품1", 1000, DiscountPolicy.NONE), 1);
    }

    @Override
    public void pay(final int totalPrice, final String cardNumber) {
        paymentGateway.execute(totalPrice, cardNumber);
    }

    @Override
    public void save(final Payment payment) {
        paymentRepository.save(payment);
    }
}
