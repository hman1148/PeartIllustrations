package controllers;

import components.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import models.GenericResponse.ItemsResponse;
import models.product.Book;
import models.product.Shirt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import services.ProductService;

import java.util.List;

@Controller
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private JwtUtil jwtUtil;

    public ProductController(ProductService productService, JwtUtil jwtUtil) {
        this.productService = productService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/books/all")
    public ResponseEntity<ItemsResponse<Book>> GetAllBooks(HttpServletRequest request) {
        String authRequest = request.getHeader("Authorization");

        if (authRequest == null || !authRequest.startsWith(("Bearer "))) {
            return ResponseEntity.status(401).body(new ItemsResponse<>(null, "Unauthorized access", false));
        }

        try {
            String token = authRequest.substring(7);

            if (!jwtUtil.validateToken(token)) {
                return ResponseEntity.status(401).body(new ItemsResponse<>(null, "Invalid token", false));
            }

            List<Book> books = this.productService.getAllBooks();
            ItemsResponse<Book> response = new ItemsResponse<>(books, "Books retrieved successfully", true);
            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            return ResponseEntity.status(500).body(new ItemsResponse<>(null, "Internal server error: " + ex.getMessage(), false));
        }
    }

    @GetMapping("/shirts/all")
    public ResponseEntity<ItemsResponse<Shirt>> GetAllShirts(HttpServletRequest request) {
        String authRequest = request.getHeader("Authorization");

        if (authRequest == null || !authRequest.startsWith(("Bearer "))) {
            return ResponseEntity.status(401).body(new ItemsResponse<>(null, "Unauthorized access", false));
        }

        try {
            String token = authRequest.substring(7);

            if (!jwtUtil.validateToken(token)) {
                return ResponseEntity.status(401).body(new ItemsResponse<>(null, "Invalid token", false));
            }

            List<Shirt> shirts = this.productService.getAllShirts();
            ItemsResponse<Shirt> response = new ItemsResponse<>(shirts, "Shirts retrieved successfully", true);
            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            return ResponseEntity.status(500).body(new ItemsResponse<>(null, "Internal server error: " + ex.getMessage(), false));
        }
    }

    @GetMapping("/books/search")
    public ResponseEntity<ItemsResponse<Book>> SearchBooksByTitle(HttpServletRequest request, @RequestBody  String title) {
        String authRequest = request.getHeader("Authorization");

        if (authRequest == null || !authRequest.startsWith(("Bearer "))) {
            return ResponseEntity.status(401).body(new ItemsResponse<>(null, "Unauthorized access", false));
        }

        try {
            String token = authRequest.substring(7);

            if (!jwtUtil.validateToken(token)) {
                return ResponseEntity.status(401).body(new ItemsResponse<>(null, "Invalid token", false));
            }

            List<Book> books = this.productService.getBooksByTitle(title);
            ItemsResponse<Book> response = new ItemsResponse<>(books, "Books retrieved successfully", true);
            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            return ResponseEntity.status(500).body(new ItemsResponse<>(null, "Internal server error: " + ex.getMessage(), false));
        }
    }

    @GetMapping("/shirts/search")
    public ResponseEntity<ItemsResponse<Shirt>> SearchShirtsByTitle(HttpServletRequest request, @RequestBody  String title) {
        String authRequest = request.getHeader("Authorization");

        if (authRequest == null || !authRequest.startsWith(("Bearer "))) {
            return ResponseEntity.status(401).body(new ItemsResponse<>(null, "Unauthorized access", false));
        }

        try {
            String token = authRequest.substring(7);

            if (!jwtUtil.validateToken(token)) {
                return ResponseEntity.status(401).body(new ItemsResponse<>(null, "Invalid token", false));
            }

            List<Shirt> shirts = this.productService.getShirtsByTitle(title);
            ItemsResponse<Shirt> response = new ItemsResponse<>(shirts, "Shirts retrieved successfully", true);
            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            return ResponseEntity.status(500).body(new ItemsResponse<>(null, "Internal server error: " + ex.getMessage(), false));
        }
    }


}
