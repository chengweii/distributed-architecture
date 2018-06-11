package com.disarch.mq.consumer;

import com.google.common.base.Throwables;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

public abstract class BaseConsumer implements ChannelAwareMessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseConsumer.class);

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        LOGGER.info("Receive message: {}", message);
        try {
            doHandle(message, channel);
        } catch (Exception e) {
            LOGGER.error("Message: {} with Error: {}", message, e);
            throw Throwables.propagate(e);
        }
    }

    protected abstract void doHandle(Message message, Channel channel);

}
