package com.web.recruitment.persistence.mapper;

import com.web.recruitment.persistence.dto.Department;
import java.util.*;
public interface DepartmentMapper {
   /* List<Department> list(Map<String, Object> reqMap) throws Exception;*/
    Department select(int id) throws Exception;
}
