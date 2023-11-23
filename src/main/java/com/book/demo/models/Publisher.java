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

@Document(collection = "PublisherCollection")
public class Publisher {
    @Id
    @JsonProperty("id")
    @Schema(type = "string", format = "uuid", description = "ID of the publisher")
    private String id;

    @Size(min = 0, max = 255, message = "Maximum size limit character of this field is 255")
    @JsonProperty("name")
    @Schema(type = "string", description = "Name of the publisher", nullable = false)
    @Field("name")
    private String name;
    
    @Indexed(unique = true)
    @JsonProperty("email")
    @Schema(type = "string", format = "email", description = "Email of the publisher", nullable = false)
    @Field("email")
    private String email;
    
    @JsonProperty("bio")
    @Schema(type = "string", description = "Short bio of the publisher")
    @Field("bio")
    private String bio;

    @JsonProperty("foundYear")
    @Schema(type = "integer", example = "2017", description = "Year of publisher was founded", nullable = false)
    @Field("foundYear")
    private int foundYear;

    @JsonProperty("address")
    @Schema(type = "string", format = "address", description = "Address of the publisher", nullable = false)
    @Field("address")
    private String address;

    @Indexed(unique = true)
    @JsonProperty("phoneNumber")
    @Schema(type = "string", format = "phone-number", description = "Phone Number of the publisher", nullable = false)
    private String phoneNumber;

    @Nullable
    @JsonProperty("createdOn")
    @Schema(type = "string", format = "date-time", description = "Create date time of the publisher data")
    private LocalDateTime createdOn;

    public Publisher() {}

    private Publisher( 
        String name, 
        String email, 
        String bio, 
        int foundYear, 
        String address, 
        String phoneNumber
    ) {
        this.id = UUID.randomUUID().toString();
        this.name = Objects.requireNonNull(name);
        this.email = Objects.requireNonNull(getVerifiedEmail(email));
        this.bio = Objects.requireNonNull(bio);
        this.foundYear = Objects.requireNonNull(foundYear);
        this.address = Objects.requireNonNull(address);
        this.phoneNumber = Objects.requireNonNull(getVerifiedPhoneNumber(phoneNumber));
        this.createdOn = LocalDateTime.now();
    }

    public static Publisher getInstance(
        String name, 
        String email, 
        String bio, 
        int foundYear, 
        String address, 
        String phoneNumber
    ) {
        return new Publisher(
            name, 
            email, 
            bio, 
            foundYear, 
            address, 
            phoneNumber
        );
    }

    public void changeCurrentId(String newId) {
        this.id = newId;
    }

    public void changeName(String newName) {
        this.name = newName;
    }

    public void changeEmail(String newEmail) {
        this.email = newEmail;
    }

    public void changeBio(String newBio) {
        this.bio = newBio;
    }

    public void changeFoundYear(int newFoundYear) {
        this.foundYear = newFoundYear;
    }

    public void changeAddress(String newAddress) {
        this.address = newAddress;
    }

    public void changePhoneNumber(String newPhoneNumber) {
        this.phoneNumber = newPhoneNumber;
    }

    public void changeCreatedOn(LocalDateTime newDateTime) {
        this.createdOn = newDateTime;
    }

    public String takeCurrentId() {
        return this.id;
    }

    public String takeName() {
        return this.name;
    }

    public String takeEmail() {
        return this.email;
    }

    public String takeBio() {
        return this.bio;
    }

    public int takeFoundYear() {
        return this.foundYear;
    }

    public String takeAddress() {
        return this.address;
    }

    public String takePhoneNumber() {
        return this.phoneNumber;
    }

    public LocalDateTime takeCreatedOn() {
        return this.createdOn;
    }

    @Override
    public String toString() {
        return String.format("Publisher{id=%s, name=%s, email=%s, bio=%s, foundYear=%d, address=%s, phoneNumber=%s, createdOn=%s}", id, name, email, bio, foundYear, address, phoneNumber, createdOn);
    }

    private static final String getVerifiedEmail(String email) {
        if (!email.contains("@") && !email.contains(".com")) {
            throw new IllegalArgumentException(String.format("'%s' not a verified email.", email));
        } else {
            return email;
        }
    }

    private static final String getVerifiedPhoneNumber(String phoneNumber) {
        if (!phoneNumber.contains("+") && !phoneNumber.contains("-")) {
            throw new IllegalArgumentException(String.format("'%s' not a verified phone number", phoneNumber));
        } else {
            return phoneNumber;
        }
    }
}
