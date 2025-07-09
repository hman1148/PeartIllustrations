package services;

import ProductRepository.BookRepository;
import ProductRepository.ShirtRepository;
import ShoppingCart.ShoppingCartRepository;
import components.JwtUtil;
import models.product.ProductBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final BookRepository bookRepository;
    private final ShirtRepository shirtRepository;
    private final JwtUtil jwtUtil;
    private final ShoppingCartRepository shoppingCartRepository;

    @Autowired
    public ProductService(BookRepository bookRepository, ShirtRepository shirtRepository, JwtUtil jwtUtil, ShoppingCartRepository shoppingCartRepository) {
        this.bookRepository = bookRepository;
        this.shirtRepository = shirtRepository;
        this.jwtUtil = jwtUtil;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    public addProductToShopingCart(ProductBase product, String token) {
        if (product == null || product.getId() == null) {
            throw new IllegalArgumentException("Product cannot be null and must have an ID");
        }



    }


}
