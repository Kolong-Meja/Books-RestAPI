package com.book.demo.dto.client;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record ClientUpdateRequestDTO(
    @NotNull
    @JsonProperty
    @Schema(type = "string", description = "Name of the client.")
    String fullname,
    
    @NotNull
    @JsonProperty("email")
    @Schema(type = "string", format = "email", description = "Email of the client.")
    String email,

    @NotNull
    @JsonProperty("password")
    @Schema(type = "string", description = "Password of the client.")
    String password
) {}
