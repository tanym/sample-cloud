package com.bozy.cloud.service;

import com.bozy.cloud.domain.Country;
import com.bozy.cloud.domain.User;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by tym on 2018/08/23 15:07.
 */
public interface UserService {

    void addUser(User user) throws Exception;

    public List<User> getUserList() throws Exception;

    void saveUserAndCountry(User user, Country country) throws Exception;

    PageInfo<User> findUsersByPage(Map<String, Object> conditions);

}
