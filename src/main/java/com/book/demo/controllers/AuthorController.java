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

import com.book.demo.dto.author.AuthorBooksDTO;
import com.book.demo.dto.author.AuthorRequestDTO;
import com.book.demo.dto.author.AuthorUpdateRequestDTO;
import com.book.demo.dto.errors.BadRequest;
import com.book.demo.dto.errors.DataNotFound;
import com.book.demo.dto.errors.ForbiddenError;
import com.book.demo.dto.errors.InternalServerError;
import com.book.demo.dto.handlers.ResponseHandler;
import com.book.demo.dto.thrives.GetResponse;
import com.book.demo.dto.thrives.RemoveResponse;
import com.book.demo.dto.thrives.RequestResponse;
import com.book.demo.dto.thrives.UpdateResponse;
import com.book.demo.models.Author;
import com.book.demo.services.AuthorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authors", description = "Endpoints for Author operations.")
@RequestMapping("/api/v1/authors")
@RestController
@SecurityRequirement(name = "bearer-key")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @Operation(summary = "Get all authors data", description = "Get all authors data, and get response from it.", tags = "Authors")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get Operation successfull.", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GetResponse.class)))}),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden Error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        @ApiResponse(responseCode = "404", description = "Data does not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataNotFound.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @GetMapping(value = "/", produces = { "application/json" })
    public ResponseEntity<Object> getAllAuthorData() {
        List<Author> datas = authorService.findAllAuthors();
        if (datas.isEmpty()) return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false, "Data does not found", datas);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successfully retrieved data!", datas); 
    }

    @Operation(summary = "Get author data by specific ID", description = "Get one author data based on specific ID.", tags = "Authors")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get Operation successfull", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetResponse.class))),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden Error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        @ApiResponse(responseCode = "404", description = "Data does not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataNotFound.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @GetMapping(value = "/{id}", produces = { "application/json" })
    public ResponseEntity<Object> getAuthorById(@PathVariable String id) {
        Author data = authorService.findAuthorById(id);
        if (data == null) return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false, "Data does not found", data);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successfully retrieved data!", data);
    }

    @Operation(summary = "Get all authors by specifiec name", description = "Get all authors from specified name, and get response from it.", tags = "Authors")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get Operation successfull.", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GetResponse.class)))}),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden Error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        @ApiResponse(responseCode = "404", description = "Data does not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataNotFound.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @GetMapping(value = "/fullname", produces = { "application/json" })
    public ResponseEntity<Object> getAuthorByName(@RequestParam String name) {
        List<Author> datas = authorService.findAuthorByName(name);
        if (datas.isEmpty()) return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false, "Data does not found.");
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successfully retrieved all data!", datas);
    }

    @Operation(summary = "Get author data by specific email", description = "Get one author data based on specific email.", tags = "Authors")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get Operation successfull.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetResponse.class))),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden Error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        @ApiResponse(responseCode = "404", description = "Data does not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataNotFound.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @GetMapping(value = "/email", produces = { "application/json" })
    public ResponseEntity<Object> getAuthorByEmail(@RequestParam String email) {
        Author data = authorService.findAuthorByEmail(email);
        if (data == null) return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false, "Data does not found", data);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successfully retrieved data!", data);
    }

    @Operation(summary = "Get author and they books from specific author ID", description = "Get author and they books from specific author ID.", tags = "Authors")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get Operation successfull.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetResponse.class))),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden Error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        @ApiResponse(responseCode = "404", description = "Data does not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataNotFound.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @GetMapping(value = "/{id}/books", produces = { "application/json" })
    public ResponseEntity<Object> getAuthorBooks(@PathVariable String id) {
        AuthorBooksDTO data = authorService.findAuthorBooks(id);
        if (data == null) return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false, "Data does not found.");
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successfully retrieved data!", data);
    }

    @Operation(
        summary = "Add new author data", 
        description = "Add new author data, and get a response from it.", 
        tags = "Authors")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Add operation succesfull.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RequestResponse.class))),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden Error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @PostMapping(value = "/", consumes = { "application/json" })
    public ResponseEntity<Object> newAuthor(@RequestBody AuthorRequestDTO authorRequestDTO) {
        Author data = authorService.addNewAuthor(authorRequestDTO);
        return ResponseHandler.generateResponse(HttpStatus.CREATED, true,  "Successfully created a new data!", data);
    }

    @Operation(
        summary = "Renew the exist author data by specified ID",
        description = "Renew the exist author data by specified ID, and get response from it.", 
        tags = "Authors")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Renew operation successfull", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UpdateResponse.class))),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden Error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        @ApiResponse(responseCode = "404", description = "Data does not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataNotFound.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @PutMapping(value = "/{id}", consumes = { "application/json" })
    public ResponseEntity<Object> renewAuthor(@PathVariable String id, @RequestBody AuthorUpdateRequestDTO authorUpdateRequestDTO) {
        authorService.renewAuthor(id, authorUpdateRequestDTO);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successfully update data!");
    }

    @Operation(
        summary = "Remove all authors data.", 
        description = "Remove all authors data, and get response from it.", 
        tags = "Authors")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Remove operation successfull.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RemoveResponse.class))),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden Error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        @ApiResponse(responseCode = "404", description = "Data does not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataNotFound.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @DeleteMapping(value = "/")
    public ResponseEntity<Object> removeAuthors() {
        authorService.discardAuthors();
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successfully removed all data!");
    }

    @Operation(
        summary = "Remove the exist author data by specified ID", 
        description = "Remove the exist author data by specified ID, and get response from it.", 
        tags = "Authors")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Remove operation successfull.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RemoveResponse.class))),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden Error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        @ApiResponse(responseCode = "404", description = "Data does not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataNotFound.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> removeAuthor(@PathVariable String id) {
        authorService.discardAuthor(id);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successfully removed data!");
    }
}
