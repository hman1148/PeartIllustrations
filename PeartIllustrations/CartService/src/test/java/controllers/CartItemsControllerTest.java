// CartItemsControllerTest.java
package controllers;

import models.GenericResponse.ItemResponse;
import models.GenericResponse.ItemsResponse;
import models.product.ProductBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import services.CartService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CartItemsControllerTest {

    private CartService cartService;
    private CartItemsController controller;

    @BeforeEach
    public void setUp() {
        cartService = mock(CartService.class);
        controller = new CartItemsController(cartService);
    }

    @Test
    public void getCartItems_success() {
        // Arrange
        ProductBase product1 = mock(ProductBase.class);
        ProductBase product2 = mock(ProductBase.class);
        List<ProductBase> products = Arrays.asList(product1, product2);
        when(cartService.getCartItems(1L)).thenReturn(products);

        // Act
        ResponseEntity<ItemsResponse<ProductBase>> response = controller.getCartItems(1L);

        // Assert
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals(products, response.getBody().getItems());
        assertEquals("Cart items retrieved successfully", response.getBody().getMessage());
    }

    @Test
    public void getCartItems_failure() {
        // Arrange
        when(cartService.getCartItems(1L)).thenThrow(new RuntimeException("DB error"));

        // Act
        ResponseEntity<ItemsResponse<ProductBase>> response = controller.getCartItems(1L);

        // Assert
        assertEquals(HttpStatusCode.valueOf(500), response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertNull(response.getBody().getItems());
        assertTrue(response.getBody().getMessage().contains("DB error"));
    }

    @Test
    public void clearCartItems_success() {
        // No exception thrown
        doNothing().when(cartService).clearCart(1L);

        // Act
        ResponseEntity<ItemResponse<String>> response = controller.clearCartItems(1L);

        // Assert
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Cart cleared successfully", response.getBody().getItem());
    }

    @Test
    public void clearCartItems_failure() {
        // Arrange
        doThrow(new RuntimeException("Clear error")).when(cartService).clearCart(1L);

        // Act
        ResponseEntity<ItemResponse<String>> response = controller.clearCartItems(1L);

        // Assert
        assertEquals(HttpStatusCode.valueOf(500), response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertNull(response.getBody().getItem());
        assertFalse(response.getBody().isSuccess());
        assertTrue(response.getBody().getMessage().contains("Clear error"));
    }
}
