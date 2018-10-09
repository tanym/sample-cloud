package com.bozy.cloud.service;

import com.bozy.cloud.domain.Country;

import java.util.List;

/**
 * Description: 国家字典业务层接口
 * Created by tym on 2018/09/03 17:06.
 */
public interface CountryService {

    void insert(Country country);

    void update(Country country);

    void deleteById(int id);

    List<Country> selectByCondition(Country country);

}
