package com.disarch.app.controller;

import com.disarch.app.common.Constans;
import com.disarch.entity.UserSession;
import com.disarch.service.session.ISessionService;
import com.disarch.util.GsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    @Resource
    private ISessionService sessionService;

    public UserSession getSession(HttpServletRequest request, HttpServletResponse response) {
        String sessionID = getSessionID(request, response);
        return sessionService.getSession(sessionID);
    }

    public boolean setSession(UserSession userSession, HttpServletRequest request, HttpServletResponse response) {
        String sessionID = getSessionID(request, response);
        return sessionService.setSession(sessionID, userSession);
    }

    public boolean clearSession(HttpServletRequest request, HttpServletResponse response) {
        String sessionID = getSessionID(request, response);
        return sessionService.clearSession(sessionID);
    }

    public <T> T getSessionAttribute(String attributeName, HttpServletRequest request, HttpServletResponse response) {
        String sessionID = getSessionID(request, response);
        return sessionService.getSessionAttribute(sessionID, attributeName);
    }

    public boolean setSessionAttribute(String attributeName, Object attributeValue, HttpServletRequest request, HttpServletResponse response) {
        String sessionID = getSessionID(request, response);
        return sessionService.setSessionAttribute(sessionID, attributeName, attributeValue);
    }

    public boolean clearSessionAttribute(String attributeName, HttpServletRequest request, HttpServletResponse response) {
        String sessionID = getSessionID(request, response);
        return sessionService.clearSessionAttribute(sessionID, attributeName);
    }

    private static String getSessionID(HttpServletRequest request, HttpServletResponse response) {
        String sessionId = request.getParameter(Constans.SESSION_ID_CACHE_KEY);
        if (StringUtils.isBlank(sessionId)) {
            sessionId = request.getSession().getId();
        }
        return sessionId;
    }

    public static void ajaxResponse(HttpServletResponse response, int status, String msg, Map<String, Object> data) throws IOException {
        response.setCharacterEncoding(ContentType.APPLICATION_JSON.getCharset().name());
        response.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        Map<String, Object> result = new HashMap<String, Object>(3);
        result.put(Constans.ACTION_MSG_ATTRIBUTE_KEY, msg);
        result.put(Constans.ACTION_STATUS_ATTRIBUTE_KEY, status);
        result.put(Constans.ACTION_DATA_ATTRIBUTE_KEY, data);
        response.getWriter().write(GsonUtils.toJson(result));
    }
}
