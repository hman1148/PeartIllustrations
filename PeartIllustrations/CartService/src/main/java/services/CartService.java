package services;

import ShoppingCart.ShoppingCartRepository;
import components.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private ShoppingCartRepository shoppingCartRepository;
    private JwtUtil jwtUtil;



}
