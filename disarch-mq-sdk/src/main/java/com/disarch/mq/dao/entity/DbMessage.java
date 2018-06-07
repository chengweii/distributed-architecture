package com.disarch.mq.dao.entity;

import java.io.Serializable;

public class DbMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private String exchange;
    private String routing;
    private String message;

    public DbMessage(String exchange, String routing, String message) {
        super();
        this.exchange = exchange;
        this.routing = routing;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getRouting() {
        return routing;
    }

    public void setRouting(String routing) {
        this.routing = routing;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
