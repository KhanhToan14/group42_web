package com.web.recruitment.persistence.mapper;

import com.web.recruitment.api.dto.user.UserInsert;
import com.web.recruitment.api.dto.user.UserUpdate;
import com.web.recruitment.persistence.dto.User;
//import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserMapper {
    List<User> list(Map<String, Object> reqMap) throws Exception;

    User select(int id) throws Exception;

    int total(Map<String, Object> reqMap) throws Exception;

    int insert(User user) throws Exception;

    int selectCountByUsername(Map<String, Object> reqMap) throws Exception;

    int update(UserUpdate userUpdate) throws Exception;

    int delete(int id) throws Exception;

    int deleteChoice(List<Integer> id) throws Exception;

    List<User> listRole(Map<String, Object> reqMap) throws Exception;

    int totalRole(Map<String, Object> reqMap) throws Exception;

    int selectCountUserByEmail(Map<String, Object> reqMap) throws Exception;

    /*Optional<User> findByUsername(String username) ;*/

    User selectInactivateByEmail(String email);

    User selectByEmail(String email);

    User selectByUsername(String username);

//    int lockUser(Map<String, Object> req);

//    int increaseOTPFailedCount(int id);

//    int increaseLoginFailedCount(int id);

    int activate(int id);

    int register(User user) throws Exception;

}
