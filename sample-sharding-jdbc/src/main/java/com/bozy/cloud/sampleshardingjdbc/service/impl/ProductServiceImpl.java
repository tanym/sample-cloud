package com.bozy.cloud.sampleshardingjdbc.service.impl;

import com.bozy.cloud.sampleshardingjdbc.dao.ProductRepository;
import com.bozy.cloud.sampleshardingjdbc.domain.Product;
import com.bozy.cloud.sampleshardingjdbc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description: 产品业务层接口
 * Created by tym on 2019/03/18 17:28.
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public int insertProduct(Product product) {
        return productRepository.insertProduct(product);
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteProduct(id);
    }

}
