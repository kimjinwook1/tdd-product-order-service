package hello.productorderservice.order;

import hello.productorderservice.product.DiscountPolicy;
import hello.productorderservice.product.Product;
import hello.productorderservice.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

public class OrderServiceTest {

    private OrderService orderService;
    private OrderPort orderPort;
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        orderRepository = new OrderRepository();
        orderPort = new OrderPort() {
            @Override
            public Product getProductById(final Long productId) {
                return new Product("상품명", 1000, DiscountPolicy.NONE);
            }

            @Override
            public void save(final Order order) {
                orderRepository.save(order);
            }
        };

        orderService = new OrderService(orderPort);
    }

    @Test
    void 상품주문() {
        final Long productId = 1L;
        final int quantity = 2;
        final CreateOrderRequest request = new CreateOrderRequest(productId, quantity);

        orderService.createOrder(request);
    }

    private class OrderService {
        private final OrderPort orderPort;

        public OrderService(final OrderPort orderPort) {
            this.orderPort = orderPort;
        }

        public void createOrder(final CreateOrderRequest request) {
            final Product product = orderPort.getProductById(request.productId());

            final Order order = new Order(product, request.quantity());

            orderPort.save(order);
        }
    }

    private record CreateOrderRequest(Long productId, int quantity) {
        private CreateOrderRequest(final Long productId, final int quantity) {
            this.productId = productId;
            this.quantity = quantity;
            Assert.notNull(productId, "상품 ID는 필수입니다.");
            Assert.isTrue(quantity > 0, "수량은 0보다 커야 합니다.");
        }

    }

    private class OrderAdapter implements OrderPort {
        private final ProductRepository productRepository;
        private final OrderRepository orderRepository;

        public OrderAdapter(final ProductRepository productRepository, final OrderRepository orderRepository) {
            this.productRepository = productRepository;
            this.orderRepository = orderRepository;
        }

        @Override
        public Product getProductById(final Long productId) {
            return productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
        }

        @Override
        public void save(final Order order) {
            orderRepository.save(order);
        }
    }

    private class Order {
        private Long id;
        private final Product product;
        private final int quantity;

        public Order(final Product product, final int quantity) {
            this.product = product;
            this.quantity = quantity;
            Assert.notNull(product, "상품은 필수입니다.");
            Assert.isTrue(quantity > 0, "수량은 0보다 커야 합니다.");
        }

        public void assignId(final Long id) {
            this.id = id;
        }

        public Long getId() {
            return id;
        }
    }

    private class OrderRepository {
        private Map<Long, Order> persistence = new HashMap<>();
        private Long sequence = 0L;

        public void save(final Order order) {
            order.assignId(++sequence);
            persistence.put(order.getId(), order);
        }
    }

    private interface OrderPort {
        Product getProductById(Long productId);

        void save(Order order);
    }
}
