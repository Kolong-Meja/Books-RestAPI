package com.book.demo.dto.thrives;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class RemoveResponse {
    @JsonProperty("statusCode")
    @Schema(type = "integer", example = "200")
    private int statusCode;

    @JsonProperty("success")
    @Schema(type = "boolean", example = "true")
    private boolean success;

    @JsonProperty("message")
    @Schema(type = "string", example = "Successfully removed data!")
    private String message;
}
