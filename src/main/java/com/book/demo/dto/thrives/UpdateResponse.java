package com.book.demo.dto.thrives;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class UpdateResponse {
    @JsonProperty("statusCode")
    @Schema(type = "integer", example = "200")
    private int statusCode;

    @JsonProperty("message")
    @Schema(type = "string", example = "Successfully updated existing data!")
    private String message;
}
