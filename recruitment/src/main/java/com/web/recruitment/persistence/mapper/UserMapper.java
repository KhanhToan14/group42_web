package com.web.recruitment.persistence.mapper;


import com.web.recruitment.api.dto.user.UserInsert;
import com.web.recruitment.api.dto.user.UserUpdate;
import com.web.recruitment.persistence.dto.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    List<User> list(Map<String, Object> reqMap) throws Exception;

    User select(int id) throws Exception;

    int total(Map<String, Object> reqMap) throws Exception;

    int insert(UserInsert userInsert) throws Exception;

    int selectByName(Map<String, Object> reqMap) throws Exception;

    int update(UserUpdate userUpdate) throws Exception;

    int delete(int id) throws Exception;

    int deleteChoice(List<Integer> id) throws Exception;

    List<User> listRole(Map<String, Object> reqMap) throws Exception;

    int totalRole(Map<String, Object> reqMap) throws Exception;
}
