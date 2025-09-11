package controllers;

import models.GenericResponse.ItemResponse;
import models.product.ProductBase;
import models.requests.ProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import services.CartService;
import services.ProductService;

@Controller
@RequestMapping("/api/cart/add/")
public class AddToCartController {

    private final CartService cartService;
    private final ProductService productService;

    public AddToCartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @RequestMapping("product/{productId}/quantity/{quantity}")
    public ResponseEntity<ItemResponse<String>> addToCart(@RequestBody ProductRequest productInfo) {



        ProductBase foundProduct = this.productService.getProductById(productId);
        try {
            this.cartService.addProductToCart(productId, quantity);
            return ResponseEntity.ok(new ItemResponse<>("Item added to cart successfully", "Item added to cart successfully", true));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ItemResponse<>(null, "Error adding item to cart: " + e.getMessage(), false));
        }
    }
}
