package com.book.demo.dto.client;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.lang.Nullable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record ClientDTO(
    @Nullable
    @JsonProperty("id")
    @Schema(type = "string", format = "uuid", description = "ID of the client.")
    String id,

    @NotNull
    @JsonProperty("fullname")
    @Schema(type = "string", description = "Name of the client.")
    String fullname,

    @NotNull
    @Email
    @JsonProperty("email")
    @Schema(
        type = "string", 
        format = "email", 
        description = "Email of the client.",
        pattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",
        nullable = false
    )
    String email,

    @NotNull
    @JsonProperty("password")
    @Schema(
        type = "string", 
        description = "Password of the client.", 
        pattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
        nullable = false
    )
    String password,

    @Nullable
    @JsonProperty("createdOn")
    @Schema(type = "string", format = "date-time", description = "Created date time of the client data.")
    LocalDateTime createdOn
) {}
