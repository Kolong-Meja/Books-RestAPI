package com.book.demo.dto.book;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.lang.Nullable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BookPatchUpdateDTO(
    @NotNull
    @JsonProperty("isbn")
    @Schema(type = "integer", description = "ISBN number of the book.", nullable = false)
    long isbn,

    @NotNull
    @Size(min = 0, max = 512, message = "Maximum size limit character of this field is 512")
    @JsonProperty("title")
    @Schema(type = "string", description = "Title of the book.", nullable = false)
    String title,

    @NotNull
    @JsonProperty("synopsis")
    @Schema(type = "string", description = "Synopsis of the book.", nullable = false)
    String synopsis,

    @NotNull
    @JsonProperty("publishYear")
    @Schema(type = "integer", example = "2023", description = "Year that the book published.", nullable = false)
    int publishYear,

    @NotNull
    @JsonProperty("totalPage")
    @Schema(type = "integer", example = "255", description = "Total page of the book.", nullable = false)
    int totalPage,

    @NotNull
    @Size(min = 0, max = 255, message = "Maximum size limit character of this field is 255")
    @JsonProperty("category")
    @Schema(type = "string", example = "Adventure Horror", description = "Categories of the book.", nullable = false)
    String category,

    @Nullable
    @JsonProperty("createdOn")
    @Schema(type = "string", format = "date-time", example = "2017-07-21T17:32:28Z", description = "Create date of the book data")
    LocalDateTime createdOn
) {}
