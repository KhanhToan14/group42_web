package com.web.recruitment.service;

import com.web.recruitment.api.dto.user.UserInsert;
import com.web.recruitment.api.dto.user.UserUpdate;
import com.web.recruitment.persistence.dto.Department;
import com.web.recruitment.persistence.dto.User;
import com.web.recruitment.persistence.mapper.UserMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.web.recruitment.utils.ConstantMessages.*;
import static com.web.recruitment.utils.ConstantMessages.DEPARTMENT;

@Slf4j
@Service
public class UserServiceImpl implements UserService{
    @Resource
    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Map<String, Object> select(int id) throws Exception{
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> subResError = new HashMap<>();
        Map<String, Object> resError = new HashMap<>();
        User user = userMapper.select(id);
        if(user == null) {
            subResError.put(ID, ID_NOT_EXIST_ERROR);
            resError.put(MESSAGE, NOT_FOUND_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        } else {
            response.put(USER, user);
            return response;
        }
    }

    @Override
    public Map<String, Object> listUser(Map<String, Object> filter) throws Exception{
        List<User> retList;
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
            sortBy = "username";
        } else {
            sortBy = sortBy.trim();
            if (!sortBy.equals("username") && !sortBy.equals("firstName") && !sortBy.equals("lastName") && !sortBy.equals("email")) {
                sortBy = "username";
            }
        }
        if (sortType == null || sortType.isBlank()) {
            sortType = "desc";
        } else {
            sortType = sortType.trim();
            if (!sortType.equals("asc") && !sortType.equals("desc")) {
                sortType = "asc";
            }
        }
        filter.replace(SORT_BY, sortBy);
        filter.replace(SORT_TYPE, sortType);
        retList = userMapper.list(filter);
        int total = retList.size();
        reqInfo.put(CURRENT_PAGE, currentPage);
        reqInfo.put(PAGE_SIZE, pageSize);
        reqInfo.put(DATA, retList);
        reqInfo.put(TOTAL, total);
        return reqInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> insert(UserInsert userInsert) throws Exception{
        return null;
    }

    @Override
    public Map<String, Object> update(UserUpdate userUpdate) throws Exception{
        return null;
    }

    @Override
    public Map<String, Object> delete(int id) throws Exception{
        return null;
    }

    @Override
    public Map<String, Object> deleteChoice(List<Integer> ids) throws Exception{
        return null;
    }

    @Override
    public Map<String, Object> listUserRole(Map<String, Object> filter) throws Exception{
        List<User> retList;
        Map<String, Object> reqInfo = new HashMap<>();
        int pageSize = (int) filter.get(PAGE_SIZE);
        int currentPage = (int) filter.get(CURRENT_PAGE);
        if (currentPage <= 0) {
            currentPage = 1;
        }
        if (pageSize <= 0) {
            pageSize = 30;
        }
        int total = userMapper.totalRole(filter);
        int limit = pageSize;
        int offset = pageSize * (currentPage - 1);
        filter.put(LIMIT, limit);
        filter.put(OFFSET, offset);
        String sortBy = (String) filter.get(SORT_BY);
        String sortType = (String) filter.get(SORT_TYPE);

        if (sortType == null || sortType.isBlank()) {
            sortType = "desc";
        } else {
            sortType = sortType.trim();
            if (!sortType.equals("asc") && !sortType.equals("desc")) {
                sortType = "asc";
            }
        }
        filter.replace(SORT_BY, sortBy);
        filter.replace(SORT_TYPE, sortType);
        retList = userMapper.listRole(filter);
        reqInfo.put(CURRENT_PAGE, currentPage);
        reqInfo.put(PAGE_SIZE, pageSize);
        reqInfo.put(DATA, retList);
        reqInfo.put(TOTAL, total);
        return reqInfo;
    }
}
