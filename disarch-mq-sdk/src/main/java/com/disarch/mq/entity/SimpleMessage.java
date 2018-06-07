package com.disarch.mq.entity;

import java.util.List;

public class SimpleMessage extends BaseMessage {
    private int event;
    private List<String> keyList;

    public SimpleMessage(int event, List<String> keyList) {
        this.event = event;
        this.keyList = keyList;
    }

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
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
                "event=" + event +
                ", keyList=" + keyList +
                '}';
    }
}
