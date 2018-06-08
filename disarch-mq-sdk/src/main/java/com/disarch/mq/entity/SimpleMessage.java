package com.disarch.mq.entity;

import java.util.List;

public class SimpleMessage extends BaseMessage {
    private int eventCode;
    private List<String> keyList;

    public SimpleMessage(int eventCode, List<String> keyList) {
        this.eventCode = eventCode;
        this.keyList = keyList;
    }

    public int getEventCode() {
        return eventCode;
    }

    public void setEventCode(int eventCode) {
        this.eventCode = eventCode;
    }

    public List<String> getKeyList() {
        return keyList;
    }

    public void setKeyList(List<String> keyList) {
        this.keyList = keyList;
    }

    @Override
    public String toString() {
        return "SimpleMessage{" +
                "id=" + getId() +
                "eventCode=" + eventCode +
                ", keyList=" + keyList +
                '}';
    }
}
