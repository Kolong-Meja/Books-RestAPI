package com.book.demo.dto.publisher;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PublisherUpdateRequestDTO(
    @Size(min = 0, max = 255, message = "Maximum size limit character of this field is 255")
    @JsonProperty("name")
    @Schema(type = "string", description = "Name of the publisher", nullable = false)
    String name,

    @NotNull
    @JsonProperty("email")
    @Schema(type = "string", format = "email", description = "Email of the publisher", nullable = false)
    String email,

    @NotNull
    @JsonProperty("bio")
    @Schema(type = "string", description = "Bio of the publisher", nullable = false)
    String bio,

    @NotNull
    @JsonProperty("foundYear")
    @Schema(type = "integer", example = "2017", description = "Year of publisher was founded", nullable = false)
    int foundYear,

    @NotNull
    @JsonProperty("address")
    @Schema(type = "string", format = "address", description = "Address of the publisher", nullable = false)
    String address,

    @NotNull
    @JsonProperty("phoneNumber")
    @Schema(type = "string", example = "+021-7766-5544", description = "Phone Number of the publisher", nullable = false)
    String phoneNumber
) {}
