package com.book.demo.dto.author;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AuthorRequestDTO(
    @NotNull
    @Size(min = 0, max = 255, message = "Maximum size limit character of this field is 255")
    @JsonProperty("fullname")
    @Schema(type = "string", description = "Fullname of the author.", nullable = false)
    String fullname,

    @NotNull
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
    String phoneNumber
) {}
