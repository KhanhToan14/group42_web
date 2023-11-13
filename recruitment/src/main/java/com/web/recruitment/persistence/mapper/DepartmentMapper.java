package com.web.recruitment.persistence.mapper;

import com.web.recruitment.api.dto.department.DepartmentInsert;
import com.web.recruitment.api.dto.department.DepartmentUpdate;
import com.web.recruitment.persistence.dto.Department;
import java.util.*;
public interface DepartmentMapper {
    List<Department> list(Map<String, Object> reqMap) throws Exception;

    Department select(int id) throws Exception;

    int total(Map<String, Object> reqMap) throws Exception;

    int totalByKeyword(Map<String, Object> reqMap) throws Exception;

    List<Department> listByName(Map<String, Object> reqMap) throws Exception;

    int insert(DepartmentInsert departmentInsert) throws Exception;

    int selectByName(Map<String, Object> reqMap) throws Exception;

    int update(DepartmentUpdate departmentUpdate) throws Exception;

    int delete(int id) throws Exception;

    int deleteChoice(List<Integer> id) throws Exception;

}
