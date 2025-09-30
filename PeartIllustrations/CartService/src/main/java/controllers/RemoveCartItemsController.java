package controllers;

import models.GenericResponse.ItemResponse;
import models.requests.CartItemsList;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import services.CartService;

import java.util.List;

@Controller
@RequestMapping("/api/cart/remove")
public class RemoveCartItemsController {

    private final CartService cartService;

    public RemoveCartItemsController(CartService cartService) {
        this.cartService = cartService;
    }

    @DeleteMapping("/cart-items/{id}")
    public ResponseEntity<ItemResponse<String>> clearCartItems(@RequestBody CartItemsList cartItemsList, @PathVariable Long id) {
        try {
            List<Long> itemIdsToRemove = cartItemsList.getProductIds();

            for (Long itemId : itemIdsToRemove) {
                this.cartService.removeProductFromCart(id, itemId);
            }

            return ResponseEntity.ok(new ItemResponse<>("Cart cleared successfully", "Cart cleared successfully", true));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ItemResponse<>(null, "Error clearing cart: " + e.getMessage(), false));
        }
    }

    @DeleteMapping("/remove-all-items/{id}")
    public ResponseEntity<ItemResponse<String>> removeAllCartItems(@PathVariable Long id) {
        try {
            this.cartService.removeAllProductsFromCart(id);
            return ResponseEntity.ok(new ItemResponse<>("All items removed from cart successfully", "All items removed from cart successfully", true));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ItemResponse<>(null, "Error removing all items from cart: " + e.getMessage(), false));
        }
    }
}
