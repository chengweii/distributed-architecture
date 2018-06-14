package com.disarch.web.common;

public enum CommonAction {

    SUCCESS("操作成功", 1),
    FAILED("操作失败", 0);

    private String msg;
    private Integer status;

    private CommonAction(String msg, Integer status) {
        this.msg = msg;
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public static CommonAction getCommonAction(Integer status) {
        for (CommonAction type : CommonAction.values()) {
            if (type.getStatus().equals(status)) {
                return type;
            }
        }
        return null;
    }
}
