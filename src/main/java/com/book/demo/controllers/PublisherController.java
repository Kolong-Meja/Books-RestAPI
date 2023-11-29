package com.book.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.book.demo.dto.errors.BadRequest;
import com.book.demo.dto.errors.DataNotFound;
import com.book.demo.dto.errors.ForbiddenError;
import com.book.demo.dto.errors.InternalServerError;
import com.book.demo.dto.handlers.ResponseHandler;
import com.book.demo.dto.publisher.PublisherBooksDTO;
import com.book.demo.dto.publisher.PublisherRequestDTO;
import com.book.demo.dto.publisher.PublisherUpdateRequestDTO;
import com.book.demo.dto.thrives.GetResponse;
import com.book.demo.dto.thrives.RemoveResponse;
import com.book.demo.dto.thrives.RequestResponse;
import com.book.demo.dto.thrives.UpdateResponse;
import com.book.demo.models.Publisher;
import com.book.demo.services.PublisherService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Publishers", description = "Endpoint for publisher operations")
@RequestMapping("/api/v1/publishers")
@RestController
@SecurityRequirement(name = "bearer-key")
public class PublisherController {
    @Autowired
    private PublisherService publisherService;

    @Operation(summary = "Get all publishers data", description = "Get all publishers data like title, author name, publisher name, etc.", tags = "Publishers")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get Operation successfull.", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GetResponse.class)))}),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden Error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        @ApiResponse(responseCode = "404", description = "Data does not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataNotFound.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @GetMapping(value = "/", produces = { "application/json" })
    public ResponseEntity<Object> getAllPublishers() {
        List<Publisher> datas = publisherService.findAllPublishers();
        if (datas.isEmpty()) return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false, "Data does not found.");
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successfully retrieved all data!", datas);
    }

    @Operation(summary = "Get publisher data by specific ID", description = "Get one publisher data based on specific ID.", tags = "Publishers")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get Operation successfull", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetResponse.class))),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden Error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        @ApiResponse(responseCode = "404", description = "Data does not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataNotFound.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @GetMapping(value = "/{id}", produces = { "application/json" })
    public ResponseEntity<Object> getOnePublisherById(@PathVariable String id) {
        Publisher data = publisherService.findPublisherById(id);
        if (data == null) return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false, "Data does not found.");
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successfully retrieved data!", data);
    }

    @Operation(summary = "Get publisher by specific name", description = "Get one publisher based on specific name.", tags = "Publishers")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get Operation successfull.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetResponse.class))),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden Error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        @ApiResponse(responseCode = "404", description = "Data does not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataNotFound.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @GetMapping(value = "/name", produces = { "application/json" })
    public ResponseEntity<Object> getOnePublisherByName(@RequestParam String name) {
        Publisher data = publisherService.findPublisherByName(name);
        if (data == null) return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false, "Data does not found.");
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successfully retrieved data!", data);
    }

    @Operation(summary = "Get publisher by specific email", description = "Get one publisher based on specific email.", tags = "Publishers")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get Operation successfull.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetResponse.class))),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden Error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        @ApiResponse(responseCode = "404", description = "Data does not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataNotFound.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @GetMapping(value = "/email", produces = { "application/json" })
    public ResponseEntity<Object> getOnePublisherByEmail(@RequestParam String email) {
        Publisher data = publisherService.findPublisherByEmail(email);
        if (data == null) return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false, "Data does not found.");
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successfully retrieved data!", data);
    }

    @Operation(summary = "Get publisher and they books from specific publisher ID", description = "Get publisher and they books from specific publisher ID.", tags = "Publishers")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get Operation successfull.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetResponse.class))),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden Error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        @ApiResponse(responseCode = "404", description = "Data does not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataNotFound.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @GetMapping(value = "/{id}/books", produces = { "application/json" })
    public ResponseEntity<Object> getPublisherBooks(@PathVariable String id) {
        PublisherBooksDTO data = publisherService.findPublisherBooks(id);
        if (data == null) return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false, "Data does not found.");
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successfully retrieved data!", data);
    }

    @Operation(
        summary = "Add new publisher data", 
        description = "Add new publisher data, and get a response from it.", 
        tags = "Publishers")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Add operation succesfull.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RequestResponse.class))),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden Error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @PostMapping(value = "/", consumes = { "application/json" })
    public ResponseEntity<Object> createNewPublisher(@RequestBody PublisherRequestDTO publisherRequestDTO) {
        Publisher data = publisherService.addNewPublisher(publisherRequestDTO);
        return ResponseHandler.generateResponse(HttpStatus.CREATED, true, "Successfully created new data!", data);
    }

    @Operation(
        summary = "Renew the exist publisher data by specific ID", 
        description = "Renew the exist publisher data by specified ID, and get response from it.", 
        tags = "Publishers")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Renew operation successfull", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UpdateResponse.class))),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden Error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        @ApiResponse(responseCode = "404", description = "Data does not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataNotFound.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @PutMapping(value = "/{id}", consumes = { "application/json" })
    public ResponseEntity<Object> updateAllPublisherData(@PathVariable String id, @RequestBody PublisherUpdateRequestDTO publisherUpdateRequestDTO) {
        publisherService.renewPublisher(id, publisherUpdateRequestDTO);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successfully updated exist data!");
    }

    @Operation(
        summary = "Remove all exist books.", 
        description = "Remove all exist books, and get response from it.", 
        tags = "Publishers")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Remove operation successfull.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RemoveResponse.class))),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden Error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        @ApiResponse(responseCode = "404", description = "Data does not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataNotFound.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @DeleteMapping(value = "/")
    public ResponseEntity<Object> removeAllPublishers() {
        publisherService.discardPublishers();
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successfully removed all data!");
    }

    @Operation(
        summary = "Remove the exist book by specified ID", 
        description = "Remove the exist book by specified ID, and get response from it.", 
        tags = "Publishers")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Remove operation successfull.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RemoveResponse.class))),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden Error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        @ApiResponse(responseCode = "404", description = "Data does not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataNotFound.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> removeOnePublisher(@PathVariable String id) {
        publisherService.discardPublisher(id);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successfully removed data!");
    }
}
