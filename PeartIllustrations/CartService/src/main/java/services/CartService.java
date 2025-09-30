package services;

import ShoppingCart.ShoppingCartRepository;
import models.ShoppingCart.ShoppingCart;
import models.product.ProductBase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final ShoppingCartRepository shoppingCartRepository;

    public CartService(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
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

    /**
     * Removes all products from the shopping cart for a given user.
     *
     * @param userId The ID of the user whose cart is to be cleared.
     */
    public void removeAllProductsFromCart(Long userId) {
        this.shoppingCartRepository.clearCart(userId);
    }

    /**
     * Updates the quantity of a specific product in the shopping cart for a given user.
     *
     * @param userId      The ID of the user whose cart is to be updated.
     * @param productId   The ID of the product whose quantity is to be updated.
     * @param newQuantity The new quantity for the specified product.
     */
    public void updateCartItemQuantity(Long userId, Long productId, int newQuantity) {
        ShoppingCart cart = this.shoppingCartRepository.findByUserId(userId);
        if (cart == null) {
            throw new IllegalArgumentException("Shopping cart not found for user ID: " + userId);
        }

        // First, remove all instances of the product from the cart
        List<ProductBase> currentItems = this.shoppingCartRepository.getAllItemsInCart(userId);
        long currentQuantity = currentItems.stream().filter(item -> item.getId().equals(productId)).count();

        for (long i = 0; i < currentQuantity; i++) {
            this.shoppingCartRepository.removeProductFromCart(userId, productId);
        }

        ProductBase productToAdd = null;
        for (ProductBase item : currentItems) {
            if (item.getId().equals(productId)) {
                productToAdd = item;
                break;
            }
        }

        if (productToAdd != null) {
            for (int i = 0; i < newQuantity; i++) {
                this.shoppingCartRepository.addProductToCart(userId, productToAdd);
            }
        } else {
            throw new IllegalArgumentException("Product with ID: " + productId + " not found in cart.");
        }
    }
}
