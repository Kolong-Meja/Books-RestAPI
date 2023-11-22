package com.book.demo.dto.book;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record BookPatchAuthorIdDTO(
    @NotNull
    @JsonProperty("authorId")
    @Schema(type = "string", format = "uuid", description = "ID of the related author.", nullable = false)
    String authorId
) {}
