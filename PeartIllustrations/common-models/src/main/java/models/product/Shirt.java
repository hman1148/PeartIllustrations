package models.product;

public class Shirt extends ProductBase {

    private String size;
    private String color;

    public Shirt(Long id, String name, String description, double price) {
        super(id, name, description, price);
    }

    @Override
    public String getProductType() {
        return "Shirt";
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
