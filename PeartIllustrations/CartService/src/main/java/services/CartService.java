package services;

import ShoppingCart.ShoppingCartRepository;
import components.JwtUtil;
import models.ShoppingCart.ShoppingCart;
import models.product.ProductBase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private ShoppingCartRepository shoppingCartRepository;
    private JwtUtil jwtUtil;

    public CartService(ShoppingCartRepository shoppingCartRepository, JwtUtil jwtUtil) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Retrieves all items in the shopping cart for a given user.
     *
     * @param userId The ID of the user whose cart items are to be retrieved.
     * @return A list of ProductBase items in the user's shopping cart.
     */
    public List<ProductBase> getCartItems(Long userId) {
        return this.shoppingCartRepository.getAllItemsInCart(userId);
    }

    /**
     * Clears all items from the shopping cart for a given user.
     *
     * @param userId The ID of the user whose cart is to be cleared.
     */
    public void clearCart(Long userId) {
        this.shoppingCartRepository.clearCart(userId);
    }

    /**
     * Adds a product to the shopping cart for a given user.
     *
     * @param userId      The ID of the user whose cart is to be updated.
     * @param productBase The product to be added to the cart.
     */
    public void addProductToCart(Long userId, ProductBase productBase, int quantity) {
        for (int i = 0; i < quantity; i++) {
            this.shoppingCartRepository.addProductToCart(userId, productBase);
        }
    }

    /**
     * Removes a product from the shopping cart for a given user.
     *
     * @param userId    The ID of the user whose cart is to be updated.
     * @param productId The ID of the product to be removed from the cart.
     */
    public void removeProductFromCart(Long userId, Long productId) {
        this.shoppingCartRepository.removeProductFromCart(userId, productId);
    }

    /**
     * Finds a shopping cart by the associated user ID.
     *
     * @param userId The ID of the user whose shopping cart is to be retrieved.
     * @return The ShoppingCart associated with the given user ID, or null if not found.
     */
    public ShoppingCart getCartByUserId(Long userId) {
        return this.shoppingCartRepository.findByUserId(userId);
    }

}
