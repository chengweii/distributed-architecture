package com.disarch.util;

public class RegexUtils {

    public static enum CheckType {
        CONTACT_NAME("联系人姓名", "^([\u4e00-\u9fa5]+|([a-zA-Z]+\\s?)+)$"),
        MOBILE("手机号", "^1(3|4|5|6|7|8|9)[0-9]{9}$"),
        TELEPHONE("固定电话", "^(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)$"),
        EMAIL("邮箱", "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"),
        NUMBER("数字", "^[0-9]+$"),
        LETTER("字母", "^[A-Za-z]+$"),
        IP("ip地址", "^(([1-9]|([1-9]\\d)|(1\\d\\d)|(2([0-4]\\d|5[0-5])))\\.)(([0-9]|([1-9]\\d)|(1\\d\\d)|(2([0-4]\\d|5[0-5])))\\.){2}([1-9]|([1-9]\\d)|(1\\d\\d)|(2([0-4]\\d|5[0-5])))$"),
        POSTAL_CODE("邮政编码", "^[0-9][0-9]{5}$"),
        ID_CARD_NO("身份证号码", "^\\d{15}$|^\\d{17}[0-9Xx]$"),
        ADDRESS("收货地址", "^[A-Za-z0-9\u4e00-\u9fa5\\s,，.。?？!！\\-_【】（）()\\[Item\\]]+$");
        private String expression;
        private String description;

        private CheckType(String description, String expression) {
            this.description = description;
            this.expression = expression;
        }

        public String getExpression() {
            return expression;
        }

        public void setExpression(String expression) {
            this.expression = expression;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static boolean check(String value, CheckType checkType) {
        if (isBlank(value) || checkType == null)
            return false;
        return value.matches(checkType.getExpression());
    }

    private static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }
}
