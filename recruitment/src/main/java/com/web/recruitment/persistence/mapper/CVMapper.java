package com.web.recruitment.persistence.mapper;

import com.web.recruitment.persistence.dto.CV;

public interface CVMapper {
    int store (CV cv) throws Exception;

    CV select (int id) throws Exception;
}
