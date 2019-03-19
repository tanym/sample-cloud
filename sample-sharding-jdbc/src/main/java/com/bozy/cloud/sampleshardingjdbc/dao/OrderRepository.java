package com.bozy.cloud.sampleshardingjdbc.dao;

import com.bozy.cloud.sampleshardingjdbc.domain.Order;
import com.bozy.cloud.sampleshardingjdbc.domain.OrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Description: 订单接口
 * Created by tym on 2019/03/12 17:02.
 */
@Mapper
public interface OrderRepository {

    int insertOrder(Order order);

    int insertOrderItem(OrderItem orderItem);

    List<Order> searchOrder();

    List<Order> queryWithEqual();

    List<Order> queryWithIn();

    List<Order> queryWithBetween();

    List<Map<String, Object>> queryUser();

}
