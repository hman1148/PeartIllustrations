package controllers;

import models.GenericResponse.ItemResponse;
import models.requests.ProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import services.CartService;

import java.util.List;

@Controller
@RequestMapping("/api/cart/update")
public class UpdateCartController {

    private final CartService cartService;

    public UpdateCartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * Updates the quantities of products in the cart.
     *
     * @param id              The ID of the cart to update.
     * @param productRequests The request body containing a list of products and their new quantities.
     * @return ResponseEntity with a success message or an error message.
     */
    @PutMapping("/items/{id}")
    public ResponseEntity<ItemResponse<String>> updateCartItems(
            @PathVariable Long id,
            @RequestBody List<ProductRequest> productRequests) {
        try {
            for (ProductRequest request : productRequests) {
                Long productId = request.getProduct().getId();
                int newQuantity = request.getQuantity();
                this.cartService.updateCartItemQuantity(id, productId, newQuantity);
            }
            String message = productRequests.size() == 1 ? "Cart item updated successfully" : "Cart items updated successfully";
            return ResponseEntity.ok(new ItemResponse<>(message, message, true));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ItemResponse<>(null, "Error updating cart items: " + e.getMessage(), false));
        }
    }
}
