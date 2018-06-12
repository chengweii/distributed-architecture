package com.disarch.mq.entity;

public enum MessageAction {
    /**
     * 处理成功
     */
    ACCEPT,
    /**
     * 可以重试的错误
     */
    RETRY,
    /**
     * 无需重试的错误
     */
    REJECT;
}
