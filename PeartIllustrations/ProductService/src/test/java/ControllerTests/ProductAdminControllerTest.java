package ControllerTests;

import components.JwtUtil;
import controllers.ProductAdminController;
import models.GenericResponse.ItemResponse;
import models.User.User;
import models.product.Book;
import models.product.Shirt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import servicelink.User.UserServiceLink;
import services.ProductService;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ProductAdminControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @Mock
    private UserServiceLink userServiceLink;

    @Mock
    private JwtUtil jwtUtil;

    private ProductAdminController controller;

    private final String validToken = "validtoken";
    private final String authHeader = "Bearer " + validToken;

    @BeforeEach
    void setup() {
        controller = new ProductAdminController(productService, userServiceLink, jwtUtil);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private User getMockUser() {
        User user = new User(1L, "testuser", "12345678",
                "test@test.com", "test12e4", "ROLE_ADMIN", false);
        user.setId(1L);
        return user;
    }

    @Test
    void createBook_shouldReturnOk_whenAuthorizedAndValid() throws Exception {

        Book book = new Book(
                1L, "Test Book", "Author Name", 30.50, "test",
                "Publisher Name", "Test", LocalDate.now());

        when(jwtUtil.validateToken(validToken)).thenReturn(true);
        when(userServiceLink.get(anyString(), eq(ItemResponse.class)))
                .thenReturn(new ItemResponse<>(getMockUser(), "User found", true));
        when(productService.createAndUpdateProduct(any(Book.class), any(User.class)))
                .thenReturn(book);

        String bookJson = "{\"id\":1,\"title\":\"Test Book\",\"author\":\"Author Name\",\"price\":30.50}";

        mockMvc.perform(post("/api/admin/products/books/create")
                        .header("Authorization", authHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.items[0].title").value("Test Book"));
    }

    @Test
    void createBook_shouldReturnUnauthorized_whenNoToken() throws Exception {
        String bookJson = "{\"id\":1,\"title\":\"Test Book\"}";

        mockMvc.perform(post("/api/admin/products/books/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void updateShirt_shouldReturnOk_whenAuthorizedAndValid() throws Exception {
        Shirt shirt = new Shirt(1L, "Test Shirt", "A nice shirt", 25.00);
        shirt.setId(2L);
        shirt.setName("Test Shirt");

        when(jwtUtil.validateToken(validToken)).thenReturn(true);
        when(userServiceLink.get(anyString(), eq(ItemResponse.class)))
                .thenReturn(new ItemResponse<>(getMockUser(), "User found", true));
        when(productService.createAndUpdateProduct(any(Shirt.class), any(User.class)))
                .thenReturn(shirt);

        String shirtJson = "{\"id\":2,\"name\":\"Test Shirt\"}";

        mockMvc.perform(post("/api/admin/products/shirts/update")
                        .header("Authorization", authHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(shirtJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.items[0].name").value("Test Shirt"));
    }

    @Test
    void deleteBook_shouldReturnOk_whenAuthorizedAndDeleted() throws Exception {
        when(jwtUtil.validateToken(validToken)).thenReturn(true);
        when(userServiceLink.get(anyString(), eq(ItemResponse.class)))
                .thenReturn(new ItemResponse<>(getMockUser(), "User found", true));
        when(productService.deleteProduct(eq(Book.class), eq(3L), any(User.class)))
                .thenReturn(true);

        String bookJson = "{\"id\":3}";

        mockMvc.perform(post("/api/admin/products/books/delete")
                        .header("Authorization", authHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Book deleted successfully"));
    }

    @Test
    void deleteShirt_shouldReturnBadRequest_whenDeleteFails() throws Exception {
        when(jwtUtil.validateToken(validToken)).thenReturn(true);
        when(userServiceLink.get(anyString(), eq(ItemResponse.class)))
                .thenReturn(new ItemResponse<>(getMockUser(), "User found", true));
        when(productService.deleteProduct(eq(Shirt.class), eq(4L), any(User.class)))
                .thenReturn(false);

        String shirtJson = "{\"id\":4}";

        mockMvc.perform(post("/api/admin/products/shirts/delete")
                        .header("Authorization", authHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(shirtJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Failed to delete shirt"));
    }

    @Test
    void createBook_shouldReturnUnauthorized_whenInvalidToken() throws Exception {
        when(jwtUtil.validateToken(validToken)).thenReturn(false);

        String bookJson = "{\"id\":1,\"title\":\"Test Book\"}";

        mockMvc.perform(post("/api/admin/products/books/create")
                        .header("Authorization", authHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Invalid token"));
    }

    @Test
    void createBook_shouldReturnInternalServerError_whenServiceThrowsException() throws Exception {
        when(jwtUtil.validateToken(validToken)).thenReturn(true);
        when(userServiceLink.get(anyString(), eq(ItemResponse.class)))
                .thenReturn(new ItemResponse<>(getMockUser(), "User found", true));
        when(productService.createAndUpdateProduct(any(Book.class), any(User.class)))
                .thenThrow(new RuntimeException("Database error"));

        String bookJson = "{\"id\":1,\"title\":\"Test Book\"}";

        mockMvc.perform(post("/api/admin/products/books/create")
                        .header("Authorization", authHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Internal server error: Database error"));
    }
}