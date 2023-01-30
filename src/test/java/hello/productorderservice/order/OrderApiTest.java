package hello.productorderservice.order;

import hello.productorderservice.ApiTest;
import hello.productorderservice.product.ProductService;
import hello.productorderservice.product.ProductSteps;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class OrderApiTest extends ApiTest {

    @Autowired
    OrderService orderService;
    @Autowired
    ProductService productService;

    @Test
    void 상품주문() {
        ProductSteps.상품등록요청(ProductSteps.상품등록요청_생성());
        final var request = OrderSteps.상품주문요청_생성();

        final var response = OrderSteps.상품주문요청(request);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());

    }

}
