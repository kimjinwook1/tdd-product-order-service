package hello.productorderservice.product;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProductServiceTest {

    private ProductService productService;
    private ProductPort productPort;

    @Test
    void 상품수정() {
        final Long productId = 1L;
        final UpdateProductRequest request = new UpdateProductRequest("상품 수정", 2000, DiscountPolicy.NONE);
        final Product product = new Product("상품명", 1000, DiscountPolicy.NONE);
        productPort = new StubProductPort(product);
        productService = new ProductService(productPort);

        productService.updateProduct(productId, request);

        assertThat(product.getName()).isEqualTo("상품 수정");
        assertThat(product.getPrice()).isEqualTo(2000);
    }

    private static class StubProductPort implements ProductPort {
        public final Product product;

        private StubProductPort(final Product product) {
            this.product = product;
        }

        @Override
        public void save(final Product product) {

        }

        @Override
        public Product getProduct(final Long productId) {
            return product;
        }
    }
}
