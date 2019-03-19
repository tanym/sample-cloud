package com.bozy.cloud.sampleshardingjdbc.dao;

import com.bozy.cloud.sampleshardingjdbc.domain.Product;
import org.apache.ibatis.annotations.Mapper;

/**
 * Description: 商品数据层接口
 * Created by tym on 2019/03/18 17:22.
 */
@Mapper
public interface ProductRepository {

    int insertProduct(Product product);

    void deleteProduct(int id);

}
