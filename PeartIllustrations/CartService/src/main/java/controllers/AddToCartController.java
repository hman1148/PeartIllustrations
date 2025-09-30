package controllers;

import models.GenericResponse.ItemResponse;
import models.product.ProductBase;
import models.requests.ProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import services.CartService;
import services.ProductService;

@Controller
@RequestMapping("/api/cart/add/")
public class AddToCartController {

    /** Services */
    private final CartService cartService;
    private final ProductService productService;

    /**
     * Constructor for AddToCartController.
     *
     * @param cartService    The service handling cart operations.
     * @param productService The service handling product operations.
     */
    public AddToCartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    /**
     * Adds a product to the cart.
     *
     * @param productRequest The request body containing product details and quantity.
     * @return ResponseEntity with a success message or an error message.
     */
    @RequestMapping("product/{productId}/")
    public ResponseEntity<ItemResponse<String>> addToCart(@PathVariable Long productId, @RequestBody ProductRequest productRequest) {
        Class<? extends ProductBase> productClass = productRequest.getProduct().getClass();
        ProductBase foundProduct = this.productService.getProductById(productClass, productId);

        try {
            this.cartService.addProductToCart(productId, foundProduct, productRequest.getQuantity());
            return ResponseEntity.ok(new ItemResponse<>("Item added to cart successfully", "Item added to cart successfully", true));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ItemResponse<>(null, "Error adding item to cart: " + e.getMessage(), false));
        }
    }
}
