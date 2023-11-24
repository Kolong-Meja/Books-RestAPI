package com.book.demo.dto.client;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record ClientPatchPasswordDTO(
    @NotNull
    @JsonProperty("password")
    @Schema(
        type = "string", 
        description = "New password of client.", 
        pattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
        nullable = false
    )
    String password
) {}
