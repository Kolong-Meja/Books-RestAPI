package com.book.demo.dto.publisher;

import java.time.LocalDateTime;
import java.util.List;

import com.book.demo.models.Book;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

public class PublisherBooksDTO {
    @JsonProperty("name")
    @Schema(type = "string", description = "Name of the publisher", nullable = false)
    private String name;

    @JsonProperty("email")
    @Schema(type = "string", format = "email", description = "Email of the publisher", nullable = false)
    private String email;

    @JsonProperty("bio")
    @Schema(type = "string", description = "Bio of the publisher", nullable = false)
    private String bio;

    @JsonProperty("foundYear")
    @Schema(type = "integer", example = "2017", description = "Year of publisher was founded", nullable = false)
    private int foundYear;

    @JsonProperty("address")
    @Schema(type = "string", format = "address", description = "Address of the publisher", nullable = false)
    private String address;

    @JsonProperty("phoneNumber")
    @Schema(type = "string", example = "+021-7766-5544", description = "Phone Number of the publisher", nullable = false)
    private String phoneNumber;

    @JsonProperty("books")
    @ArraySchema(schema = @Schema(type = "object"))
    private List<Book> books;

    @JsonProperty("createdOn")
    @Schema(type = "string", format = "date-time", description = "Create date time of the publisher data.")
    private LocalDateTime createdOn;
    
    public PublisherBooksDTO() {}

    public PublisherBooksDTO(
        String name, 
        String email,
        String bio, 
        int foundYear, 
        String address, 
        String phoneNumber, 
        List<Book> books,
        LocalDateTime createdOn
        ) {
        this.name = name;
        this.email = email;
        this.bio = bio;
        this.foundYear = foundYear;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.books = books;
        this.createdOn = createdOn;
    }

    public void setName(String newValue) {
        this.name = newValue;
    }

    public void setEmail(String newValue) {
        this.email = newValue;
    }

    public void setBio(String newValue) {
        this.bio = newValue;
    }

    public void setFoundYear(int newValue) {
        this.foundYear = newValue;
    }

    public void setAddress(String newValue) {
        this.address = newValue;
    }

    public void setPhoneNumber(String newValue) {
        this.phoneNumber = newValue;
    }

    public void setBooks(List<Book> newValues) {
        this.books = newValues;
    }

    public void setCreatedOn(LocalDateTime newDateTime) {
        this.createdOn = newDateTime;
    }

    public String getPublisherName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getBio() {
        return this.bio;
    }

    public int getFoundYear() {
        return this.foundYear;
    }

    public String getAddress() {
        return this.address;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public List<Book> getBooks() {
        return this.books;
    }

    public LocalDateTime getCreatedOn() {
        return this.createdOn;
    }
}
