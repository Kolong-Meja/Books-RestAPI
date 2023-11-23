package com.book.demo.dto.publisher;

import java.time.LocalDateTime;
import java.util.List;

import com.book.demo.models.Book;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

public record PublisherBooksDTO(
    @JsonProperty("name")
    @Schema(type = "string", description = "Name of the publisher", nullable = false)
    String name,

    @JsonProperty("email")
    @Schema(type = "string", format = "email", description = "Email of the publisher", nullable = false)
    String email,

    @JsonProperty("bio")
    @Schema(type = "string", description = "Bio of the publisher", nullable = false)
    String bio,

    @JsonProperty("foundYear")
    @Schema(type = "integer", example = "2017", description = "Year of publisher was founded", nullable = false)
    int foundYear,

    @JsonProperty("address")
    @Schema(type = "string", format = "address", description = "Address of the publisher", nullable = false)
    String address,

    @JsonProperty("phoneNumber")
    @Schema(type = "string", example = "+021-7766-5544", description = "Phone Number of the publisher", nullable = false)
    String phoneNumber,

    @JsonProperty("books")
    @ArraySchema(schema = @Schema(type = "object"))
    List<Book> books,

    @JsonProperty("createdOn")
    @Schema(type = "string", format = "date-time", description = "Create date time of the publisher data.")
    LocalDateTime createdOn
) {}
