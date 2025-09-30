package models.requests;

import java.util.List;

public class CartItemsList {

    private List<Long> productIds;

    public CartItemsList(List<Long> productIds) {
        this.productIds = productIds;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }
}
