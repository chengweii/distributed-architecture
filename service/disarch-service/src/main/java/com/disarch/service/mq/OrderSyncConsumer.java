package com.disarch.service.mq;

import com.disarch.mq.consumer.BaseConsumer;
import com.disarch.mq.converter.IMessageConverter;
import com.disarch.mq.entity.MessageAction;
import com.disarch.mq.entity.SimpleMessage;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

public class OrderSyncConsumer extends BaseConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderSyncConsumer.class);

    @Resource
    private IMessageConverter messageConverter;

    @Override
    protected MessageAction doHandle(String messagePlain, Channel channel) {
        SimpleMessage simpleMessage = messageConverter.deserialize(messagePlain, SimpleMessage.class);
        return MessageAction.ACCEPT;
    }
}
