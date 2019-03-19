package com.bozy.cloud.sampleshardingjdbc.service;

import com.bozy.cloud.sampleshardingjdbc.domain.Product;

/**
 * Description:
 * Created by tym on 2019/03/18 17:27.
 */
public interface ProductService {

    int insertProduct(Product product);

    void deleteProduct(int id);

}
