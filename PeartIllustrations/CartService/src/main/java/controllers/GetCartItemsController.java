package controllers;

import models.GenericResponse.ItemsResponse;
import models.product.ProductBase;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import services.CartService;

@Controller
@RequestMapping("/api/cart/")
public class GetCartItemsController {

    private final CartService cartService;

    public GetCartItemsController(CartService cartService) {
        this.cartService = cartService;
    }

    @RequestMapping("/items/{id}")
    public ResponseEntity<ItemsResponse<ProductBase>> getCartItems(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(new ItemsResponse<>(this.cartService.getCartItems(id), "Cart items retrieved successfully", true));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ItemsResponse<>(null, "Error retrieving cart items: " + e.getMessage(), false));
        }
    }
}
