package com.dinis.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BooksData(
        @JsonProperty("count") Integer count,
        @JsonProperty("next") String next,
        @JsonProperty("previous") String previous,
        @JsonProperty("results") List<BookData> results) {
}


