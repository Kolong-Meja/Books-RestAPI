package com.book.demo.dto.book;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record BookPatchPublisherIdDTO(
    @NotNull    
    @JsonProperty("publisherId")
    @Schema(type = "string", format = "uuid", description = "ID of the related publisher.", nullable = false)
    String publisherId
) {}
