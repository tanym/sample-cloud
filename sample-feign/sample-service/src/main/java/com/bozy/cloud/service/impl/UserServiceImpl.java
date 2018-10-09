package com.bozy.cloud.service.impl;

import com.bozy.cloud.annotation.TargetDataSource;
import com.bozy.cloud.dao.CountryMapper;
import com.bozy.cloud.dao.UserMapper;
import com.bozy.cloud.domain.Country;
import com.bozy.cloud.domain.User;
import com.bozy.cloud.enums.DataSourceKey;
import com.bozy.cloud.service.UserService;
import com.bozy.cloud.vo.PageBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * Description: 用户信息业务层接口
 * Created by tym on 2018/08/23 15:08.
 */
@Service
//@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    CountryMapper countryMapper;

    /**
     * Description: 数据源不加任何注解的情况下,默认为从库:DataSourceKey.DB_SLAVE;
     * @Author tym
     * @Create Date: 2018/9/7/0007 下午 5:00
     * @param user
     * @throws Exception
     */
//    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @TargetDataSource(dataSourceKey = DataSourceKey.DB_MASTER)
    @Override
    public void addUser(User user) throws Exception{
        userMapper.insert(user);
    }

//    @TargetDataSource("dataSourceMaster")
    @TargetDataSource(dataSourceKey = DataSourceKey.DB_MASTER)
    @Override
    public List<User> getUserList() throws Exception{
        return userMapper.getAll(null);
    }

//    @Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.NOT_SUPPORTED)
    @TargetDataSource(dataSourceKey = DataSourceKey.DB_MASTER)
    @Override
    public void saveUserAndCountry(User user, Country country) throws Exception{
        userMapper.insert(user);
        System.out.println(1/0);
        countryMapper.insert(country);
//        System.out.println(1/0);
//        System.out.println(1/0);
    }

    /**
     * Description: 分页查询
     * @Author tym
     * @Create Date: 2018/9/17/0017 下午 3:13
     * @param conditions
     * @return
     */
    @TargetDataSource(dataSourceKey = DataSourceKey.DB_MASTER)
    @Override
    public PageInfo<User> findUsersByPage(Map<String, Object> conditions) {
        int currentPage = 1;
        int pageSize = 10;
        if(!CollectionUtils.isEmpty(conditions)){
            if(null != conditions.get("currentPage")){
               currentPage = Integer.parseInt(conditions.get("currentPage").toString());
            }
            if(null != conditions.get("pageSize")){
               pageSize = Integer.parseInt(conditions.get("pageSize").toString());
            }
        }
        //设置分页信息,分别是当前页数和每页显示的总记录数【记住:必须在mapper接口中的方法执行之前设置该分页信息】
        Page<User> pageInfo = PageHelper.startPage(currentPage, pageSize);
        userMapper.getAll(conditions);

        /*PageBean<User> pageBean = new PageBean<>();
        pageBean.setCurrentPage(pageInfo.getPageNum());
        pageBean.setPageSize(pageInfo.getPageSize());
        pageBean.setItems(pageInfo.getResult());
        pageBean.setTotalNum(pageInfo.getTotal());*/

        System.out.println("====分页结果全数据:" + pageInfo.toPageInfo());
        return pageInfo.toPageInfo();
    }

}
