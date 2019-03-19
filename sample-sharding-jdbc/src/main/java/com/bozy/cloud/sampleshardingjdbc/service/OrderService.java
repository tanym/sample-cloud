package com.bozy.cloud.sampleshardingjdbc.service;

import com.bozy.cloud.sampleshardingjdbc.common.OutPutObject;
import com.bozy.cloud.sampleshardingjdbc.domain.Order;
import com.bozy.cloud.sampleshardingjdbc.domain.OrderItem;

/**
 * Description:
 * Created by tym on 2019/03/15 17:12.
 */
public interface OrderService {

    public OutPutObject insertOrder(Order order, OrderItem orderItem);

}
