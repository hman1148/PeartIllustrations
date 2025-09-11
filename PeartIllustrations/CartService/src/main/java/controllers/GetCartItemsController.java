package controllers;

import models.GenericResponse.ItemsResponse;
import models.product.ProductBase;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import services.CartService;

@Controller
@RequestMapping("/api/cart/")
public class GetCartItemsController {

    private final CartService cartService;

    public GetCartItemsController(CartService cartService) {
        this.cartService = cartService;
    }

    @RequestMapping("/items")
    public ResponseEntity<ItemsResponse<ProductBase>> getCartItems() {
        try {
            return ResponseEntity.ok(new ItemsResponse<>(this.cartService.getCartItems(), "Cart items retrieved successfully", true));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ItemsResponse<>(null, "Error retrieving cart items: " + e.getMessage(), false));
        }
    }
}
