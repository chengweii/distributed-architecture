package com.disarch.util;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

    /**
     * 验证cps跳转链接是否合法
     * @param redirectUrl　url
     * @return　是否合法
     */
    public static boolean checkCpsRedirectUrl(String redirectUrl) {
        if(isBlank(redirectUrl)){
            return false;
        }
        String regex = "^http:\\/?\\/?[A-Za-z0-9]+\\.?[A-Za-z0-9]*\\.jiuxian\\.com*|^http%3A%2F%2Fwww.jiuxian.com%2F*$|^http%3A%2F%2F[A-Za-z0-9]+\\.?[A-Za-z0-9]*\\.jiuxian\\.com*";
        Pattern pattern = Pattern.compile(regex);
        Matcher mat = pattern.matcher(redirectUrl);
        if (!mat.find()) {
            return false;
        }
        return true;
    }

    public static boolean checkRedirectUrl(String redirectUrl) {
        if(isBlank(redirectUrl)){
            return false;
        }
        String regex = "^http://\\w+\\.jiuxian\\.com.*$";//英文名字之间可以有空格
        Pattern pattern = Pattern.compile(regex);
        Matcher mat = pattern.matcher(redirectUrl);
        if (!mat.find()) {
            return false;
        }
        return true;
    }


    /**
     * 验证联系人姓名只能是英文字母或汉字
     * @param contacts　联系人姓名
     * @return　是否验证通过
     */
    public static boolean checkName(String contacts) {
        if(isBlank(contacts)){
            return false;
        }
        String pattern1 = "^([\u4e00-\u9fa5]+|([a-zA-Z]+\\s?)+)$";//英文名字之间可以有空格
        Pattern pattern = Pattern.compile(pattern1);
        Matcher mat = pattern.matcher(contacts);
        if (!mat.find()) {
            return false;
        }
        return true;
    }

    /**
     * 检查手机
     *
     * @param mobile　手机号
     * @return　是否校验成功
     */
    public static boolean checkPhoneNum(String mobile) {
        if(isBlank(mobile)){
            return false;
        }
        String regex = "^((1[0-9]))\\d{9}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(mobile);
        if (!m.find()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 检查邮箱
     *
     * @param str　邮箱
     * @return　是否验证成功
     */
    public static boolean checkEmail(String str) {
        if(isBlank(str)){
            return false;
        }
        String regex = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        if (!m.find()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 检查密码 由位数字或字母组成
     * @param str 密码
     * @param start	起始位数
     * @param end 结束位数
     * @author MengTao
     * @return　是否验证成功
     */
    public static boolean checkPassWord(String str, int start, int end) {
        if(isBlank(str)){
            return false;
        }
        String regex = "^[a-z0-9A-Z]{" + start + "," + end + "}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        if (!m.find()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 检查是不是数字组成
     *
     * @param str　待检查字符串
     * @return　是否为数字
     */
    public static boolean checkIsNum(String str) {
        if(isBlank(str)){
            return false;
        }
        String regex = "^[0-9]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        if (!m.find()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 检查是不是字母组成
     *
     * @param str　待检查字符串
     * @return　是否为全字母
     */
    public static boolean checkIsWord(String str) {
        if(isBlank(str)){
            return false;
        }
        String regex = "^[A-Za-z]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        if (!m.find()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 检查是不是 IP
     *
     * @param str　待检查字符串
     * @return　是否为ip
     */
    public static boolean isIp(String str) {
        if(isBlank(str)){
            return false;
        }
        String regex = "^(([1-9]|([1-9]\\d)|(1\\d\\d)|(2([0-4]\\d|5[0-5])))\\.)(([0-9]|([1-9]\\d)|(1\\d\\d)|(2([0-4]\\d|5[0-5])))\\.){2}([1-9]|([1-9]\\d)|(1\\d\\d)|(2([0-4]\\d|5[0-5])))$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        if (!m.find()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断str是否包含数字
     *
     * @param str　待检查字符串
     * @return　是否包含数字
     */
    public static boolean isNumeric(String str) {
        if(isBlank(str)){
            return false;
        }
        String regex = "[0-9]+?";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        if (m.find() == true) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证邮编
     *
     * @param post　邮编
     * @return　是否验证通过
     */
    public static boolean checkPost(String post) {
        if(isBlank(post)){
            return false;
        }
        if (post.matches("[0-9]\\d{5}(?!\\d)")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证身份证号
     *
     * @param idCard　身份证字符串
     * @return　是否验证通过
     */
    public static boolean checkIdCard(String idCard) {
        if(isBlank(idCard)){
            return false;
        }
        if (idCard.length() != 15 && idCard.length() != 18) {
            return false;
        }
        String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
        return Pattern.matches(regex, idCard);
    }

    /**
     * 验证固定电话
     *
     * @param telephone　固定电话
     * @return　是否验证通过
     */
    public static boolean checkTelephone(String telephone) {
        if(isBlank(telephone)){
            return false;
        }
        Pattern p1 = Pattern.compile("^\\d{1,4}-?\\d{6,8}$");
        Matcher m1 = p1.matcher(telephone);
        if (m1.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检验收货地址
     * @param address　收货地址
     * @return　是否验证通过
     */
    public static boolean checkAddress(String address) {
        if(isBlank(address)){
            return false;
        }
        Pattern pattern = Pattern.compile("^[A-Za-z0-9\u4e00-\u9fa5\\s,，.。?？!！\\-_【】（()）\\[Item\\]]+$");
        Matcher mat = pattern.matcher(address);
        if (!mat.find()) {
            return false;
        }
        return true;
    }

    /**
     * <p>Checks if a String is whitespace, empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param str  the String to check, may be null
     * @return <code>true</code> if the String is null, empty or whitespace
     * @since 2.0
     */
    public static boolean isBlank(String str) {
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

    /**
     * 保留2位小数
     * @param b　原值
     * @return　保留两位小数后的字符串
     */
    public static String changeTwoDecimal_f(BigDecimal b) {
        if (b == null) {
            return "0.00";
        }

        Double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String s_x = f1.toString();
        int pos_decimal = s_x.indexOf(".");
        if (pos_decimal < 0) {
            pos_decimal = s_x.length();
            s_x += '.';
        }
        while (s_x.length() <= pos_decimal + 2) {
            s_x += '0';
        }
        return s_x;
    }
}
