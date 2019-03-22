package com.bozy.cloud.sampleshardingjdbc.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Description: 订单POJO
 * Created by tym on 2019/03/12 16:56.
 */
@Data
public class Order {

    private Long order_id;
    private Long user_id;
    private Integer order_status;
    private String order_title;
    private Integer pay_status;
    private BigDecimal totalAmount;
//    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;
//    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date modify_time;

    private List<OrderItem> orderItems;

}
