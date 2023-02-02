package hello.productorderservice.product.application.port;

import hello.productorderservice.product.domain.Product;

public interface ProductPort {

    void save(final Product product);

    Product getProduct(Long productId);
}
