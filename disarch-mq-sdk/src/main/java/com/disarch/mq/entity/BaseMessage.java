package com.disarch.mq.entity;

import java.io.Serializable;
import java.util.UUID;

public abstract class BaseMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    protected BaseMessage() {
        id = UUID.randomUUID().toString();
    }

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
