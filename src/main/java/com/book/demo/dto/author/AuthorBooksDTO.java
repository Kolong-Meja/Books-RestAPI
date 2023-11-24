package com.book.demo.dto.author;

import java.time.LocalDateTime;
import java.util.List;

import com.book.demo.models.Book;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record AuthorBooksDTO(
    @JsonProperty("id")
    @Schema(type = "string", format = "uuid", description = "ID of the Author", nullable = true)
    String id,

    @JsonProperty("fullname")
    @Schema(type = "string", description = "Fullname of the author.", nullable = false)
    String fullname,

    @JsonProperty("biography")
    @Schema(type = "string", description = "Biography of the author.", nullable = false)
    String biography,

    @NotNull
    @JsonProperty("email")
    @Schema(
        type = "string", 
        format = "email", 
        description = "Email of the author.",
        pattern =  "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",
        nullable = false
    )
    String email,

    @NotNull
    @JsonProperty("phoneNumber")
    @Schema(
        type = "string", 
        example = "+919367788755", 
        description = "Phone Number of the author.",
        pattern =  "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$",
        nullable = false
    )
    String phoneNumber,

    @JsonProperty("books")
    @ArraySchema(schema = @Schema(type = "object"))
    List<Book> books,

    @JsonProperty("createdOn")
    @Schema(type = "string", format = "date-time", description = "Create date time of the publisher data.")
    LocalDateTime createdOn
) {}
