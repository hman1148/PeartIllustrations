package controllers;

import models.GenericResponse.ItemResponse;
import models.requests.CartItemsList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import services.CartService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RemoveCartItemsTest {

    @Mock
    private CartService cartService;

    @Mock
    private RemoveCartItemsController controller;

    @BeforeEach
    public void setUp() {
        cartService = mock(CartService.class);
        controller = new RemoveCartItemsController(cartService);
    }

    @Test
    public void clearCartItems_success() {
        // Arrange
        CartItemsList cartItemsList = mock(CartItemsList.class);
        List<Long> itemIds = Arrays.asList(1L, 2L, 3L);
        when(cartItemsList.getProductIds()).thenReturn(itemIds);
        doNothing().when(cartService).removeProductFromCart(anyLong(), anyLong());

        // Act
        ResponseEntity<ItemResponse<String>> response = controller.clearCartItems(cartItemsList, 1L);

        // Assert
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Cart cleared successfully", response.getBody().getItem());
        assertEquals("Cart cleared successfully", response.getBody().getMessage());
        verify(cartService, times(3)).removeProductFromCart(eq(1L), anyLong());
    }

    @Test
    public void clearCartItems_failure() {
        // Arrange
        CartItemsList cartItemsList = mock(CartItemsList.class);
        List<Long> itemIds = Arrays.asList(1L, 2L);
        when(cartItemsList.getProductIds()).thenReturn(itemIds);
        doThrow(new RuntimeException("Remove error")).when(cartService).removeProductFromCart(1L, 1L);

        // Act
        ResponseEntity<ItemResponse<String>> response = controller.clearCartItems(cartItemsList, 1L);

        // Assert
        assertEquals(HttpStatusCode.valueOf(500), response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertNull(response.getBody().getItem());
        assertTrue(response.getBody().getMessage().contains("Remove error"));
    }

    @Test
    public void removeAllCartItems_success() {
        // Arrange
        doNothing().when(cartService).removeAllProductsFromCart(1L);

        // Act
        ResponseEntity<ItemResponse<String>> response = controller.removeAllCartItems(1L);

        // Assert
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals("All items removed from cart successfully", response.getBody().getItem());
        assertEquals("All items removed from cart successfully", response.getBody().getMessage());
        verify(cartService).removeAllProductsFromCart(1L);
    }

    @Test
    public void removeAllCartItems_failure() {
        // Arrange
        doThrow(new RuntimeException("Remove all error")).when(cartService).removeAllProductsFromCart(1L);

        // Act
        ResponseEntity<ItemResponse<String>> response = controller.removeAllCartItems(1L);

        // Assert
        assertEquals(HttpStatusCode.valueOf(500), response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertNull(response.getBody().getItem());
        assertTrue(response.getBody().getMessage().contains("Remove all error"));
    }
}
