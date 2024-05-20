package com.dinis.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Book (String title, List<Author> authors, List<String> bookshelves, List<String> languages,
                    @JsonProperty("download_count")Integer download){

}
