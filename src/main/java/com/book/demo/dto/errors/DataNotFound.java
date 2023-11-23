package com.book.demo.dto.errors;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

public class DataNotFound {
    @JsonProperty("statusCode")
    @Schema(type = "integer", example = "404")
    private int statudCode;

    @JsonProperty("success")
    @Schema(type = "boolean", example = "false")
    private boolean success;

    @JsonProperty("message")
    @Schema(type = "string", example = "Data does not found.")
    private String message;

    @JsonProperty("data")
    @ArraySchema(schema = @Schema(type = "object"))
    private List<Map<String, Object>> data;
}
