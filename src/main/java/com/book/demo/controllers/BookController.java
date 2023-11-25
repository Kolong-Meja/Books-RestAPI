package com.book.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.demo.dto.book.BookPatchAuthorIdDTO;
import com.book.demo.dto.book.BookPatchPublisherIdDTO;
import com.book.demo.dto.book.BookPatchUpdateDTO;
import com.book.demo.dto.book.BookRequestDTO;
import com.book.demo.dto.book.BookUpdateRequestDTO;
import com.book.demo.dto.errors.BadRequest;
import com.book.demo.dto.errors.DataNotFound;
import com.book.demo.dto.errors.ForbiddenError;
import com.book.demo.dto.errors.InternalServerError;
import com.book.demo.dto.handlers.ResponseHandler;
import com.book.demo.dto.thrives.GetResponse;
import com.book.demo.dto.thrives.RemoveResponse;
import com.book.demo.dto.thrives.RequestResponse;
import com.book.demo.dto.thrives.UpdateResponse;
import com.book.demo.models.Book;
import com.book.demo.services.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Books", description = "Endpoints for book operations.")
@RequestMapping("/api/v1/books")
@RestController
@SecurityRequirement(name = "bearer-key")
public class BookController {
    @Autowired
    private BookService bookService;
    
    @Operation(summary = "Get all books data", description = "Get all books data like title, author name, publisher name, etc.", tags = "Books")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get Operation successfull.", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GetResponse.class)))}),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden Error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        @ApiResponse(responseCode = "404", description = "Data does not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataNotFound.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @GetMapping(value = "/", produces = { "application/json" })
    public ResponseEntity<Object> getAllBooks() {
        List<Book> allExistingBookDatas = bookService.findAllBooks();
        if (allExistingBookDatas.isEmpty()) return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false, "Data does not found", allExistingBookDatas);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successfully retrieved data!", allExistingBookDatas);
    }

    @Operation(summary = "Get book data by specific ID", description = "Get one book data based on specific ID.", tags = "Books")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get Operation successfull", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetResponse.class))),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden Error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        @ApiResponse(responseCode = "404", description = "Data does not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataNotFound.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @GetMapping(value = "/{id}", produces = { "application/json" })
    public ResponseEntity<Object> getOneBookById(@PathVariable String id) {
        Book data = bookService.findBookById(id);
        if (data == null) return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false, "Data does not found", data);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successfully retrieved data!", data);
    }

    @Operation(summary = "Get all books by specific Title", description = "Get all books based on specific Title.", tags = "Books")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get Operation successfull.", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GetResponse.class)))}),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden Error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        @ApiResponse(responseCode = "404", description = "Data does not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataNotFound.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @GetMapping(value = "/title/{title}", produces = { "application/json" })
    public ResponseEntity<Object> getSeveralBooksByTitle(@PathVariable String title) {
        List<Book> data = bookService.findBookByTitle(title);
        if (data.isEmpty()) return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false, "Data does not found", data);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successfully retrieved data!", data);
    }

    @Operation(summary = "Get all books by specific Author ID", description = "Get all books based on specific Author ID.", tags = "Books")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get Operation successfull.", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GetResponse.class)))}),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden Error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        @ApiResponse(responseCode = "404", description = "Data does not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataNotFound.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @GetMapping(value = "/author/{authorId}", produces = { "application/json" })
    public ResponseEntity<Object> getSeveralBooksByAuthorId(@PathVariable String authorId) {
        List<Book> data = bookService.findBookByAuthorId(authorId);
        if (data.isEmpty()) return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false, "Data does not found", data);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successfully retrieved data!", data);
    }

    @Operation(summary = "Get all books by specific Category", description = "Get all books based on specific Category.", tags = "Books")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get Operation successfull.", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GetResponse.class)))}),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden Error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        @ApiResponse(responseCode = "404", description = "Data does not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataNotFound.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @GetMapping(value = "/category/{regex}", produces = { "application/json" })
    public ResponseEntity<Object> getSeveralBooksByCategory(@PathVariable String regex) {
        List<Book> data = bookService.findBookByCategory(regex);
        if (data.isEmpty()) return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false, "Data does not found", data);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successfully retrieved data!", data);
    }

    @Operation(
        summary = "Add new book data", 
        description = "Add new book data, and get a response from it.", 
        tags = "Books")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Add operation succesfull.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RequestResponse.class))),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden Error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @PostMapping(value = "/", consumes = { "application/json" })
    public ResponseEntity<Object> createNewBook(@RequestBody BookRequestDTO bookRequestDTO) {
       Book data = bookService.addNewBook(bookRequestDTO);
       return ResponseHandler.generateResponse(HttpStatus.CREATED, true, "Successfully created a new data!", data);
    }

    @Operation(
        summary = "Renew the exist book data by specific ID", 
        description = "Renew the exist book data by specified ID, and get response from it.", 
        tags = "Books")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Renew operation successfull", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UpdateResponse.class))),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden Error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        @ApiResponse(responseCode = "404", description = "Data does not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataNotFound.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @PutMapping(value = "/{id}", consumes = { "application/json" })
    public ResponseEntity<Object> updateAllBookData(@PathVariable String id, @RequestBody BookUpdateRequestDTO bookUpdateRequestDTO) {
        bookService.renewBook(id, bookUpdateRequestDTO);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successfully updated existing data!");
    }

    @Operation(
        summary = "Renew the exist book data by specific ID", 
        description = "Renew the exist book data by specified ID, and get response from it.", 
        tags = "Books")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Renew operation successfull", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UpdateResponse.class))),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden Error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        @ApiResponse(responseCode = "404", description = "Data does not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataNotFound.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @PatchMapping(value = "/{id}", consumes = { "application/json" })
    public ResponseEntity<Object> updateSeveralBookData(@PathVariable String id, @RequestBody BookPatchUpdateDTO bookPatchUpdateDTO) {
        bookService.renewBook(id, bookPatchUpdateDTO);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successfully updated existing data!");
    }

    @Operation(
        summary = "Renew the author of exist book data by specific ID", 
        description = "Renew the author exist book data by specified ID, and get response from it.", 
        tags = "Books")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Renew operation successfull", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UpdateResponse.class))),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden Error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        @ApiResponse(responseCode = "404", description = "Data does not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataNotFound.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @PatchMapping(value = "/{id}/author", consumes = { "application/json" })
    public ResponseEntity<Object> updateBookAuthorId(@PathVariable String id, @RequestBody BookPatchAuthorIdDTO bookPatchAuthorIdDTO ) {
        bookService.renewAuthor(id, bookPatchAuthorIdDTO);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successfully updated existing data!");
    }

    @Operation(
        summary = "Renew the publisher of exist book data by specific ID", 
        description = "Renew the publisher exist book data by specified ID, and get response from it.", 
        tags = "Books")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Renew operation successfull", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UpdateResponse.class))),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden Error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        @ApiResponse(responseCode = "404", description = "Data does not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataNotFound.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @PatchMapping(value = "/{id}/publisher", consumes = { "application/json" })
    public ResponseEntity<Object> updateBookPublisherId(@PathVariable String id, @RequestBody BookPatchPublisherIdDTO bookPatchPublisherIdDTO) {
        bookService.renewPublisher(id, bookPatchPublisherIdDTO);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successfully updated existing data!");
    }

    @Operation(
        summary = "Remove all exist book.", 
        description = "Remove all exist book data, and get response from it.", 
        tags = "Books")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Remove operation successfull.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RemoveResponse.class))),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden Error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        @ApiResponse(responseCode = "404", description = "Data does not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataNotFound.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @DeleteMapping(value = "/")
    public ResponseEntity<Object> removeAllBooks() {
        bookService.discardBooks();
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successfully removed all data");
    }

    @Operation(
        summary = "Remove the exist book data by specified ID", 
        description = "Remove the exist book data by specified ID, and get response from it.", 
        tags = "Books")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Remove operation successfull.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RemoveResponse.class))),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden Error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenError.class))),
        @ApiResponse(responseCode = "404", description = "Data does not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataNotFound.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> removeOneBook(@PathVariable String id) {
        bookService.discardBook(bookService.findBookById(id).takeCurrentId());
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successfully removed data");
    }   
}
