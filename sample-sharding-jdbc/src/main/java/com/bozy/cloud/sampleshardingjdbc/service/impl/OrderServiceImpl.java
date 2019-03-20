package com.bozy.cloud.sampleshardingjdbc.service.impl;

import com.bozy.cloud.sampleshardingjdbc.common.OutPutObject;
import com.bozy.cloud.sampleshardingjdbc.dao.OrderRepository;
import com.bozy.cloud.sampleshardingjdbc.domain.Order;
import com.bozy.cloud.sampleshardingjdbc.domain.OrderItem;
import com.bozy.cloud.sampleshardingjdbc.service.OrderService;
import io.shardingsphere.api.HintManager;
import io.shardingsphere.core.keygen.DefaultKeyGenerator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.hash;

/**
 * Description:
 * Created by tym on 2019/03/15 17:16.
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = Logger.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    /**
     * 添加分片键值
     * ● 使用hintManager.addDatabaseShardingValue来添加数据源分片键值。
     * ● 使用hintManager.addTableShardingValue来添加表分片键值。
     * 分库不分表情况下，强制路由至某一个分库时，可使用hintManager.setDatabaseShardingValue方式添加分片。
     * 通过此方式添加分片键值后，将跳过SQL解析和改写阶段，从而提高整体执行效率。
     * @param order
     * @param orderItem
     * @return
     */
    @Override
    public OutPutObject insertOrderByHint(Order order, OrderItem orderItem) {
        DefaultKeyGenerator keyGenerator = new DefaultKeyGenerator();
        int db_shard_value = Math.abs(hash(order.getUser_id()));
        long order_id = keyGenerator.generateKey().longValue();
        int tb_shard_value = Math.abs(hash(order_id));
        System.out.println("==根据user_id:"+order.getUser_id()+"分库,对应分片列值:" + db_shard_value + ",根据order_id:"+order_id+"分表,对应分片列值:" + tb_shard_value);

        HintManager hintManager = HintManager.getInstance();
        hintManager.addDatabaseShardingValue("t_order", db_shard_value);
        hintManager.addTableShardingValue("t_order", tb_shard_value);
        hintManager.addDatabaseShardingValue("t_order_item",db_shard_value);
        hintManager.addTableShardingValue("t_order_item", tb_shard_value);
        try{
            order.setOrder_id(order_id);
            int count = orderRepository.insertOrder(order);
            System.out.println(count);
            long orderId = order.getOrder_id();
            System.out.println("=========" + order.getOrder_id() +"========");
            orderItem.setOrder_id(orderId);
            orderRepository.insertOrderItem(orderItem);
            hintManager.close();
            return new OutPutObject(true, "新增订单成功");
        }catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException("新增订单失败");
        }
    }

    /**
     * Description: 使用inline分片策略
     * @Author tym
     * @Create Date: 2019/3/20/0020 上午 11:20
     */
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
