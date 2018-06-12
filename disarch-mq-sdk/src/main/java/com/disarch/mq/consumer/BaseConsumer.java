package com.disarch.mq.consumer;

import com.disarch.mq.common.Constans;
import com.disarch.mq.dao.MessageMapper;
import com.disarch.mq.dao.entity.DbMessage;
import com.disarch.mq.entity.MessageAction;
import com.google.common.base.Preconditions;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseConsumer implements ChannelAwareMessageListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseConsumer.class);

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        LOGGER.info("Receive message: {}", message);

        Preconditions.checkNotNull(message, Constans.MESSAGE_CANNOT_BE_NULL);
        Preconditions.checkNotNull(message.getMessageProperties(), Constans.MESSAGE_PROPERTIES_CANNOT_BE_NULL);

        MessageAction action = MessageAction.RETRY;
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        String messagePlain = String.valueOf(message.getBody());

        try {
            action = doHandle(messagePlain, channel);
        } catch (Exception e) {
            LOGGER.error("Message: {} with Error: {}", message, e);
        } finally {
            try {
                if (action == MessageAction.ACCEPT) {
                    channel.basicAck(deliveryTag, false);
                } else if (action == MessageAction.RETRY && !message.getMessageProperties().isRedelivered()) {
                    channel.basicNack(deliveryTag, false, true);
                } else {
                    channel.basicNack(deliveryTag, false, false);
                    saveFailedMessage(message, messagePlain);
                }
            } catch (Exception e) {
                LOGGER.error("Message: {} nack failed , Error: {}", message, e);
                saveFailedMessage(message, messagePlain);
            }
        }
    }

    private void saveFailedMessage(Message message, String messagePlain) {
        String receivedExchange = message.getMessageProperties().getReceivedExchange();
        String receivedRoutingKey = message.getMessageProperties().getReceivedRoutingKey();
        DbMessage dbMessage = new DbMessage(receivedExchange, receivedRoutingKey, messagePlain);
        messageMapper.insert(dbMessage);
    }

    protected abstract MessageAction doHandle(String messagePlain, Channel channel);

}
