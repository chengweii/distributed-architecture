package com.disarch.service.session;

import com.disarch.entity.UserSession;
import com.disarch.service.cache.ICacheService;
import com.disarch.util.SerializationUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;

public class SessionService implements ISessionService {

    @Resource
    private ICacheService cacheService;

    @Value("#{sessionProps['session.expire.time']}")
    private Integer sessionExpireTime;

    @Override
    public UserSession getSession(String sessionID) {
        byte[] value = cacheService.get(SerializationUtils.serialize(sessionID));
        UserSession userSession = (UserSession) SerializationUtils.deserialize(value);
        if (userSession != null) {
            setSession(sessionID, userSession);
        }
        return userSession;
    }

    @Override
    public boolean setSession(String sessionID, UserSession userSession) {
        byte[] value = SerializationUtils.serialize(userSession);
        byte[] key = SerializationUtils.serialize(sessionID);
        return cacheService.setWithExpire(key, value, sessionExpireTime);
    }

    @Override
    public boolean clearSession(String sessionID) {
        byte[] key = SerializationUtils.serialize(sessionID);
        return cacheService.remove(key);
    }

    @Override
    public <T> T getSessionAttribute(String sessionID, String attributeName) {
        byte[] value = cacheService.get(SerializationUtils.serialize(sessionID));
        UserSession userSession = (UserSession) SerializationUtils.deserialize(value);
        if (userSession != null) {
            return (T) userSession.getAttributeMap().get(attributeName);
        }
        return null;
    }

    @Override
    public boolean setSessionAttribute(String sessionID, String attributeName, Object attributeValue) {
        byte[] value = cacheService.get(SerializationUtils.serialize(sessionID));
        UserSession userSession = (UserSession) SerializationUtils.deserialize(value);
        if (userSession != null) {
            userSession.getAttributeMap().put(attributeName, attributeValue);
            setSession(sessionID, userSession);
            return true;
        }
        return false;
    }

    @Override
    public boolean clearSessionAttribute(String sessionID, String attributeName) {
        byte[] value = cacheService.get(SerializationUtils.serialize(sessionID));
        UserSession userSession = (UserSession) SerializationUtils.deserialize(value);
        if (userSession != null) {
            userSession.getAttributeMap().remove(attributeName);
            setSession(sessionID, userSession);
            return true;
        }
        return false;
    }
}
