package com.web.recruitment.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DeleteRequest {
    List<Integer> deleteIds;
}
