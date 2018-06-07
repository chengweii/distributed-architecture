package com.disarch.mq.converter;

import com.disarch.mq.entity.BaseMessage;

public interface IMessageConverter {
    <T extends BaseMessage> String serialize(T message);

    <T extends BaseMessage> T deserialize(String msg, Class<T> messageClazz);
}
