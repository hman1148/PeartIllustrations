package models.requests;

import models.product.ProductBase;

public class ProductRequest {
    private String productType;
    private int quantity;
    private ProductBase product;

    public ProductRequest(String productType, int quantity, ProductBase product) {
        this.productType = productType;
        this.product = product;
        this.quantity = quantity;
    }

    public ProductBase getProduct() {
        return product;
    }

    public void setProduct(ProductBase product) {
        this.product = product;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
