package com.disarch.service.order;

import com.disarch.dao.OrderMapper;
import com.disarch.entity.Order;
import com.disarch.mq.entity.SimpleMessage;
import com.disarch.mq.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.util.ArrayList;

public class OrderService implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Value("#{mqProps['mq.exchange.orderSync']}")
    private String orderSyncExchange;

    @Resource
    private IMessageService messageService;

    @Override
    public boolean createOrder(Order order) {
        boolean result = orderMapper.insertOrder(order) > 0;
        SimpleMessage sendMessage = new SimpleMessage(0,new ArrayList<String>());
        messageService.sendAfterCommit(orderSyncExchange,"",sendMessage);
        return result;
    }
}
