package com.book.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.demo.dto.client.ClientDTO;
import com.book.demo.dto.client.ClientLoginDTO;
import com.book.demo.dto.errors.BadRequest;
import com.book.demo.dto.errors.InternalServerError;
import com.book.demo.dto.handlers.ResponseHandler;
import com.book.demo.dto.thrives.GetResponse;
import com.book.demo.dto.thrives.RequestResponse;
import com.book.demo.services.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentications", description = "Endpoints for authentication operations.")
@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @Operation(summary = "Create new account", description = "Create new account, and get a response from it.", tags = "Authentications")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Create operation succesfull.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RequestResponse.class))),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @PostMapping(value = "signup", consumes = { "application/json" })
    public ResponseEntity<Object> signUpAccount(@RequestBody ClientDTO clientDTO) {
        String data = authenticationService.signUp(clientDTO);
        return ResponseHandler.generateResponse(HttpStatus.CREATED, true, "Successfully sign up new account!", data);
    }

    @Operation(summary = "Login exist account", description = "Login exist account, and get a response from it.", tags = "Authentications")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login operation succesfull.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetResponse.class))),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @PostMapping(value = "signin", consumes = { "application/json" })
    public ResponseEntity<Object> signInAccount(@RequestBody ClientLoginDTO clientLoginDTO) {
        String data = authenticationService.signIn(clientLoginDTO);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successfully sign in account!", data);
    }
}
