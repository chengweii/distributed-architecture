package com.disarch.mq.service;

import com.disarch.mq.entity.BaseMessage;
import com.disarch.mq.converter.IMessageConverter;
import com.disarch.mq.dao.MessageMapper;
import com.disarch.mq.dao.entity.DbMessage;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageService implements IMessageService {

    private static final String MESSAGE_CANNOT_BE_NULL = "Message cannot be null";

    private RabbitTemplate rabbitTemplate;

    private IMessageConverter messageConverter;

    private ExecutorService executor;

    @Autowired
    private MessageMapper messageMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);

    public MessageService(RabbitTemplate rabbitTemplate, IMessageConverter messageConverter) {
        super();
        this.rabbitTemplate = rabbitTemplate;
        this.messageConverter = messageConverter;
        executor = Executors.newCachedThreadPool();
    }

    @Override
    public void send(String exchange, String routingKey, BaseMessage message) {
        String messagePlain = messageConverter.serialize(Preconditions.checkNotNull(message, MESSAGE_CANNOT_BE_NULL));
        DbMessage dbMessage = new DbMessage(exchange, routingKey, messagePlain);
        messageMapper.insert(dbMessage);

        // Sync message to queue
        long id = dbMessage.getId();
        executor.execute(new Runnable() {

            @Override
            public void run() {
                try {
                    rabbitTemplate.convertAndSend(exchange, routingKey, messagePlain);
                    messageMapper.delete(id);
                } catch (AmqpException e) {
                    LOGGER.error("消息发送异常", e);
                }
            }
        });
    }

}