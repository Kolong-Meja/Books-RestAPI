package com.book.demo.dto.thrives;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public class AuthenticationResponse {
    @JsonProperty("token")
    @Schema(type = "string", description = "Authentication token.")
    private String token;

    public AuthenticationResponse() {}

    public AuthenticationResponse(String token) {
        this.token = token;
    }

    public void setAuthenticationToken(String newToken) {
        this.token = newToken;
    }

    public String getAuthenticationToken() {
        return this.token;
    }

    @Override
    public String toString() {
        return String.format("Token{token=%s}", token);
    }
}
