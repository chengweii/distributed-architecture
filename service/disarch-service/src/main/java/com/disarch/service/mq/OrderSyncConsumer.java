package com.disarch.service.mq;

import com.disarch.mq.consumer.BaseConsumer;
import com.disarch.mq.converter.IMessageConverter;
import com.disarch.mq.entity.SimpleMessage;
import com.disarch.mq.service.IMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;

public class OrderSyncConsumer extends BaseConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderSyncConsumer.class);

    @Resource
    private IMessageConverter messageConverter;

    @Value("#{mqProps['mq.exchange.orderSync']}")
    private String orderSyncExchange;

    @Resource
    private IMessageService messageService;

    @Override
    protected void doHandle(String message) {
        SimpleMessage simpleMessage = messageConverter.deserialize(message, SimpleMessage.class);
    }
}
