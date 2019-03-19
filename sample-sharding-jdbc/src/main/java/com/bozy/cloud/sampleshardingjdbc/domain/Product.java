package com.bozy.cloud.sampleshardingjdbc.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Description: 产品pojo
 * Created by tym on 2019/03/18 17:04.
 */
@Data
public class Product implements java.io.Serializable{

    private static final long serialVersionUID = 2027520205834865847L;

    private Integer id;
    private String name;
    private BigDecimal price;
    private String productTags;
    private Boolean isHot;
    private Boolean isNew;
    private String description;
    private Date createDate;
    private Date modifyDate;

}
