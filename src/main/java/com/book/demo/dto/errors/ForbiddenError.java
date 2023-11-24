package com.book.demo.dto.errors;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class ForbiddenError {
    @JsonProperty
    @Schema(type = "integer", example = "403")
    private int statusCode;

    @JsonProperty("success")
    @Schema(type = "boolean", example = "false")
    private boolean success;

    @JsonProperty
    @Schema(type = "string", example = "Access denied! client don't have permissions for this resource")
    private String message;
}
