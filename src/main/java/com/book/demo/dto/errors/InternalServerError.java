package com.book.demo.dto.errors;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

public class InternalServerError {
    @JsonProperty("statusCode")
    @Schema(type = "integer", example = "500")
    private int statusCode;

    @JsonProperty("success")
    @Schema(type = "boolean", example = "false")
    private boolean success;

    @JsonProperty("message")
    @Schema(type = "string", example = "Internal Server Error")
    private String message;

    @JsonProperty("content")
    @ArraySchema(schema = @Schema(type = "object"))
    private List<Map<String, Object>> content;
}
