package com.disarch.entity;

import com.disarch.common.Channel;

import java.util.HashMap;
import java.util.Map;

public class UserSession {
    private int userId;

    private Channel channel;

    /**
     * 注意：session中请勿存放过多数据，否则可能会影响用户访问性能
     */
    private Map<String, Object> attributeMap = new HashMap<String, Object>();

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Map<String, Object> getAttributeMap() {
        return attributeMap;
    }

    public void setAttributeMap(Map<String, Object> attributeMap) {
        this.attributeMap = attributeMap;
    }
}
