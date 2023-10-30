package com.web.recruitment.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BaseDto {
    @JsonProperty("page_size")
    int pageSize;
    @JsonProperty("current_page")
    int currentPage;
    @JsonProperty("sort_by")
    String sortBy;
    @JsonProperty("sort_type")
    String sortType;
    String keyword;
    @JsonIgnore
    int limit;
    @JsonIgnore
    int offset;
}
