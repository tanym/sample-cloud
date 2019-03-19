package com.bozy.cloud.sampleshardingjdbc.controller;

import com.bozy.cloud.sampleshardingjdbc.domain.Product;
import com.bozy.cloud.sampleshardingjdbc.service.ProductService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Description:
 * Created by tym on 2019/03/18 17:31.
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    private static final Logger logger = Logger.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public void addProduct(){
        try{
            Product product = new Product();
            product.setName("干炒牛河");
            product.setPrice(new BigDecimal(30));
            product.setDescription("粤菜");
            product.setIsHot(true);
            product.setIsNew(true);
            product.setProductTags("粤菜");
            product.setCreateDate(new Date());
            product.setModifyDate(new Date());
            productService.insertProduct(product);
            logger.info("======新增商品ID:" + product.getId() + "," + product.getName());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
