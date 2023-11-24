package com.book.demo.dto.publisher;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.lang.Nullable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PublisherDTO(
    @Nullable
    @JsonProperty("id")
    @Schema(type = "string", format = "uuid", description = "ID of the publisher")
    String id,

    @NotNull
    @Size(min = 0, max = 255, message = "Maximum size limit character of this field is 255")
    @JsonProperty("name")
    @Schema(type = "string", description = "Name of the publisher", nullable = false)
    String name,

    @NotNull
    @JsonProperty("email")
    @Schema(
        type = "string", 
        format = "email", 
        description = "Email of the publisher", 
        pattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",
        nullable = false
    )
    String email,

    @NotNull
    @JsonProperty("phoneNumber")
    @Schema(
        type = "string", 
        example = "+919367788755", 
        description = "Phone Number of the publisher",
        pattern =  "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$", 
        nullable = false
    )
    String phoneNumber,

    @NotNull
    @JsonProperty("bio")
    @Schema(type = "string", description = "Bio of the publisher", nullable = false)
    String bio,

    @NotNull
    @JsonProperty("address")
    @Schema(type = "string", format = "address", description = "Address of the publisher", nullable = false)
    String address,

    @NotNull
    @JsonProperty("foundYear")
    @Schema(type = "integer", example = "2017", description = "Year of publisher was founded", nullable = false)
    int foundYear,

    @Nullable
    @JsonProperty("createdOn")
    @Schema(type = "string", format = "date-time", description = "Create date time of the publisher data")
    LocalDateTime createdOn
) {}
