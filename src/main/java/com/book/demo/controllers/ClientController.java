package com.book.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.demo.dto.client.ClientPatchPasswordDTO;
import com.book.demo.dto.client.ClientUpdateRequestDTO;
import com.book.demo.dto.errors.BadRequest;
import com.book.demo.dto.errors.DataNotFound;
import com.book.demo.dto.errors.ForbiddenError;
import com.book.demo.dto.errors.InternalServerError;
import com.book.demo.dto.handlers.ResponseHandler;
import com.book.demo.dto.thrives.GetResponse;
import com.book.demo.dto.thrives.RemoveResponse;
import com.book.demo.dto.thrives.UpdateResponse;
import com.book.demo.models.Client;
import com.book.demo.services.ClientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Clients", description = "Endpoints for client endpoints.")
@RestController
@RequestMapping(value = "/api/v1/clients")
@SecurityRequirement(name = "bearer-key")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @Operation(summary = "Get client data by specific ID", description = "Get client book data based on specific ID.", tags = "Clients")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get Operation successfull", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetResponse.class))),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden Error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        @ApiResponse(responseCode = "404", description = "Data does not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataNotFound.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @GetMapping(value = "/{id}", produces = { "application/json" })
    public ResponseEntity<Object> getOneClientById(@PathVariable String id) {
        Client data = clientService.findClientById(id);
        if (data == null) return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false, "Data does not found.");
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successfully retrieved data!", data); 
    }

    @Operation(summary = "Get client data by specific email", description = "Get one client data based on specific email.", tags = "Clients")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get Operation successfull.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetResponse.class))),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden Error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        @ApiResponse(responseCode = "404", description = "Data does not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataNotFound.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @GetMapping(value = "/email/{email}", produces = { "application/json" })
    public ResponseEntity<Object> getOneClientByEmail(@PathVariable String email) {
        Client data = clientService.findClientByEmail(email);
        if (data == null) return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false, "Data does not found.");
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successfully retrieved data!", data);
    }

    @Operation(
        summary = "Renew the client data by specific ID", 
        description = "Renew the client data by specified ID, and get response from it.", 
        tags = "Clients"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Renew operation successfull", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UpdateResponse.class))),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden Error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        @ApiResponse(responseCode = "404", description = "Data does not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataNotFound.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @PutMapping(value = "/{id}", consumes = { "application/json" })
    public ResponseEntity<Object> updateAllClientData(@PathVariable String id, @RequestBody ClientUpdateRequestDTO clientUpdateRequestDTO) {
        clientService.renewClient(id, clientUpdateRequestDTO);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successfully updated data!");
    }

    @Operation(
        summary = "Renew the client password", 
        description = "Renew the client password, and get response from it.", 
        tags = "Clients"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Renew operation successfull", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UpdateResponse.class))),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden Error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        @ApiResponse(responseCode = "404", description = "Data does not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataNotFound.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @PatchMapping(value = "/{id}/password", consumes = { "application/json" })
    public ResponseEntity<Object> updateClientPassword(@PathVariable String id, @RequestBody ClientPatchPasswordDTO clientPatchPasswordDTO) {
        clientService.renewClient(id, clientPatchPasswordDTO);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successfully updated data!");
    }

    @Operation(
        summary = "Remove the client data by specified ID", 
        description = "Remove the client data by specified ID, and get response from it.", 
        tags = "Clients"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Remove operation successfull.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RemoveResponse.class))),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden Error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        @ApiResponse(responseCode = "404", description = "Data does not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataNotFound.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> removeOneClient(@PathVariable String id) {
        clientService.discardClient(id);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successfully removed one data!");
    }
}
