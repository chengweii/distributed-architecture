package com.disarch.service.order;

import com.disarch.entity.Order;

public interface IOrderService {
    /**
     * 创建订单
     *
     * @param order
     * @return
     */
    int createOrder(Order order);
}
