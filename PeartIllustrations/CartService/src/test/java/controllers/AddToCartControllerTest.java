package controllers;

import models.GenericResponse.ItemResponse;
import models.product.ProductBase;
import models.requests.ProductRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import services.CartService;
import services.ProductService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddToCartControllerTest {

    private CartService cartService;
    private ProductService productService;
    private AddToCartController controller;

    @BeforeEach
    public void setUp() {
        cartService = mock(CartService.class);
        productService = mock(ProductService.class);
        controller = new AddToCartController(cartService, productService);
    }

    @Test
    public void addToCart_success() {
        // Arrange
        ProductBase product = mock(ProductBase.class);
        when(product.getId()).thenReturn(1L);
        ProductRequest request = mock(ProductRequest.class);
        when(request.getProduct()).thenReturn(product);
        when(request.getQuantity()).thenReturn(2);
        when(productService.getProductById(product.getClass(), 1L)).thenReturn(product);

        // Act
        ResponseEntity<ItemResponse<String>> response = controller.addToCart(request);

        // Assert
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Item added to cart successfully", response.getBody().getItem());
        verify(cartService).addProductToCart(1L, product, 2);
    }

    @Test
    public void addToCart_failure() {
        // Arrange
        ProductBase product = mock(ProductBase.class);
        when(product.getId()).thenReturn(1L);
        ProductRequest request = mock(ProductRequest.class);
        when(request.getProduct()).thenReturn(product);
        when(request.getQuantity()).thenReturn(2);
        when(productService.getProductById(product.getClass(), 1L)).thenReturn(product);
        doThrow(new RuntimeException("Cart error")).when(cartService).addProductToCart(1L, product, 2);

        // Act
        ResponseEntity<ItemResponse<String>> response = controller.addToCart(request);

        // Assert
        assertEquals(HttpStatusCode.valueOf(500), response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertTrue(response.getBody().getMessage().contains("Cart error"));
    }
}
