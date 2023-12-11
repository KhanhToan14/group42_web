package com.web.recruitment.api;

import com.web.recruitment.persistence.dto.CV;
import com.web.recruitment.persistence.mapper.CVMapper;
import com.web.recruitment.service.CVService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static com.web.recruitment.utils.ConstantMessages.*;

@Tag(name = "CV API", description = "API for functions related to CV")
@RestController
@Slf4j
@RequestMapping(value = "/v1/cv")
public class CVController {
    @Autowired
    private final CVService cvService;
    @Autowired
    private final CVMapper cvMapper;

    @Autowired
    public CVController(CVService cvService, CVMapper cvMapper) {
        this.cvService = cvService;
        this.cvMapper = cvMapper;
    }
    @Operation(summary = "Store CV API", description = "Store CV")
    @PostMapping(path = "/store", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> storeCV(
            @RequestParam("file") MultipartFile file
    ) throws Exception{
        JSONObject res;
        Map<String, Object> resError;
        resError = cvService.store(file);
        res = new JSONObject(resError);
        if (resError.get(MESSAGE).equals(SUCCESS_STORE_FILE)){
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(res, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    @Operation(summary = "Get CV API", description = "Get CV")
    @GetMapping(path = "/select/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> selectCV(
            @PathVariable("id") int id
    ) throws Exception{
        Map<String, Object> response;
        JSONObject res;
        response = cvService.select(id);
        res = new JSONObject(response);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Download CV API", description = "Download CV")
    @GetMapping(path = "/download/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> downloadCV(
            @PathVariable("id") int id
    ) throws Exception{
        CV cv = cvMapper.select(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(cv.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + cv.getName() + "\"")
                .body(new ByteArrayResource(cv.getData()));
    }
}
