package com.bozy.cloud.service.impl;

import com.bozy.cloud.annotation.TargetDataSource;
import com.bozy.cloud.dao.CountryMapper;
import com.bozy.cloud.domain.Country;
import com.bozy.cloud.enums.DataSourceKey;
import com.bozy.cloud.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description: 国家字典业务层接口实现类
 * Created by tym on 2018/09/03 17:09.
 */
@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    CountryMapper countryMapper;

    @TargetDataSource(dataSourceKey = DataSourceKey.DB_SLAVE)
    @Override
    public void insert(Country country) {
        countryMapper.insert(country);
    }

    @TargetDataSource(dataSourceKey = DataSourceKey.DB_SLAVE)
    @Override
    public void update(Country country) {
        countryMapper.update(country);
    }

    @TargetDataSource(dataSourceKey = DataSourceKey.DB_SLAVE)
    @Override
    public void deleteById(int id) {
        countryMapper.delete(id);
    }

    @TargetDataSource(dataSourceKey = DataSourceKey.DB_SLAVE)
    @Override
    public List<Country> selectByCondition(Country country) {
        return countryMapper.selectByCondition(country);
    }

}
