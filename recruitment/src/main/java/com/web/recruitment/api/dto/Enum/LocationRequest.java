package com.web.recruitment.api.dto.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LocationRequest {
    private CityEnum city;
    private DistrictEnum district;
    private CommuneEnum commune;
}
