package com.disarch.service.session;

import com.disarch.entity.UserSession;

public interface ISessionService {
    UserSession getSession(String sessionID);

    boolean setSession(String sessionID, UserSession userSession);

    boolean clearSession(String sessionID);

    <T> T getSessionAttribute(String sessionID, String attributeName);

    boolean setSessionAttribute(String sessionID, String attributeName, Object attributeValue);

    boolean clearSessionAttribute(String sessionID, String attributeName);
}
