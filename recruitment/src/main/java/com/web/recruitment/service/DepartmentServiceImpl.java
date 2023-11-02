package com.web.recruitment.service;

import com.web.recruitment.persistence.dto.Department;
import com.web.recruitment.persistence.mapper.DepartmentMapper;
import com.web.recruitment.utils.ConstantMessages;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

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
        response.put(ConstantMessages.DEPARTMENT, department);
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
        reqMap.put(ConstantMessages.LIMIT, limit);
        reqMap.put(ConstantMessages.OFFSET, offset);
        reqMap.put(ConstantMessages.SORT_BY, sortBy);
        reqMap.put(ConstantMessages.SORT_TYPE, sortType);
        if(keyword == null || keyword.trim().equals("")){
            retList = departmentMapper.list(reqMap);
            total = retList.size();
        }
        else {
            reqMap.put(ConstantMessages.KEYWORD, keyword);
            retList = departmentMapper.listByName(reqMap);
            total = departmentMapper.totalByKeyword(reqMap);
        }
        reqInfo.put(ConstantMessages.CURRENT_PAGE, currentPage);
        reqInfo.put(ConstantMessages.PAGE_SIZE, pageSize);
        reqInfo.put(ConstantMessages.DATA, retList);
        reqInfo.put(ConstantMessages.TOTAL, total);
        return reqInfo;
    }
}
