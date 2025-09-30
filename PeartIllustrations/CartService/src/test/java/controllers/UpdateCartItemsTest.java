package controllers;

import models.GenericResponse.ItemResponse;
import models.product.ProductBase;
import models.requests.ProductRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import services.CartService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UpdateCartItemsTest {

    @Mock
    private CartService cartService;

    @Mock
    private UpdateCartController controller;

    @BeforeEach
    public void setUp() {
        cartService = mock(CartService.class);
        controller = new UpdateCartController(cartService);
    }

    @Test
    public void updateCartItems_singleItem_success() {
        // Arrange
        ProductBase product = mock(ProductBase.class);
        when(product.getId()).thenReturn(1L);

        ProductRequest request = mock(ProductRequest.class);
        when(request.getProduct()).thenReturn(product);
        when(request.getQuantity()).thenReturn(5);

        List<ProductRequest> productRequests = Collections.singletonList(request);
        doNothing().when(cartService).updateCartItemQuantity(1L, 1L, 5);

        // Act
        ResponseEntity<ItemResponse<String>> response = controller.updateCartItems(1L, productRequests);

        // Assert
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Cart item updated successfully", response.getBody().getItem());
        assertEquals("Cart item updated successfully", response.getBody().getMessage());
        verify(cartService).updateCartItemQuantity(1L, 1L, 5);
    }

    @Test
    public void updateCartItems_multipleItems_success() {
        // Arrange
        ProductBase product1 = mock(ProductBase.class);
        when(product1.getId()).thenReturn(1L);
        ProductBase product2 = mock(ProductBase.class);
        when(product2.getId()).thenReturn(2L);

        ProductRequest request1 = mock(ProductRequest.class);
        when(request1.getProduct()).thenReturn(product1);
        when(request1.getQuantity()).thenReturn(3);

        ProductRequest request2 = mock(ProductRequest.class);
        when(request2.getProduct()).thenReturn(product2);
        when(request2.getQuantity()).thenReturn(7);

        List<ProductRequest> productRequests = Arrays.asList(request1, request2);
        doNothing().when(cartService).updateCartItemQuantity(anyLong(), anyLong(), anyInt());

        // Act
        ResponseEntity<ItemResponse<String>> response = controller.updateCartItems(1L, productRequests);

        // Assert
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Cart items updated successfully", response.getBody().getItem());
        assertEquals("Cart items updated successfully", response.getBody().getMessage());
        verify(cartService).updateCartItemQuantity(1L, 1L, 3);
        verify(cartService).updateCartItemQuantity(1L, 2L, 7);
    }

    @Test
    public void updateCartItems_serviceThrowsException_failure() {
        // Arrange
        ProductBase product = mock(ProductBase.class);
        when(product.getId()).thenReturn(1L);

        ProductRequest request = mock(ProductRequest.class);
        when(request.getProduct()).thenReturn(product);
        when(request.getQuantity()).thenReturn(5);

        List<ProductRequest> productRequests = Collections.singletonList(request);
        doThrow(new RuntimeException("Update error")).when(cartService).updateCartItemQuantity(1L, 1L, 5);

        // Act
        ResponseEntity<ItemResponse<String>> response = controller.updateCartItems(1L, productRequests);

        // Assert
        assertEquals(HttpStatusCode.valueOf(500), response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertNull(response.getBody().getItem());
        assertTrue(response.getBody().getMessage().contains("Update error"));
        verify(cartService).updateCartItemQuantity(1L, 1L, 5);
    }

    @Test
    public void updateCartItems_emptyList_success() {
        // Arrange
        List<ProductRequest> productRequests = Collections.emptyList();

        // Act
        ResponseEntity<ItemResponse<String>> response = controller.updateCartItems(1L, productRequests);

        // Assert
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Cart items updated successfully", response.getBody().getItem());
        verifyNoInteractions(cartService);
    }
}
