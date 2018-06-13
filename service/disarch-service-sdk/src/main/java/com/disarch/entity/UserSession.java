package com.disarch.entity;

import java.util.HashMap;
import java.util.Map;

public class UserSession {
    private int userId;
    private Map<String, Object> attributeMap = new HashMap<String, Object>();

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Map<String, Object> getAttributeMap() {
        return attributeMap;
    }

    public void setAttributeMap(Map<String, Object> attributeMap) {
        this.attributeMap = attributeMap;
    }
}
