package com.book.demo.models;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.lang.Nullable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Document(collection = "BookCollection")
public class Book {
    @Id
    @JsonProperty("id")
    @Schema(type = "string", format = "uuid", description = "ID of the book.")
    private String id;

    @Indexed(unique = true)
    @JsonProperty("isbn")
    @Schema(type = "integer", description = "ISBN number of the book.", nullable = false)
    @Field("isbn")
    private long isbn;

    @Indexed(unique = false)
    @Size(min = 0, max = 512, message = "Maximum size limit character of this field is 512")
    @JsonProperty("title")
    @Schema(type = "string", description = "Title of the book.", nullable = false)
    @Field("title")
    private String title;

    @JsonProperty("synopsis")
    @Schema(type = "string", description = "Synopsis of the book.", nullable = false)
    @Field("synopsis")
    private String synopsis;

    @JsonProperty("authorId")
    @Schema(type = "string", format = "uuid", description = "ID of the related author.", nullable = false)
    @Field("authorId")
    private String authorId;

    @JsonProperty("publisherId")
    @Schema(type = "string", format = "uuid", description = "ID of the related publisher.", nullable = false)
    @Field("publisherId")
    private String publisherId;

    @JsonProperty("publishYear")
    @Schema(type = "integer", example = "2023", description = "Year that the book published.", nullable = false)
    @Field("publishYear")
    private int publishYear;

    @JsonProperty("totalPage")
    @Schema(type = "integer", example = "255", description = "Total page of the book.", nullable = false)
    @Field("totalPage")
    private int totalPage;

    @JsonProperty("price")
    @Schema(type = "number", format = "double", description = "Price of the book in US dollar", nullable = false)
    @Field("price")
    private double price;

    @Size(min = 0, max = 255, message = "Maximum size limit character of this field is 255")
    @JsonProperty("category")
    @Schema(type = "string", description = "Category of the book.", nullable = false)
    @Field("category")
    private String category;

    @Nullable
    @JsonProperty("createdOn")
    @Schema(type = "string", format = "date-time", description = "Create date of the book.")
    @Field("createdOn")
    private LocalDateTime createdOn;

    public Book() {}

    private Book( 
        long isbn,
        String title,
        String synopsis,
        String authorId,
        String publisherId, 
        int publishYear, 
        int totalPage,
        double price, 
        String category
    ) {
        this.id = UUID.randomUUID().toString();
        this.isbn = Objects.requireNonNull(isbn);
        this.title = Objects.requireNonNull(title);
        this.synopsis = Objects.requireNonNull(synopsis);
        this.authorId = Objects.requireNonNull(authorId);
        this.publisherId = Objects.requireNonNull(publisherId);
        this.publishYear = Objects.requireNonNull(publishYear);
        this.totalPage = Objects.requireNonNull(totalPage);
        this.category = Objects.requireNonNull(category);
        this.price = Objects.requireNonNull(price);
        this.createdOn = LocalDateTime.now();
    }

    private Book(
        String id,
        long isbn,
        String title,
        String synopsis,
        String authorId,
        String publisherId, 
        int publishYear, 
        int totalPage,
        double price, 
        String category,
        LocalDateTime createdOn
    ) {
        this.id = Objects.requireNonNull(id);
        this.isbn = Objects.requireNonNull(isbn);
        this.title = Objects.requireNonNull(title);
        this.synopsis = Objects.requireNonNull(synopsis);
        this.authorId = Objects.requireNonNull(authorId);
        this.publisherId = Objects.requireNonNull(publisherId);
        this.publishYear = Objects.requireNonNull(publishYear);
        this.totalPage = Objects.requireNonNull(totalPage);
        this.category = Objects.requireNonNull(category);
        this.price = Objects.requireNonNull(price);
        this.createdOn = Objects.requireNonNull(createdOn);
    }

    public static Book getInstance(
        long isbn,
        String title,
        String synopsis,
        String authorId,
        String publisherId, 
        int publishYear, 
        int totalPage,
        double price,
        String category
    ) {
        return new Book(
            isbn, 
            title, 
            synopsis, 
            authorId, 
            publisherId, 
            publishYear, 
            totalPage, 
            price,
            category
        );
    }

    public static Book getInstance(
        String id,
        long isbn,
        String title,
        String synopsis,
        String authorId,
        String publisherId, 
        int publishYear, 
        int totalPage,
        double price, 
        String category,
        LocalDateTime createdOn
    ) {
        return new Book(
            id,
            isbn, 
            title, 
            synopsis, 
            authorId, 
            publisherId, 
            publishYear, 
            totalPage, 
            price,
            category,
            createdOn
        );
    }

    public void changeId(String newValue) {
        this.id = newValue;
    }

    public void changeISBN(long newValue) {
        this.isbn = newValue;
    }

    public void changeTitle(String newValue) {
        this.title = newValue;
    }

    public void changeSynopsis(String newValue) {
        this.synopsis = newValue;
    }

    public void changeAuthor(String newValue) {
        this.authorId = newValue;
    }

    public void changePublisher(String newValue) {
        this.publisherId = newValue;
    }

    public void changePublishYear(int newValue) {
        this.publishYear = newValue;
    }

    public void changeTotalPage(int newValue) {
        this.totalPage = newValue;
    }

    public void changePrice(double newValue) {
        this.price = newValue;
    }

    public void changeCategory(String newValue) {
        this.category = newValue;
    }

    public void changeCreatedOn(LocalDateTime newValue) {
        this.createdOn = newValue;
    }

    public String takeCurrentId() {
        return this.id;
    }

    public long takeCurrentISBN() {
        return this.isbn;
    }

    public String takeBookTitle() {
        return this.title;
    }

    public String takeBookSynopsis() {
        return this.synopsis;
    }

    public String takeAuthor() {
        return this.authorId;
    }

    public String takePublisher() {
        return this.publisherId;
    }

    public int takeBookPublishYear() {
        return this.publishYear;
    }

    public int takeBookTotalPage() {
        return this.totalPage;
    }

    public double takeBookPrice() {
        return this.price;
    }

    public String takeBookCategory() {
        return this.category;
    }

    public LocalDateTime takeBookCreatedOn() {
        return this.createdOn;
    }

    @Override
    public String toString() {
        return String.format(
            "Book{id=%s, isbn=%d, title=%s, synopsis=%s, authorId=%s, publisherId=%s, publishYear=%d, totalPage=%d, price=%d, category=%s, createdOn=%s}", 
            id, 
            isbn,
            title, 
            synopsis, 
            authorId, 
            publisherId, 
            publishYear, 
            totalPage, 
            price,
            category, 
            createdOn
        );
    }
}
