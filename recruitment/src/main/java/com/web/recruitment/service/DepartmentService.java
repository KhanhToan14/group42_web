package com.web.recruitment.service;

import com.web.recruitment.api.dto.department.DepartmentInsert;
import com.web.recruitment.api.dto.department.DepartmentUpdate;
import com.web.recruitment.persistence.dto.Department;
import java.util.*;
public interface DepartmentService {
    Map<String, Object> select(int id) throws Exception;

    Map<String, Object> listDepartment(int pageSize, int currentPage, String keyword, String sortBy, String sortType) throws Exception;

    Map<String, Object> insert(DepartmentInsert departmentInsert) throws Exception;

    Map<String, Object> update(DepartmentUpdate departmentUpdate) throws Exception;

    Map<String, Object> delete(int id) throws Exception;

    Map<String, Object> deleteChoice(List<Integer> id) throws Exception;

}
