package com.bozy.cloud.dao;

import com.bozy.cloud.domain.Country;

import java.util.List;

/**
 * Description:
 * Created by tym on 2018/09/03 17:04.
 */
public interface CountryMapper {

    void insert(Country country);
    void update(Country country);
    void delete(int id);
    List<Country> selectByCondition(Country country);

}
