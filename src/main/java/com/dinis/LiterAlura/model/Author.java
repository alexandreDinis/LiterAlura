package com.dinis.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Author ( @JsonProperty("birth_year")Integer birthYear,
                       @JsonProperty("death_year") Integer deathYear,
                      String name) {
}
