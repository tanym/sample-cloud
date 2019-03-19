package com.bozy.cloud.sampleshardingjdbc.service.impl;

import com.bozy.cloud.sampleshardingjdbc.common.OutPutObject;
import com.bozy.cloud.sampleshardingjdbc.dao.OrderRepository;
import com.bozy.cloud.sampleshardingjdbc.domain.Order;
import com.bozy.cloud.sampleshardingjdbc.domain.OrderItem;
import com.bozy.cloud.sampleshardingjdbc.service.OrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 * Created by tym on 2019/03/15 17:16.
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = Logger.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public OutPutObject insertOrder(Order order, OrderItem orderItem) {
        try{
            int count = orderRepository.insertOrder(order);
            System.out.println(count);
            long orderId = order.getOrder_id();
            System.out.println("=========" + order.getOrder_id() +"========");
            orderItem.setOrder_id(orderId);
            orderRepository.insertOrderItem(orderItem);
            return new OutPutObject(true, "新增订单成功");
        }catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException("新增订单失败");
        }
    }

}
