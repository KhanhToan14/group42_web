package com.web.recruitment.service;

import com.web.recruitment.api.dto.department.DepartmentInsert;
import com.web.recruitment.exception.ValidationException;
import com.web.recruitment.persistence.dto.Department;
import com.web.recruitment.persistence.mapper.DepartmentMapper;
import com.web.recruitment.utils.ConstantMessages;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.web.recruitment.utils.ConstantMessages.*;
import static com.web.recruitment.utils.ValidationUtils.*;

@Slf4j
@Service
public class DepartmentServiceImpl implements DepartmentService{
    @Resource
    private final DepartmentMapper departmentMapper;

    public DepartmentServiceImpl(DepartmentMapper departmentMapper) {
        this.departmentMapper = departmentMapper;
    }

    @Override
    public Map<String, Object> select(int id) throws Exception{
        Map<String, Object> response = new HashMap<>();
        Department department = departmentMapper.select(id);
        response.put(DEPARTMENT, department);
        return response;
    }
    @Override
    public Map<String, Object> listDepartment(int pageSize, int currentPage, String keyword, String sortBy, String sortType) throws Exception {
        List<Department> retList = null;
        Map<String, Object> reqInfo = new HashMap<>();
        Map<String, Object> reqMap = new HashMap<>();
        int total;
        int limit;
        int offset;
        if (sortBy == null || sortBy.isBlank()) {
            sortBy = "createAt";
        } else {
            sortBy = sortBy.trim();
            if (!sortBy.equals("name") && !sortBy.equals("createAt")) {
                sortBy = "createAt";
            }
        }
        if (sortType == null || sortType.isBlank()) {
            if (sortBy.equals("createAt")) {
                sortType = "desc";
            } else {
                sortType = "asc";
            }
        } else {
            sortType = sortType.trim();
            if (!sortType.equals("asc") && !sortType.equals("desc")) {
                sortType = "asc";
            }
        }

        if (currentPage <= 0) {
            currentPage = 1;
        }
        if (pageSize <= 0) {
            pageSize = 30;
        }
        limit = pageSize;
        offset = pageSize * (currentPage - 1);
        if (offset < 0) {
            return null;
        }
        reqMap.put(LIMIT, limit);
        reqMap.put(OFFSET, offset);
        reqMap.put(SORT_BY, sortBy);
        reqMap.put(SORT_TYPE, sortType);
        if(keyword == null || keyword.trim().equals("")){
            retList = departmentMapper.list(reqMap);
            total = retList.size();
        }
        else {
            reqMap.put(KEYWORD, keyword);
            retList = departmentMapper.listByName(reqMap);
            total = departmentMapper.totalByKeyword(reqMap);
        }
        reqInfo.put(CURRENT_PAGE, currentPage);
        reqInfo.put(PAGE_SIZE, pageSize);
        reqInfo.put(DATA, retList);
        reqInfo.put(TOTAL, total);
        return reqInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> insert(DepartmentInsert departmentInsert) throws Exception{
        Map<String, Object> response = new HashMap<>();
        departmentInsert.setName(autoCorrectFormatName(departmentInsert.getName()));
        String name = departmentInsert.getName();
        if(name == null || name.isBlank()){
            throw new ValidationException(NAME, NAME_NOT_NULL_ERROR);
        } else {
            name = name.trim();
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put(NAME, name);
            if(departmentMapper.selectByName(reqMap) != 0){
                throw new ValidationException(NAME, NAME_EXIST);
            } else {
                departmentInsert.setName(name);
            }
        }
        String description = departmentInsert.getDescription();
        if(description != null && !description.trim().equals("")){
            departmentInsert.setDescription(autoCorrectFormatName(description));
        }else {
            departmentInsert.setDescription(null);
        }
        int isSuccess = departmentMapper.insert(departmentInsert);
        if (isSuccess == 1){
            response.put(MESSAGE, SUCCESS_INSERT_DEPARTMENT);
        }
        return response;
    }
}
