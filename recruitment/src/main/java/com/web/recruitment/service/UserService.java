package com.web.recruitment.service;


import com.web.recruitment.api.dto.user.UserInsert;
import com.web.recruitment.api.dto.user.UserUpdate;

import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String, Object> select(int id) throws Exception;

    Map<String, Object> listUser(Map<String, Object> filter) throws Exception;

    Map<String, Object> insert(UserInsert userInsert) throws Exception;

    Map<String, Object> update(UserUpdate userUpdate) throws Exception;

    Map<String, Object> delete(int id) throws Exception;

    Map<String, Object> deleteChoice(List<Integer> ids) throws Exception;

    Map<String, Object> listUserRole(Map<String, Object> filter) throws Exception;
}
