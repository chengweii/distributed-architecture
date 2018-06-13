package com.disarch.web.controller;

import com.disarch.entity.UserSession;
import com.disarch.service.session.ISessionService;
import com.disarch.web.util.CookieUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private ISessionService sessionService;

    public UserSession getSession(HttpServletRequest request, HttpServletResponse response) {
        String sessionID = CookieUtils.getSessionID(request, response);
        return sessionService.getSession(sessionID);
    }

    public boolean setSession(UserSession userSession, HttpServletRequest request, HttpServletResponse response) {
        String sessionID = CookieUtils.getSessionID(request, response);
        return sessionService.setSession(sessionID, userSession);
    }

    public boolean clearSession(HttpServletRequest request, HttpServletResponse response) {
        String sessionID = CookieUtils.getSessionID(request, response);
        return sessionService.clearSession(sessionID);
    }

    public <T> T getSessionAttribute(String attributeName, HttpServletRequest request, HttpServletResponse response) {
        String sessionID = CookieUtils.getSessionID(request, response);
        return sessionService.getSessionAttribute(sessionID, attributeName);
    }

    public boolean setSessionAttribute(String attributeName, Object attributeValue, HttpServletRequest request, HttpServletResponse response) {
        String sessionID = CookieUtils.getSessionID(request, response);
        return sessionService.setSessionAttribute(sessionID, attributeName, attributeValue);
    }

    public boolean clearSessionAttribute(String attributeName, HttpServletRequest request, HttpServletResponse response) {
        String sessionID = CookieUtils.getSessionID(request, response);
        return sessionService.clearSessionAttribute(sessionID, attributeName);
    }
}
