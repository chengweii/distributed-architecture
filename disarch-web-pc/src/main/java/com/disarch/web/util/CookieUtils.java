package com.disarch.web.util;

import com.disarch.web.common.Constans;
import com.google.common.base.Strings;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

    /**
     * 创建主域名cookie
     *
     * @param key
     * @param value
     * @param response
     */
    public static void addDomainCookie(String key, String value, HttpServletResponse response) {
        Cookie cookie = new Cookie(key, value);
        cookie.setDomain(Constans.PC_COOKIE_DOMAIN);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 创建主域名下cookie
     *
     * @param key
     * @param maxAge   0立即删除，-1关闭浏览器后删除
     * @param value
     * @param response
     */
    public static void addDomainCookieAndAge(String key, int maxAge, String value, HttpServletResponse response) {
        Cookie cookie = new Cookie(key, value);
        cookie.setDomain(Constans.PC_COOKIE_DOMAIN);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 创建指定域下的cookie
     *
     * @param domain
     * @param key
     * @param maxAge   0立即删除，-1关闭浏览器后删除
     * @param value
     * @param response
     */
    public static void addDomainCookieAndAgeForDomain(String domain, String key, int maxAge, String value, HttpServletResponse response) {
        Cookie cookie = new Cookie(key, value);
        cookie.setDomain(domain);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 创建用于保存用户sessionId的cookie
     *
     * @param request
     * @param response
     * @return
     */
    public static String createSessionIdCookie(HttpServletRequest request, HttpServletResponse response) {
        String sessionId = request.getSession().getId();
        Cookie cookie = new Cookie(Constans.SESSION_ID_CACHE_KEY, sessionId);
        cookie.setDomain(Constans.PC_COOKIE_DOMAIN);
        cookie.setPath("/");
        response.addCookie(cookie);
        return sessionId;
    }

    /**
     * 获取用户sessionID
     *
     * @param request
     * @param response
     * @return
     */
    public static String getSessionID(HttpServletRequest request, HttpServletResponse response) {
        String sessionID = getCookieValue(request, Constans.SESSION_ID_CACHE_KEY);
        if (Strings.isNullOrEmpty(sessionID) || sessionID.equals("null")) {
            sessionID = createSessionIdCookie(request, response);
        }
        return sessionID;
    }

    /**
     * 获取cookie值
     *
     * @param request
     * @param key
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
