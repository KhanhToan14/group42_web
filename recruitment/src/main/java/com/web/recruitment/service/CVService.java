package com.web.recruitment.service;

import com.web.recruitment.persistence.dto.CV;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CVService {
    Map<String, Object> store (MultipartFile file) throws Exception;

    Map<String, Object> select (int id) throws Exception;
}
