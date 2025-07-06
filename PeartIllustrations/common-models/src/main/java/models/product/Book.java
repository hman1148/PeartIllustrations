package models.product;

import java.time.LocalDate;

public class Book extends ProductBase {

    private String author;
    private String isbn;
    private String publisher;
    private LocalDate publicationDate;

    public Book(String id, String name, String description, double price, String author, String isbn, String publisher, LocalDate publicationDate) {
        super(id, name, description, price);
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }
}
