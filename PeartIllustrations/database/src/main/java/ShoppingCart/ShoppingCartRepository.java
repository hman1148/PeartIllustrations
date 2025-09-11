package ShoppingCart;

import models.ShoppingCart.ShoppingCart;
import models.product.ProductBase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    /**
     * Finds a shopping cart by the associated user ID.
     *
     * @param userId The ID of the user whose shopping cart is to be retrieved.
     * @return The ShoppingCart associated with the given user ID, or null if not found.
     */
    ShoppingCart findByUserId(Long userId);

    /**
     * Adds a product to the shopping cart for a given user.
     *
     * @param userId      The ID of the user whose cart is to be updated.
     * @param productBase The product to be added to the cart.
     * @return The updated ProductBase that was added to the cart.
     */
    ProductBase addProductToCart(Long userId, ProductBase productBase);

    /**
     * Removes a product from the shopping cart for a given user.
     *
     * @param userId    The ID of the user whose cart is to be updated.
     * @param productId The ID of the product to be removed from the cart.
     */
    void removeProductFromCart(Long userId, Long productId);

    /**
     * Retrieves all items in the shopping cart for a given user.
     *
     * @param userId The ID of the user whose cart items are to be retrieved.
     * @return A list of ProductBase items in the user's shopping cart.
     */
    List<ProductBase> getAllItemsInCart(Long userId);

    /**
     * Clears all items from the shopping cart for a given user.
     *
     * @param userId The ID of the user whose cart is to be cleared.
     */
    void clearCart(Long userId);

}
