package com.book.demo.dto.thrives;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

public class RequestResponse {
    @JsonProperty("statusCode")
    @Schema(type = "integer", example = "201")
    private int statusCode;

    @JsonProperty("success")
    @Schema(type = "boolean", example = "true")
    private boolean success;
    
    @JsonProperty("message")
    @Schema(type = "string", example = "Successfully created new data!")
    private String message;

    @JsonProperty("data")
    @ArraySchema(schema = @Schema(type = "object"))
    private List<Map<String, Object>> data;
}
