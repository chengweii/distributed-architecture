package com.disarch.entity;

public enum UUIDPeriodType {
    DAY(1, 24 * 60 * 60),
    MONTH(2, 30 * 24 * 60 * 60),
    QUARTER(3, 4 * 30 * 24 * 60 * 60),
    YEAR(4, 12 * 30 * 24 * 60 * 60);

    private UUIDPeriodType(int code, int seconds) {
        this.code = code;
        this.seconds = seconds;
    }

    private int code;
    private int seconds;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
