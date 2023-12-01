package com.web.recruitment.service;

import com.web.recruitment.persistence.dto.CV;
import com.web.recruitment.persistence.dto.Department;
import com.web.recruitment.persistence.mapper.CVMapper;
import com.web.recruitment.utils.FileUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

import static com.web.recruitment.utils.ConstantMessages.*;
import static com.web.recruitment.utils.ConstantMessages.DEPARTMENT;

@Slf4j
@Service
public class CVServiceImpl implements CVService{

    @Resource
    private final CVMapper cvMapper;

    public CVServiceImpl(CVMapper cvMapper) {
        this.cvMapper = cvMapper;
    }

    @Override
    public Map<String, Object> store (MultipartFile file) throws Exception{
        Map<String, Object> subResError = new HashMap<>();
        Map<String, Object> resError = new HashMap<>();
        if(file.getOriginalFilename() == null || file.getOriginalFilename().isEmpty()){
            subResError.put(FILE_NAME, FILE_NAME_NOT_NULL);
            resError.put(MESSAGE, FILE_INVALID);
            resError.put(ERRORS, subResError);
            return resError;
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = FileUtils.getFileExtension(fileName);
        if(!FileUtils.checkFileExtension(fileExtension)){
            subResError.put(FILE_EXTENSION, FILE_INCORRECT_FORMAT);
            resError.put(MESSAGE, FILE_INVALID);
            resError.put(ERRORS, subResError);
            return resError;
        }
        double fileSize =  (double) (file.getSize() / 1_048_576);
        if(fileSize > 10){
            subResError.put(FILE_SIZE, FILE_INCORRECT_FORMAT);
            resError.put(MESSAGE, FILE_INVALID);
            resError.put(ERRORS, subResError);
            return resError;
        }
        CV cv = new CV();
        cv.setName(fileName);
        cv.setType(file.getContentType());
        cv.setData(file.getBytes());
        cvMapper.store(cv);
        resError.put(ID, cv.getId());
        resError.put(MESSAGE, SUCCESS_STORE_FILE);
        return resError;
    }

    @Override
    public Map<String, Object> select(int id) throws Exception{
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> subResError = new HashMap<>();
        Map<String, Object> resError = new HashMap<>();
        CV cv = cvMapper.select(id);
        if(cv == null) {
            subResError.put(ID, ID_NOT_EXIST_ERROR);
            resError.put(MESSAGE, NOT_FOUND_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        } else {
            response.put(CV, cv);
            return response;
        }
    }
}
