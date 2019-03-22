package com.bozy.cloud.sampleshardingjdbc.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Description: 订单明细POJO
 * Created by tym on 2019/03/12 17:00.
 */
@Data
public class OrderItem {

    private Long order_item_id;
    private Long order_id;
    private Integer product_id;
    private String product_name;
    private BigDecimal product_price;
    private Integer product_quantity;
    private String product_sn;
//    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;
//    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date modify_time;
    private Long user_id;

}
