package services;

import ProductRepository.BookRepository;
import ProductRepository.ShirtRepository;
import ShoppingCart.ShoppingCartRepository;
import components.JwtUtil;
import models.User.User;
import models.product.Book;
import models.product.ProductBase;
import models.product.Shirt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import servicelink.User.UserServiceLink;

import java.util.List;

@Service
public class ProductService {

    private final BookRepository bookRepository;
    private final ShirtRepository shirtRepository;
    private final JwtUtil jwtUtil;
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserServiceLink userServiceLink;

    @Autowired
    public ProductService(BookRepository bookRepository, ShirtRepository shirtRepository, JwtUtil jwtUtil,
                          ShoppingCartRepository shoppingCartRepository, UserServiceLink userServiceLink) {
        this.bookRepository = bookRepository;
        this.shirtRepository = shirtRepository;
        this.jwtUtil = jwtUtil;
        this.shoppingCartRepository = shoppingCartRepository;
        this.userServiceLink = userServiceLink;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Shirt> getAllShirts() {
        return this.shirtRepository.findAll();
    }

    public List<Book> getBooksByTitle(String title) {
        return this.bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Shirt> getShirtsByTitle(String title) {
        // Assuming a similar method exists in ShirtRepository
        return this.shirtRepository.findByTitleContainingIgnoreCase(title);
    }

    public <T extends ProductBase> ProductBase getProductById(Class<T> type, Long id) {
        return switch (type.getSimpleName()) {
            case "Book" -> bookRepository.findById(id).orElse(null);
            case "Shirt" -> shirtRepository.findById(id).orElse(null);
            default -> null;
        };
    }

    public <T extends ProductBase> ProductBase createAndUpdateProduct(ProductBase product, User user) {
        if (!user.getRole().equalsIgnoreCase("ADMIN")) {
            return null;
        }
        // Discover the product type
        switch (product.getClass().getTypeName()) {
            case "Book": {
                Book book = (Book) product;
                this.bookRepository.save(book);
                return book;
            }
            case "Shirt": {
                Shirt shirt = (Shirt) product;
                this.shirtRepository.save(shirt);
                return shirt;
            }
            default: {
                return null;
            }
        }
    }

    public <T extends ProductBase> boolean deleteProduct(Class<T> type, Long id, User user) {
        if (!user.getRole().equalsIgnoreCase("ADMIN")) {
            return false;
        }
        // Discover the product type
        return switch (type.getSimpleName()) {
            case "Book" -> {
                bookRepository.deleteById(id);
                yield true;
            }
            case "Shirt" -> {
                shirtRepository.deleteById(id);
                yield true;
            }
            default -> false;
        };
    }
}
