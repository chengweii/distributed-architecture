package com.disarch.mq.converter;

import com.disarch.mq.entity.BaseMessage;
import com.disarch.web.util.GsonUtils;


public class MessageConverter implements IMessageConverter{
    public MessageConverter() {
        super();
    }

    @Override
    public <T extends BaseMessage> String serialize(T message) {
        return GsonUtils.toJson(message);
    }

    @Override
    public <T extends BaseMessage> T deserialize(String msg, Class<T> messageClazz) {
        return GsonUtils.fromJson(msg, messageClazz);
    }
}
