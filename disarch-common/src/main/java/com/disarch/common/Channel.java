package com.disarch.common;

public enum Channel {

    PC("pc", 1),
    APP("app", 2),
    WAP("wap", 3);

    private String name;
    private int value;

    private Channel(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static Channel getChannel(int value) {
        for (Channel type : Channel.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        return null;
    }
}
