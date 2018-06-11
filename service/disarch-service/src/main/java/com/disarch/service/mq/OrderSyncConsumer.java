package com.disarch.service.mq;

import com.disarch.mq.consumer.BaseConsumer;
import com.disarch.mq.converter.IMessageConverter;
import com.disarch.mq.entity.SimpleMessage;
import com.disarch.mq.service.IMessageService;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;

import javax.annotation.Resource;
import java.io.IOException;

public class OrderSyncConsumer extends BaseConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderSyncConsumer.class);

    @Resource
    private IMessageConverter messageConverter;

    @Resource
    private IMessageService messageService;

    @Override
    protected void doHandle(Message message, Channel channel) {
        try {
            LOGGER.info("Consum start: {}", message);
            SimpleMessage simpleMessage = messageConverter.deserialize(String.valueOf(message.getBody()), SimpleMessage.class);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            LOGGER.error("Consum failed", e);
            try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            } catch (IOException e1) {
                LOGGER.error("Nack failed", e);
            }
        }
    }
}
