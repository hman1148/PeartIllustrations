package ShoppingCart;

import models.ShoppingCart.ShoppingCart;
import models.product.ProductBase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    ShoppingCart findByUserId(Long userId);
    ProductBase addProductToCart(Long userId, ProductBase productBase);
    ProductBase removeProductFromCart(Long userId, Long productId);
    void clearCart(Long userId);

}
