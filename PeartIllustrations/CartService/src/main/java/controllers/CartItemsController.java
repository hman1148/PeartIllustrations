package controllers;


import models.GenericResponse.ItemResponse;
import models.GenericResponse.ItemsResponse;
import models.product.ProductBase;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import services.CartService;

@Controller
@RequestMapping("/api/cart/")
public class CartItemsController {

    private final CartService cartService;

    public CartItemsController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<ItemsResponse<ProductBase>> getCartItems(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(new ItemsResponse<>(this.cartService.getCartItems(id), "Cart items retrieved successfully", true));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ItemsResponse<>(null, "Error retrieving cart items: " + e.getMessage(), false));
        }
    }

    @PostMapping("/items/{id}")
    public ResponseEntity<ItemResponse<String>> clearCartItems(@PathVariable Long id) {
        try {
            this.cartService.clearCart(id);
            return ResponseEntity.ok(new ItemResponse<>("Cart cleared successfully", "Cart cleared successfully", true));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ItemResponse<>(null, "Error clearing cart: " + e.getMessage(), false));
        }
    }
}
