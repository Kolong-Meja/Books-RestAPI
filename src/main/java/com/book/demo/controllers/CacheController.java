package com.book.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.book.demo.dto.errors.BadRequest;
import com.book.demo.dto.errors.DataNotFound;
import com.book.demo.dto.errors.InternalServerError;
import com.book.demo.dto.handlers.ResponseHandler;
import com.book.demo.dto.thrives.RemoveResponse;
import com.book.demo.services.CacheService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Caches", description = "Endpoints for cache endpoints.")
@RestController
@RequestMapping("/api/v1/caches")
@Deprecated
public class CacheController {
    @Autowired
    private CacheService cacheService;

    @Operation(
        summary = "Clear cache of an endpoint", 
        description = "Clear cache of an endpoint, and get response from it.", 
        tags = "Caches"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Remove operation successfull.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RemoveResponse.class))),
        @ApiResponse(responseCode = "400", description = "Data request cannot be processing.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "404", description = "Data does not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataNotFound.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerError.class)))
    })
    @DeleteMapping(value = "/clear-cache")
    public ResponseEntity<Object> clearAuthorsCache(@RequestParam String cacheName) {
        cacheService.clearCache(cacheName);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successfully cleared cache!");
    }
}
