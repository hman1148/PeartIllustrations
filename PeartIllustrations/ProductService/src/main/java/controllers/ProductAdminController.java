package controllers;

import components.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import models.GenericResponse.ItemResponse;
import models.GenericResponse.ItemsResponse;
import models.User.User;
import models.product.Book;
import models.product.Shirt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import servicelink.User.UserServiceLink;
import services.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
public class ProductAdminController {

    @Autowired
    private final ProductService productService;

    @Autowired
    private final UserServiceLink userServiceLink;

    @Autowired
    private final JwtUtil jwtUtil;

    public ProductAdminController(ProductService productService, UserServiceLink userServiceLink, JwtUtil jwtUtil) {
        this.productService = productService;
        this.userServiceLink = userServiceLink;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/books/create")
    public ResponseEntity<ItemsResponse<Book>> CreateBook(HttpServletRequest request, @RequestBody Book book) {
        String authRequest = request.getHeader("Authorization");

        if (authRequest == null || !authRequest.startsWith(("Bearer "))) {
            return ResponseEntity.status(401).body(new ItemsResponse<>(null, "Unauthorized access", false));
        }

        try {
            String token = authRequest.substring(7);

            if (!jwtUtil.validateToken(token)) {
                return ResponseEntity.status(401).body(new ItemsResponse<>(null, "Invalid token", false));
            }

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ItemResponse<User> userResponse = this.userServiceLink.get("/api/users/me", ItemResponse.class);
            User user = userResponse.getItem();

            Book createdBook = (Book) this.productService.createAndUpdateProduct(book, user);
            ItemsResponse<Book> response = new ItemsResponse<>(List.of(createdBook), "Book created successfully", true);
            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            return ResponseEntity.status(500).body(new ItemsResponse<>(null, "Internal server error: " + ex.getMessage(), false));
        }
    }

    @PostMapping("/shirts/create")
    public ResponseEntity<ItemsResponse<Shirt>> CreateShirt(HttpServletRequest request, @RequestBody Shirt shirt) {
        String authRequest = request.getHeader("Authorization");

        if (authRequest == null || !authRequest.startsWith(("Bearer "))) {
            return ResponseEntity.status(401).body(new ItemsResponse<>(null, "Unauthorized access", false));
        }

        try {
            String token = authRequest.substring(7);

            if (!this.jwtUtil.validateToken(token)) {
                return ResponseEntity.status(401).body(new ItemsResponse<>(null, "Invalid token", false));
            }

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ItemResponse<User> userResponse = this.userServiceLink.get("/api/users/me", ItemResponse.class);
            User user = userResponse.getItem();

            Shirt createdShirt = (Shirt) this.productService.createAndUpdateProduct(shirt, user);
            ItemsResponse<Shirt> response = new ItemsResponse<>(List.of(createdShirt), "Shirt created successfully", true);
            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            return ResponseEntity.status(500).body(new ItemsResponse<>(null, "Internal server error: " + ex.getMessage(), false));
        }
    }

    @PostMapping("/books/update")
    public ResponseEntity<ItemsResponse<Book>> UpdateBook(HttpServletRequest request, @RequestBody Book book) {
        String authRequest = request.getHeader("Authorization");

        if (authRequest == null || !authRequest.startsWith(("Bearer "))) {
            return ResponseEntity.status(401).body(new ItemsResponse<>(null, "Unauthorized access", false));
        }

        try {
            String token = authRequest.substring(7);

            if (!jwtUtil.validateToken(token)) {
                return ResponseEntity.status(401).body(new ItemsResponse<>(null, "Invalid token", false));
            }

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ItemResponse<User> userResponse = this.userServiceLink.get("/api/users/me", ItemResponse.class);
            User user = userResponse.getItem();

            Book updatedBook = (Book) this.productService.createAndUpdateProduct(book, user);
            ItemsResponse<Book> response = new ItemsResponse<>(List.of(updatedBook), "Book updated successfully", true);
            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            return ResponseEntity.status(500).body(new ItemsResponse<>(null, "Internal server error: " + ex.getMessage(), false));
        }
    }
    @PostMapping("/shirts/update")
    public ResponseEntity<ItemsResponse<Shirt>> UpdateShirt(HttpServletRequest request, @RequestBody Shirt shirt) {
        String authRequest = request.getHeader("Authorization");

        if (authRequest == null || !authRequest.startsWith(("Bearer "))) {
            return ResponseEntity.status(401).body(new ItemsResponse<>(null, "Unauthorized access", false));
        }

        try {
            String token = authRequest.substring(7);

            if (!jwtUtil.validateToken(token)) {
                return ResponseEntity.status(401).body(new ItemsResponse<>(null, "Invalid token", false));
            }

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ItemResponse<User> userResponse = this.userServiceLink.get("/api/users/me", ItemResponse.class);
            User user = userResponse.getItem();

            Shirt updatedShirt = (Shirt) this.productService.createAndUpdateProduct(shirt, user);
            ItemsResponse<Shirt> response = new ItemsResponse<>(List.of(updatedShirt), "Shirt updated successfully", true);
            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            return ResponseEntity.status(500).body(new ItemsResponse<>(null, "Internal server error: " + ex.getMessage(), false));
        }
    }

    @PostMapping("/books/delete")
    public ResponseEntity<ItemsResponse<Book>> DeleteBook(HttpServletRequest request, @RequestBody Book book) {
        String authRequest = request.getHeader("Authorization");

        if (authRequest == null || !authRequest.startsWith(("Bearer "))) {
            return ResponseEntity.status(401).body(new ItemsResponse<>(null, "Unauthorized access", false));
        }

        try {
            String token = authRequest.substring(7);

            if (!jwtUtil.validateToken(token)) {
                return ResponseEntity.status(401).body(new ItemsResponse<>(null, "Invalid token", false));
            }

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ItemResponse<User> userResponse = this.userServiceLink.get("/api/users/me", ItemResponse.class);
            User user = userResponse.getItem();

            boolean deleted = this.productService.deleteProduct(Book.class, book.getId(), user);
            if (deleted) {
                return ResponseEntity.ok(new ItemsResponse<>(null, "Book deleted successfully", true));
            } else {
                return ResponseEntity.status(400).body(new ItemsResponse<>(null, "Failed to delete book", false));
            }

        } catch (Exception ex) {
            return ResponseEntity.status(500).body(new ItemsResponse<>(null, "Internal server error: " + ex.getMessage(), false));
        }
    }

    @PostMapping("/shirts/delete")
    public ResponseEntity<ItemsResponse<Shirt>> DeleteShirt(HttpServletRequest request, @RequestBody Shirt shirt) {
        String authRequest = request.getHeader("Authorization");

        if (authRequest == null || !authRequest.startsWith(("Bearer "))) {
            return ResponseEntity.status(401).body(new ItemsResponse<>(null, "Unauthorized access", false));
        }

        try {
            String token = authRequest.substring(7);

            if (!jwtUtil.validateToken(token)) {
                return ResponseEntity.status(401).body(new ItemsResponse<>(null, "Invalid token", false));
            }

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ItemResponse<User> userResponse = this.userServiceLink.get("/api/users/me", ItemResponse.class);
            User user = userResponse.getItem();

            boolean deleted = this.productService.deleteProduct(Shirt.class, shirt.getId(), user);
            if (deleted) {
                return ResponseEntity.ok(new ItemsResponse<>(null, "Shirt deleted successfully", true));
            } else {
                return ResponseEntity.status(400).body(new ItemsResponse<>(null, "Failed to delete shirt", false));
            }

        } catch (Exception ex) {
            return ResponseEntity.status(500).body(new ItemsResponse<>(null, "Internal server error: " + ex.getMessage(), false));
        }
    }
}
