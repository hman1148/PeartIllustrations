package models.product;

public class Shirt extends ProductBase {

    private String size;
    private String color;

    public Shirt(String id, String name, String description, double price) {
        super(id, name, description, price);
    }
}
