package models.User;

import jakarta.persistence.*;
import models.Order.Order;
import models.ShoppingCart.ShoppingCart;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;
    private String role;
    private boolean isEmailSubscriptionsActive;
    private String stripeCustomerId;

    @OneToMany
    private ArrayList<Order> orderHistory;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShoppingCart> shoppingCart;

    public User(Long id, String username, String password, String email,
                String stripeCustomerId, String role, boolean isEmailSubscriptionsActive) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.stripeCustomerId = stripeCustomerId;
        this.isEmailSubscriptionsActive = isEmailSubscriptionsActive;
        this.orderHistory = new ArrayList<>();
        this.shoppingCart = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEmailSubscriptionsActive() {
        return isEmailSubscriptionsActive;
    }

    public void setEmailSubscriptionsActive(boolean emailSubscriptionsActive) {
        isEmailSubscriptionsActive = emailSubscriptionsActive;
    }

    public ArrayList<Order> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(ArrayList<Order> orderHistory) {
        this.orderHistory = orderHistory;
    }

    public List<ShoppingCart> getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(List<ShoppingCart> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public String getStripeCustomerId() {
        return stripeCustomerId;
    }

    public void setStripeCustomerId(String stripeCustomerId) {
        this.stripeCustomerId = stripeCustomerId;
    }
}
