package com.disarch.mq.consumer;

import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseConsumer.class);

    public void handle(String message) {
        LOGGER.info("Receive message: {}", message);
        try {
            doHandle(message);
        } catch (Exception e) {
            LOGGER.error("Message: {} with Error: {}", message, e);
            throw Throwables.propagate(e);
        }
    }

    protected abstract void doHandle(String message);

}
