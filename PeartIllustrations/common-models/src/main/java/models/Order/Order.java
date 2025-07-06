package models.Order;

import jakarta.persistence.*;
import models.User.User;
import models.product.ProductBase;

import java.util.ArrayList;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String orderId;

    @ManyToMany
    private ArrayList<ProductBase> products;
    private int totalPrice;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String paymentStatus;
    private String stripePaymentIntentId;


    public Order(String orderId, ArrayList<ProductBase> products, int totalPrice, User user) {
        this.orderId = orderId;
        this.products = products;
        this.totalPrice = totalPrice;
        this.user = user;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public ArrayList<ProductBase> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ProductBase> products) {
        this.products = products;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getStripePaymentIntentId() {
        return stripePaymentIntentId;
    }

    public void setStripePaymentIntentId(String stripePaymentIntentId) {
        this.stripePaymentIntentId = stripePaymentIntentId;
    }
}
