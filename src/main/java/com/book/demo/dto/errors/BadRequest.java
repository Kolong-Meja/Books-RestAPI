package com.book.demo.dto.errors;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class BadRequest {
    @JsonProperty("statusCode")
    @Schema(type = "integer", example = "400")
    private int statudCode;

    @JsonProperty("success")
    @Schema(type = "boolean", example = "false")
    private boolean success;

    @JsonProperty("message")
    @Schema(type = "string", example = "Bad request, server cannot proccess request.")
    private String message;
}
