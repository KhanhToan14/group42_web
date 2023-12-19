/*
package com.web.recruitment.service;

import com.web.recruitment.api.dto.department.DepartmentInsert;
import com.web.recruitment.api.dto.department.DepartmentUpdate;
import com.web.recruitment.persistence.dto.Department;
import com.web.recruitment.persistence.mapper.CompanyMapper;
import com.web.recruitment.persistence.mapper.DepartmentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.web.recruitment.utils.ConstantMessages.*;
import static com.web.recruitment.utils.ValidationUtils.*;

@Slf4j
@Service
public class DepartmentServiceImpl implements DepartmentService{
    @Autowired
    private final DepartmentMapper departmentMapper;

    @Autowired
    private final CompanyMapper companyMapper;

    public DepartmentServiceImpl(DepartmentMapper departmentMapper, CompanyMapper companyMapper) {
        this.departmentMapper = departmentMapper;
        this.companyMapper = companyMapper;
    }

    @Override
    public Map<String, Object> select(int id) throws Exception{
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> subResError = new HashMap<>();
        Map<String, Object> resError = new HashMap<>();
        Department department = departmentMapper.select(id);
        if(department == null) {
            subResError.put(ID, ID_NOT_EXIST_ERROR);
            resError.put(MESSAGE, NOT_FOUND_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        } else {
            response.put(DEPARTMENT, department);
            return response;
        }
    }
    @Override
    public Map<String, Object> listDepartment(Map<String, Object> filter) throws Exception {
        List<Department> retList;
        Map<String, Object> reqInfo = new HashMap<>();
        int pageSize = (int) filter.get(PAGE_SIZE);
        int currentPage = (int) filter.get(CURRENT_PAGE);
        if (currentPage <= 0) {
            currentPage = 1;
        }
        if (pageSize <= 0) {
            pageSize = 30;
        }
        int limit = pageSize;
        int offset = pageSize * (currentPage - 1);
        filter.put(LIMIT, limit);
        filter.put(OFFSET, offset);
        String sortBy = (String) filter.get(SORT_BY);
        String sortType = (String) filter.get(SORT_TYPE);
        if (sortBy == null || sortBy.isBlank()) {
            sortBy = "time";
        } else {
            sortBy = sortBy.trim();
            if (!sortBy.equals("name") && !sortBy.equals("time")) {
                sortBy = "time";
            }
        }
        if (sortType == null || sortType.isBlank()) {
            if (sortBy.equals("time")) {
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
        filter.replace(SORT_BY, sortBy);
        filter.replace(SORT_TYPE, sortType);
        retList = departmentMapper.list(filter);
        int total = retList.size();
        reqInfo.put(CURRENT_PAGE, currentPage);
        reqInfo.put(PAGE_SIZE, pageSize);
        reqInfo.put(DATA, retList);
        reqInfo.put(TOTAL, total);
        return reqInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> insert(DepartmentInsert departmentInsert) throws Exception{
        Map<String, Object> subResError = new HashMap<>();
        Map<String, Object> resError = new HashMap<>();
        int companyId = departmentInsert.getCompanyId();
        if(companyMapper.select(companyId) == null){
            subResError.put(COMPANY_ID, COMPANY_ID_NOT_EXIST);
            resError.put(MESSAGE, NOT_FOUND_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
        departmentInsert.setName(autoCorrectFormatName(departmentInsert.getName()));
        String name = departmentInsert.getName();
        if(name == null || name.isBlank()){
            subResError.put(NAME, NAME_MUST_NOT_NULL);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        } else {
            name = name.trim();
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put(NAME, name);
            if(departmentMapper.selectByName(reqMap) != 0){
                subResError.put(NAME, NAME_EXIST);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
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
        departmentMapper.insert(departmentInsert);
        resError.put(MESSAGE, SUCCESS_INSERT_DEPARTMENT);
        return resError;
    }

    @Override
    public Map<String, Object> update(DepartmentUpdate departmentUpdate) throws Exception{
        Map<String, Object> subResError = new HashMap<>();
        Map<String, Object> resError = new HashMap<>();
        if(departmentUpdate.getId() == null){
            subResError.put(ID, ID_MUST_NOT_NULL);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
        if(departmentMapper.select(departmentUpdate.getId()) == null){
            subResError.put(ID, ID_NOT_EXIST_ERROR);
            resError.put(MESSAGE, NOT_FOUND_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
        int companyId = departmentUpdate.getCompanyId();
        if(companyMapper.select(companyId) == null){
            subResError.put(COMPANY_ID, COMPANY_ID_NOT_EXIST);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
        departmentUpdate.setName(autoCorrectFormatName(departmentUpdate.getName()));
        String name = departmentUpdate.getName();
        if(name == null || name.isBlank()){
            subResError.put(NAME, NAME_MUST_NOT_NULL);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        } else {
            name = name.trim();
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put(NAME, name);
            if(departmentMapper.selectByName(reqMap) != 0){
                subResError.put(NAME, NAME_EXIST);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            } else {
                departmentUpdate.setName(name);
            }
        }
        String description = departmentUpdate.getDescription();
        if(description != null && !description.trim().equals("")){
            departmentUpdate.setDescription(autoCorrectFormatName(description));
        }else {
            departmentUpdate.setDescription(null);
        }
        departmentMapper.update(departmentUpdate);
        resError.put(MESSAGE, SUCCESS_UPDATE_DEPARTMENT);
        return resError;
    }

    @Override
    public Map<String, Object> delete (int id) throws Exception{
        Map<String, Object> subResError = new HashMap<>();
        Map<String, Object> resError = new HashMap<>();
        if(departmentMapper.select(id) == null){
            subResError.put(ID, ID_NOT_EXIST_ERROR);
            resError.put(MESSAGE, NOT_FOUND_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
        departmentMapper.delete(id);
        resError.put(MESSAGE, SUCCESS_DELETE_DEPARTMENT);
        return resError;
    }
    @Override
    public Map<String, Object> deleteChoice(List<Integer> ids) throws Exception{
        Map<String, Object> subResError = new HashMap<>();
        Map<String, Object> resError = new HashMap<>();
        ArrayList<Integer> idsDeleteSuccess = new ArrayList<>();
        ArrayList<Integer> idsDeleteNotSuccess = new ArrayList<>();
        int validId;
        int idsSize = ids.size();
        if(idsSize > 0){
            for(Integer id : ids){
                if(departmentMapper.select(id) == null){
                    idsDeleteNotSuccess.add(id);
                } else {
                    validId = id;
                    idsDeleteSuccess.add(validId);
                    departmentMapper.delete(id);
                }
            }
        } else {
            subResError.put(DELETE_IDS, DELETE_IDS_MUST_NOT_NULL_OR_EMPTY);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
        if(idsDeleteSuccess.size() > 0){
            if (idsDeleteNotSuccess.size() > 0){
                resError.put(LIST_ID_CAN_NOT_DELETE, idsDeleteNotSuccess);
                resError.put(LIST_ID_DELETED, idsDeleteSuccess);
                resError.put(MESSAGE, SUCCESS_DELETE_DEPARTMENT);
            } else{
                resError.put(MESSAGE, SUCCESS_DELETE_DEPARTMENT);
            }
        } else {
            subResError.put(DELETE_IDS, IDS_INVALID);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
        }
        return resError;
    }
}
*/
