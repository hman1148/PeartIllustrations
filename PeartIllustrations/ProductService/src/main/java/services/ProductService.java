package services;

import ProductRepository.BookRepository;
import ProductRepository.ShirtRepository;
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
    private final UserServiceLink userServiceLink;

    @Autowired
    public ProductService(BookRepository bookRepository, ShirtRepository shirtRepository, JwtUtil jwtUtil,
                           UserServiceLink userServiceLink) {
        this.bookRepository = bookRepository;
        this.shirtRepository = shirtRepository;
        this.jwtUtil = jwtUtil;
        this.userServiceLink = userServiceLink;
    }

    /**
     * Retrieves all books from the repository.
     *
     * @return A list of all Book entities.
     */
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * Retrieves all shirts from the repository.
     *
     * @return A list of all Shirt entities.
     */
    public List<Shirt> getAllShirts() {
        return this.shirtRepository.findAll();
    }

    /**
     * Searches for books by title, ignoring case.
     *
     * @param title The title or part of the title to search for.
     * @return A list of Book entities that match the search criteria.
     */
    public List<Book> getBooksByTitle(String title) {
        return this.bookRepository.findByTitleContainingIgnoreCase(title);
    }

    /**
     * Searches for shirts by title, ignoring case.
     *
     * @param title The title or part of the title to search for.
     * @return A list of Shirt entities that match the search criteria.
     */
    public List<Shirt> getShirtsByTitle(String title) {
        // Assuming a similar method exists in ShirtRepository
        return this.shirtRepository.findByTitleContainingIgnoreCase(title);
    }

    /**
     * Retrieves a product by its ID and type.
     *
     * @param type The class type of the product (e.g., Book.class, Shirt.class).
     * @param id   The ID of the product to retrieve.
     * @param <T>  The type parameter extending ProductBase.
     * @return The ProductBase entity if found, otherwise null.
     */
    public <T extends ProductBase> ProductBase getProductById(Class<T> type, Long id) {
        return switch (type.getSimpleName()) {
            case "Book" -> bookRepository.findById(id).orElse(null);
            case "Shirt" -> shirtRepository.findById(id).orElse(null);
            default -> null;
        };
    }

    /**
     * Creates or updates a product in the repository.
     *
     * @param product The product entity to create or update.
     * @param user    The user performing the operation (must be an admin).
     * @param <T>     The type parameter extending ProductBase.
     * @return The created or updated ProductBase entity, or null if the user is not an admin or the type is unsupported.
     */
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

    /**
     * Deletes a product by its ID and type.
     *
     * @param type The class type of the product (e.g., Book.class, Shirt.class).
     * @param id   The ID of the product to delete.
     * @param user The user performing the operation (must be an admin).
     * @param <T>  The type parameter extending ProductBase.
     * @return True if the product was deleted, false if the user is not an admin or the type is unsupported.
     */
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
