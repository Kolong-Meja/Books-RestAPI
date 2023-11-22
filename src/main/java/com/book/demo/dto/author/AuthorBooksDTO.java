package com.book.demo.dto.author;

import java.time.LocalDateTime;
import java.util.List;

import com.book.demo.models.Book;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

public class AuthorBooksDTO {
    @JsonProperty("fullname")
    @Schema(type = "string", description = "Fullname of the author.", nullable = false)
    private String fullname;

    @JsonProperty("biography")
    @Schema(type = "string", description = "Biography of the author.", nullable = false)
    private String biography;

    @JsonProperty("email")
    @Schema(type = "string", format = "email", description = "Email of the author.", nullable = false)
    private String email;

    @JsonProperty("phoneNumber")
    @Schema(type = "string", example = "+021-7665-8765", description = "Phone Number of the author.", nullable = false)
    private String phoneNumber;

    @JsonProperty("books")
    @ArraySchema(schema = @Schema(type = "object"))
    private List<Book> books;

    @JsonProperty("createdOn")
    @Schema(type = "string", format = "date-time", description = "Create date time of the publisher data.")
    private LocalDateTime createdOn;

    public AuthorBooksDTO() {}

    public AuthorBooksDTO(
        String fullname, 
        String biography, 
        String email, 
        String phoneNumber, 
        List<Book> books,
        LocalDateTime createdOn
        ) {
        this.fullname = fullname;
        this.biography = biography;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.books = books;
        this.createdOn = createdOn;
    }

    public void setFullName(String newValue) {
        this.fullname = newValue;
    }

    public void setBiography(String newValue) {
        this.biography = newValue;
    }

    public void setEmail(String newValue) {
        this.email = newValue;
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

    public String getFullName() {
        return this.fullname;
    }

    public String getBiography() {
        return this.biography;
    }

    public String getEmail() {
        return this.email;
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
