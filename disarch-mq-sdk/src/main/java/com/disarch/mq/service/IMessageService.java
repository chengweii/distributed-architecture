package com.disarch.mq.service;

import com.disarch.mq.entity.BaseMessage;

public interface IMessageService {
    /**
     * 发送消息
     *
     * @param exchange
     * @param routingKey
     * @param message
     */
    void send(String exchange, String routingKey, BaseMessage message);

    /**
     * 事务提交后发送消息
     * 注意：仅限事务环境下使用，非事务环境请使用{@link IMessageService#send}
     * @param exchange
     * @param routingKey
     * @param message
     */
    void sendAfterCommit(String exchange, String routingKey, BaseMessage message);
}
