package com.disarch.mq.converter;

import com.disarch.mq.entity.BaseMessage;
import com.google.gson.Gson;

public class MessageConverter implements IMessageConverter{
    private Gson gson;

    public MessageConverter(Gson gson) {
        super();
        this.gson = gson;
    }

    @Override
    public <T extends BaseMessage> String serialize(T message) {
        return gson.toJson(message);
    }

    @Override
    public <T extends BaseMessage> T deserialize(String msg, Class<T> messageClazz) {
        return gson.fromJson(msg, messageClazz);
    }
}
