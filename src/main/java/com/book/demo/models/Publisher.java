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
    @Schema(
        type = "string", 
        format = "email", 
        description = "Email of the publisher", 
        pattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",
        nullable = false
    )
    @Field("email")
    private String email;
    
    @Indexed(unique = true)
    @JsonProperty("phoneNumber")
    @Schema(
        type = "string", 
        example = "+919367788755", 
        description = "Phone Number of the publisher",
        pattern = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$",
        nullable = false
    )
    private String phoneNumber;
    
    @JsonProperty("bio")
    @Schema(type = "string", description = "Short bio of the publisher")
    @Field("bio")
    private String bio;

    @JsonProperty("address")
    @Schema(type = "string", format = "address", description = "Address of the publisher", nullable = false)
    @Field("address")
    private String address;
    
    @JsonProperty("foundYear")
    @Schema(type = "integer", example = "2017", description = "Year of publisher was founded", nullable = false)
    @Field("foundYear")
    private int foundYear;

    @Nullable
    @JsonProperty("createdOn")
    @Schema(type = "string", format = "date-time", description = "Create date time of the publisher data")
    private LocalDateTime createdOn;

    public Publisher() {}

    private Publisher( 
        String name, 
        String email, 
        String phoneNumber,
        String bio, 
        String address, 
        int foundYear
    ) {
        this.id = UUID.randomUUID().toString();
        this.name = Objects.requireNonNull(name);
        this.email = Objects.requireNonNull(VerifiedDataUtil.getVerifiedEmail(email));
        this.phoneNumber = Objects.requireNonNull(VerifiedDataUtil.getVerifiedPhoneNumber(phoneNumber));
        this.bio = Objects.requireNonNull(bio);
        this.foundYear = Objects.requireNonNull(foundYear);
        this.address = Objects.requireNonNull(address);
        this.createdOn = LocalDateTime.now();
    }

    private Publisher(
        String id,
        String name, 
        String email, 
        String phoneNumber,
        String bio, 
        String address, 
        int foundYear,
        LocalDateTime createdOn
    ) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.email = Objects.requireNonNull(VerifiedDataUtil.getVerifiedEmail(email));
        this.phoneNumber = Objects.requireNonNull(VerifiedDataUtil.getVerifiedPhoneNumber(phoneNumber));
        this.bio = Objects.requireNonNull(bio);
        this.foundYear = Objects.requireNonNull(foundYear);
        this.address = Objects.requireNonNull(address);
        this.createdOn = Objects.requireNonNull(createdOn);
    }

    public static Publisher getInstance(
        String name, 
        String email, 
        String phoneNumber,
        String bio, 
        String address, 
        int foundYear
    ) {
        return new Publisher(
            name, 
            email, 
            phoneNumber,
            bio, 
            address, 
            foundYear 
        );
    }

    public static Publisher getInstance(
        String id,
        String name, 
        String email, 
        String phoneNumber,
        String bio, 
        String address, 
        int foundYear,
        LocalDateTime createdOn
    ) {
        return new Publisher(
            id, 
            name, 
            email, 
            phoneNumber, 
            bio, 
            address, 
            foundYear, 
            createdOn
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

    public void changePhoneNumber(String newPhoneNumber) {
        this.phoneNumber = newPhoneNumber;
    }

    public void changeBio(String newBio) {
        this.bio = newBio;
    }

    public void changeAddress(String newAddress) {
        this.address = newAddress;
    }

    public void changeFoundYear(int newFoundYear) {
        this.foundYear = newFoundYear;
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

    public String takePhoneNumber() {
        return this.phoneNumber;
    }

    public String takeBio() {
        return this.bio;
    }

    public String takeAddress() {
        return this.address;
    }

    public int takeFoundYear() {
        return this.foundYear;
    }

    public LocalDateTime takeCreatedOn() {
        return this.createdOn;
    }

    @Override
    public String toString() {
        return String.format("Publisher{id=%s, name=%s, email=%s, phoneNumber=%s, bio=%s, address=%s, foundYear=%d, createdOn=%s}", id, name, email, phoneNumber, bio, address, foundYear, createdOn);
    }
}
