package com.disarch.app.common;

public enum CommonActionResult {

    SUCCESS("操作成功", 1),
    FAILED("操作失败", 0),
    VERIFY_SIGNATURE_FAILURE("验证签名失败:", 88),
    USER_SESSION_EXPIRED("您的登录已过期，请重新登录", 99),
    SYSTEM_EXCEPTION("系统异常", 500);

    private String msg;
    private Integer status;

    private CommonActionResult(String msg, Integer status) {
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

    public static CommonActionResult getCommonAction(Integer status) {
        for (CommonActionResult type : CommonActionResult.values()) {
            if (type.getStatus().equals(status)) {
                return type;
            }
        }
        return null;
    }
}
