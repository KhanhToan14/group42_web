package com.web.recruitment.service;

import com.web.recruitment.persistence.dto.Department;
import java.util.*;
public interface DepartmentService {
    Map<String, Object> select(long id) throws Exception;

    /*Map<String, Object> listDepartment(int pageSize, int currentPage, String keyword, String sortBy, String sortType);*/
}
