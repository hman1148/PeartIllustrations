package controllers;


import models.GenericResponse.ItemResponse;
import models.GenericResponse.ItemsResponse;
import models.product.ProductBase;
import models.requests.CartItemsList;
import models.requests.ProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import services.CartService;

import java.util.Hashtable;
import java.util.List;

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



}
