package ControllerTests;

import components.JwtUtil;
import controllers.ProductController;
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
import services.ProductService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @Mock
    private JwtUtil jwtUtil;

    private final String validToken = "validtoken";
    private final String authHeader = "Bearer " + validToken;

    @BeforeEach
    void setup() {
        ProductController controller = new ProductController(productService, jwtUtil);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getAllBooks_shouldReturnOk_whenAuthorizedAndBooksExist() throws Exception {
        Book book1 = new Book(1L, "Test Book 1", "Author 1", 25.99, "Description 1", "Publisher 1", "Category 1", LocalDate.now());
        Book book2 = new Book(2L, "Test Book 2", "Author 2", 30.50, "Description 2", "Publisher 2", "Category 2", LocalDate.now());
        List<Book> books = Arrays.asList(book1, book2);

        when(jwtUtil.validateToken(validToken)).thenReturn(true);
        when(productService.getAllBooks()).thenReturn(books);

        mockMvc.perform(get("/api/products/books/all")
                        .header("Authorization", authHeader)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items[0].title").value("Test Book 1"))
                .andExpect(jsonPath("$.items[1].title").value("Test Book 2"));
    }

    @Test
    void getAllBooks_shouldReturnUnauthorized_whenNoToken() throws Exception {
        mockMvc.perform(get("/api/products/books/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Unauthorized access"));
    }

    @Test
    void getAllBooks_shouldReturnUnauthorized_whenInvalidToken() throws Exception {
        when(jwtUtil.validateToken(validToken)).thenReturn(false);

        mockMvc.perform(get("/api/products/books/all")
                        .header("Authorization", authHeader)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Invalid token"));
    }

    @Test
    void getAllShirts_shouldReturnOk_whenAuthorizedAndShirtsExist() throws Exception {
        Shirt shirt1 = new Shirt(1L, "Test Shirt 1", "Description 1", 19.99);
        Shirt shirt2 = new Shirt(2L, "Test Shirt 2", "Description 2", 24.99);
        List<Shirt> shirts = Arrays.asList(shirt1, shirt2);

        when(jwtUtil.validateToken(validToken)).thenReturn(true);
        when(productService.getAllShirts()).thenReturn(shirts);

        mockMvc.perform(get("/api/products/shirts/all")
                        .header("Authorization", authHeader)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items[0].name").value("Test Shirt 1"))
                .andExpect(jsonPath("$.items[1].name").value("Test Shirt 2"));
    }

    @Test
    void searchBooksByTitle_shouldReturnOk_whenAuthorizedAndBooksFound() throws Exception {
        Book book = new Book(1L, "Java Programming", "John Doe", 45.99, "Programming book", "Tech Publisher", "Programming", LocalDate.now());
        List<Book> books = List.of(book);

        when(jwtUtil.validateToken(validToken)).thenReturn(true);
        when(productService.getBooksByTitle("Java Programming")).thenReturn(books);

        mockMvc.perform(get("/api/products/books/search")
                        .header("Authorization", authHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("Java Programming"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items[0].title").value("Java Programming"));
    }

    @Test
    void searchShirtsByTitle_shouldReturnOk_whenAuthorizedAndNoResults() throws Exception {
        when(jwtUtil.validateToken(validToken)).thenReturn(true);
        when(productService.getShirtsByTitle("nonexistent")).thenReturn(List.of());

        mockMvc.perform(get("/api/products/shirts/search")
                        .header("Authorization", authHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("nonexistent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items").isEmpty());
    }

    @Test
    void searchBooks_shouldReturnUnauthorized_whenNoToken() throws Exception {
        mockMvc.perform(get("/api/products/books/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("Java"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Unauthorized access"));
    }
}