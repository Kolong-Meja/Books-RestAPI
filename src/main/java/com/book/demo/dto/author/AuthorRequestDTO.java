package com.book.demo.dto.author;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.lang.Nullable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AuthorRequestDTO(
    @Nullable
    @JsonProperty("id")
    @Schema(type = "string", format = "uuid", description = "ID of the author.")    
    String id,

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
    @Schema(type = "string", format = "email", description = "Email of the author.", nullable = false)
    String email,

    @NotNull
    @JsonProperty("phoneNumber")
    @Schema(type = "string", example = "+021-7665-8765", description = "Phone Number of the author.", nullable = false)
    String phoneNumber,

    @Nullable
    @JsonProperty("createdOn")
    @Schema(type = "string", format = "date-time", description = "Create date of the book.")
    LocalDateTime createdOn
) {}
