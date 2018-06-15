package com.disarch.service.session;

import com.disarch.common.Channel;
import com.disarch.entity.UserSession;
import com.disarch.service.cache.ICacheService;
import com.disarch.util.SerializationUtils;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;

public class SessionService implements ISessionService {

    @Resource
    private ICacheService cacheService;

    @Value("#{sessionProps['pc.session.expire.time']}")
    private Integer pcSessionExpireTime;

    @Value("#{sessionProps['app.session.expire.time']}")
    private Integer appSessionExpireTime;

    @Value("#{sessionProps['wap.session.expire.time']}")
    private Integer wapSessionExpireTime;

    @Override
    public UserSession getSession(String sessionID) {
        if (sessionID == null) {
            return null;
        }
        sessionID = sessionID.trim();
        byte[] value = cacheService.get(SerializationUtils.serialize(sessionID));
        UserSession userSession = (UserSession) SerializationUtils.deserialize(value);
        if (userSession != null) {
            setSession(sessionID, userSession);
        }
        return userSession;
    }

    @Override
    public boolean setSession(String sessionID, UserSession userSession) {
        Preconditions.checkArgument(Strings.isNullOrEmpty(sessionID), "sessionID不能为空");
        Preconditions.checkNotNull(userSession, "userSession不能为空");
        Preconditions.checkNotNull(userSession.getChannel(), "userSession.channel不能为空");

        byte[] value = SerializationUtils.serialize(userSession);
        byte[] key = SerializationUtils.serialize(sessionID);

        Integer sessionExpireTime = null;
        if (userSession.getChannel() == Channel.PC) {
            sessionExpireTime = pcSessionExpireTime;
        } else if (userSession.getChannel() == Channel.APP) {
            sessionExpireTime = appSessionExpireTime;
        } else if (userSession.getChannel() == Channel.WAP) {
            sessionExpireTime = wapSessionExpireTime;
        }
        return cacheService.setWithExpire(key, value, sessionExpireTime);
    }

    @Override
    public boolean clearSession(String sessionID) {
        Preconditions.checkArgument(Strings.isNullOrEmpty(sessionID), "sessionID不能为空");
        byte[] key = SerializationUtils.serialize(sessionID);
        return cacheService.remove(key);
    }

    @Override
    public <T> T getSessionAttribute(String sessionID, String attributeName) {
        Preconditions.checkArgument(Strings.isNullOrEmpty(sessionID), "sessionID不能为空");
        Preconditions.checkArgument(Strings.isNullOrEmpty(attributeName), "attributeName不能为空");
        byte[] value = cacheService.get(SerializationUtils.serialize(sessionID));
        UserSession userSession = (UserSession) SerializationUtils.deserialize(value);
        if (userSession != null) {
            return (T) userSession.getAttributeMap().get(attributeName);
        }
        return null;
    }

    @Override
    public boolean setSessionAttribute(String sessionID, String attributeName, Object attributeValue) {
        Preconditions.checkArgument(Strings.isNullOrEmpty(sessionID), "sessionID不能为空");
        Preconditions.checkArgument(Strings.isNullOrEmpty(attributeName), "attributeName不能为空");
        Preconditions.checkNotNull(attributeValue, "attributeValue不能为空");
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
        Preconditions.checkArgument(Strings.isNullOrEmpty(sessionID), "sessionID不能为空");
        Preconditions.checkArgument(Strings.isNullOrEmpty(attributeName), "attributeName不能为空");
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
