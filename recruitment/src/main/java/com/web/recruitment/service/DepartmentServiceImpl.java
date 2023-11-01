package com.web.recruitment.service;

import com.web.recruitment.persistence.dto.Department;
import com.web.recruitment.persistence.mapper.DepartmentMapper;
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
    public Map<String, Object> select(long id) throws Exception{
        Map<String, Object> response = new HashMap<>();
        Department department = departmentMapper.select(id);
        response.put("department", department);
        return response;
    }
    /*@Override
    public Map<String, Object> listDepartment(int pageSize, int currentPage, String keyword, String sortBy, String sortType){
        return null;
    }*/
}
