package models.requests;

public class ProductRequest {
    private String productType;
    private int quantity;

    public ProductRequest(String productType, int quantity) {
        this.productType = productType;
        this.quantity = quantity;
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
