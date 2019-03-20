package com.bozy.cloud.sampleshardingjdbc.controller;

import com.bozy.cloud.sampleshardingjdbc.common.OutPutObject;
import com.bozy.cloud.sampleshardingjdbc.dao.OrderRepository;
import com.bozy.cloud.sampleshardingjdbc.domain.Order;
import com.bozy.cloud.sampleshardingjdbc.domain.OrderItem;
import com.bozy.cloud.sampleshardingjdbc.service.OrderService;
import io.shardingsphere.core.keygen.DefaultKeyGenerator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Description: 订单控制层接口
 * Created by tym on 2019/03/12 17:05.
 */
@RestController
@RequestMapping(value = "/order")
public class OrderController {

    private static final Logger logger = Logger.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    private static DefaultKeyGenerator keyGenerator = new DefaultKeyGenerator();

    /**
     * 新增订单
     * @return
     */
    @GetMapping(value = "/add")
    public String add(){
        for (int i = 0; i < 200; i++) {
            Order order = new Order();
            long user_id = keyGenerator.generateKey().longValue();
            order.setUser_id(user_id);
            order.setOrder_status(0);
            Date date = new Date();
            order.setCreate_time(date);
            order.setModify_time(date);
            order.setOrder_title("新增订单");
            order.setTotalAmount(new BigDecimal("666.66"));
            order.setPay_status(0);

            OrderItem item = new OrderItem();
            item.setCreate_time(date);
            item.setModify_time(date);
            item.setProduct_id(100);
            item.setProduct_name("西湖醋鱼");
            item.setProduct_price(new BigDecimal(666.66));
            item.setProduct_quantity(1);
            item.setProduct_sn("CN10005");
            item.setUser_id(user_id);
            try{
             OutPutObject opo = orderService.insertOrder(order, item);
//             OutPutObject _opo = orderService.insertOrderByHint(order, item);
             if(opo.isSuccess()){
                 System.out.println("======"+opo.getReturnMsg()+"->" + order.getOrder_id());
             }
            }catch (Exception ex){
                logger.error(ex.getMessage());
            }
        }
        return "success";
    }

    /**
     * 查询订单信息
     * @return
     */
    @GetMapping(value = "/search")
    public OutPutObject search(){
        List<Order> list = orderRepository.searchOrder();
        System.out.println(list.size() + " all");
        List<Order> list1 = orderRepository.queryWithIn();
        System.out.println(list1.size() + " In");
        List<Order> list2 = orderRepository.queryWithEqual();
        System.out.println(list2.size() + " Equal");
        List<Order> list3 = orderRepository.queryWithBetween();
        System.out.println(list3.size() + " Between");
        List<Map<String, Object>> list4 = orderRepository.queryUser();
        System.out.println(list4.size() + " user");
        return new OutPutObject(true, "查询成功");
    }

}
