package com.dinis.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookData(String title,
                       List<AuthorData> authors,
                       List<String> languages,
                       @JsonProperty("download_count")Integer download){

}
