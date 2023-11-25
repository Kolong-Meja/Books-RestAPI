package com.book.demo.models;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.book.demo.utils.VerifiedDataUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.lang.Nullable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Document(collection = "AuthorCollection")
public class Author {
    @Id
    @JsonProperty("id")
    @Schema(type = "string", format = "uuid", description = "ID of the author.")
    private String id;

    @Indexed
    @Size(min = 0, max = 255, message = "Maximum size limit character of this field is 255")
    @JsonProperty("fullname")
    @Schema(type = "string", description = "Fullname of the author.", nullable = false)
    @Field("fullname")
    private String fullname;

    @JsonProperty("biography")
    @Schema(type = "string", description = "Biography of the author.", nullable = false)
    @Field("biography")
    private String biography;

    @Indexed(unique = true)
    @JsonProperty("email")
    @Schema(
        type = "string", 
        format = "email", 
        description = "Email of the author.",
        pattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", 
        nullable = false)
    @Field("email")
    private String email;

    @Indexed(unique = true)
    @JsonProperty("phoneNumber")
    @Schema(
        type = "string", 
        example = "+919367788755", 
        description = "Phone Number of the author.", 
        pattern = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$",
        nullable = false
    )
    @Field("phoneNumber")
    private String phoneNumber;
    
    @Nullable
    @JsonProperty("createdOn")
    @Schema(type = "string", format = "date-time", description = "Create date of the book.")
    @Field("createdOn")
    private LocalDateTime createdOn;

    public Author() {}

    private Author(
        String fullname, 
        String biography, 
        String email, 
        String phoneNumber
    ) {
        this.id = UUID.randomUUID().toString();
        this.fullname = Objects.requireNonNull(fullname);
        this.biography = Objects.requireNonNull(biography);
        this.email = Objects.requireNonNull(VerifiedDataUtil.getVerifiedEmail(email));
        this.phoneNumber = Objects.requireNonNull(VerifiedDataUtil.getVerifiedPhoneNumber(phoneNumber));
        this.createdOn = LocalDateTime.now();
    }

    private Author(
        String id,
        String fullname, 
        String biography, 
        String email, 
        String phoneNumber,
        LocalDateTime createdOn
    ) {
        this.id = Objects.requireNonNull(id);
        this.fullname = Objects.requireNonNull(fullname);
        this.biography = Objects.requireNonNull(biography);
        this.email = Objects.requireNonNull(VerifiedDataUtil.getVerifiedEmail(email));
        this.phoneNumber = Objects.requireNonNull(VerifiedDataUtil.getVerifiedPhoneNumber(phoneNumber));
        this.createdOn = Objects.requireNonNull(createdOn);
    }

    public static Author getInstance(
        String fullname, 
        String biography, 
        String email, 
        String phoneNumber
    ) {
        return new Author(fullname, biography, email, phoneNumber);
    }

    public static Author getInstance(
        String id,
        String fullname, 
        String biography, 
        String email, 
        String phoneNumber,
        LocalDateTime createdOn
    ) {
        return new Author(id, fullname, biography, email, phoneNumber, createdOn);
    }

    public void changeId(String newValue) {
        this.id = newValue;
    }

    public void changeFullName(String newValue) {
        this.fullname = newValue;
    }

    public void changeBiography(String newValue) {
        this.biography = newValue;
    }

    public void changeEmail(String newValue) {
        this.email = newValue;
    }

    public void changePhoneNumber(String newValue) {
        this.phoneNumber = newValue;
    }

    public void changeCreatedOn(LocalDateTime newValue) {
        this.createdOn = newValue;
    }

    public String takeCurrentId() {
        return this.id;
    }

    public String takeFullName() {
        return this.fullname;
    }

    public String takeBiography() {
        return this.biography;
    }

    public String takeEmail() {
        return this.email;
    }

    public String takePhoneNumber() {
        return this.phoneNumber;
    }

    public LocalDateTime takeCreatedOn() {
        return this.createdOn;
    }

    @Override
    public String toString() {
        return String.format(
            "Author{id=%s, fullname=%s, biography=%s, email=%s, phoneNumber=%s, createdOn=%s}", 
            id, 
            fullname,
            biography,
            email,
            phoneNumber,
            createdOn
            );
    }
}
