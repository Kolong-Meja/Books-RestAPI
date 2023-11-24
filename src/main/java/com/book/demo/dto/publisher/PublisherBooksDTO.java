package com.book.demo.dto.publisher;

import java.time.LocalDateTime;
import java.util.List;

import com.book.demo.models.Book;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

public record PublisherBooksDTO(
    @JsonProperty("id")
    @Schema(type = "string", format = "uuid", description = "ID of the publisher.", nullable = true)
    String id,
    
    @JsonProperty("name")
    @Schema(type = "string", description = "Name of the publisher", nullable = false)
    String name,

    @JsonProperty("email")
    @Schema(
        type = "string", 
        format = "email", 
        description = "Email of the publisher", 
        pattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",
        nullable = false
    )
    String email,

    @JsonProperty("phoneNumber")
    @Schema(
        type = "string", 
        example = "+919367788755", 
        description = "Phone Number of the publisher",
        pattern =  "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$", 
        nullable = false
    )
    String phoneNumber,

    @JsonProperty("bio")
    @Schema(type = "string", description = "Bio of the publisher", nullable = false)
    String bio,

    @JsonProperty("address")
    @Schema(type = "string", format = "address", description = "Address of the publisher", nullable = false)
    String address,
    
    @JsonProperty("foundYear")
    @Schema(type = "integer", example = "2017", description = "Year of publisher was founded", nullable = false)
    int foundYear,

    @JsonProperty("books")
    @ArraySchema(schema = @Schema(type = "object"))
    List<Book> books,

    @JsonProperty("createdOn")
    @Schema(type = "string", format = "date-time", description = "Create date time of the publisher data.")
    LocalDateTime createdOn
) {}
