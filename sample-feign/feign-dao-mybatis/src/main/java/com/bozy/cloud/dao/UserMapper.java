package com.bozy.cloud.dao;

import com.bozy.cloud.domain.User;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by tym on 2018/08/23 14:55.
 */
public interface UserMapper {
    List<User> getAll(Map<String, Object> condition);

    User getOne(Long id);

    void insert(User user);

    void update(User user);

    void delete(Long id);
}
