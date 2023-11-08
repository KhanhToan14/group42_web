package com.web.recruitment.persistence.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    private int id;
    private String name;
    private String description;
    private int delYn;
    private String createAt;
    private String updateAt;
}
