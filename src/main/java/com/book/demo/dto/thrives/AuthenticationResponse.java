package com.book.demo.dto.thrives;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthenticationResponse(
    @JsonProperty("token")
    @Schema(type = "string", description = "Authentication token.")
    String token
) {}
