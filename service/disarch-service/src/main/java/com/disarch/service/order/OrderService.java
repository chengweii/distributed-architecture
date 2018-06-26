package com.disarch.service.order;

import com.disarch.dao.OrderMapper;
import com.disarch.entity.Order;
import com.disarch.mq.entity.SimpleMessage;
import com.disarch.mq.service.IMessageService;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Value("#{mqProps['mq.exchange.orderSync']}")
    private String orderSyncExchange;

    @Resource
    private IMessageService messageService;

    @Override
    public int createOrder(Order order) {
        Preconditions.checkNotNull(order, "订单数据不能为NULL");
        boolean result = orderMapper.insertOrder(order) > 0;
        SimpleMessage sendMessage = new SimpleMessage(0, new ArrayList<String>());
        messageService.sendAfterCommit(orderSyncExchange, "", sendMessage);
        return order.getId();
    }
}
