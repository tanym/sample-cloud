package com.bozy.cloud.sampleshardingjdbc.service;

import com.bozy.cloud.sampleshardingjdbc.common.OutPutObject;
import com.bozy.cloud.sampleshardingjdbc.domain.Order;
import com.bozy.cloud.sampleshardingjdbc.domain.OrderItem;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by tym on 2019/03/15 17:12.
 */
public interface OrderService {

    public OutPutObject insertOrderByHint(Order order, OrderItem orderItem);

    public OutPutObject insertOrder(Order order, OrderItem orderItem);

    public List<Order> selectPageByUserId(Map<String, Object> paramsMap);

    public List<Order> selectPageByHelper(Map<String, Object> paramsMap);

}
