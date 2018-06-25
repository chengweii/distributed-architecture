package com.disarch.service.session;

import com.disarch.cache.SessionJedisAccessor;
import com.disarch.common.Channel;
import com.disarch.entity.UserSession;
import com.disarch.util.SerializationUtils;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import javax.annotation.Resource;

public class SessionService implements ISessionService {

    @Resource
    private SessionJedisAccessor sessionJedisAccessor;

    @Override
    public UserSession getSession(String sessionID) {
        if (sessionID == null) {
            return null;
        }
        sessionID = sessionID.trim();
        byte[] value = sessionJedisAccessor.get(SerializationUtils.serialize(sessionID));
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
            sessionExpireTime = Channel.PC.getValue();
        } else if (userSession.getChannel() == Channel.APP) {
            sessionExpireTime = Channel.APP.getValue();
        } else if (userSession.getChannel() == Channel.WAP) {
            sessionExpireTime = Channel.WAP.getValue();
        }
        return sessionJedisAccessor.setWithExpire(key, value, sessionExpireTime);
    }

    @Override
    public boolean clearSession(String sessionID) {
        Preconditions.checkArgument(Strings.isNullOrEmpty(sessionID), "sessionID不能为空");
        byte[] key = SerializationUtils.serialize(sessionID);
        return sessionJedisAccessor.remove(key);
    }

    @Override
    public <T> T getSessionAttribute(String sessionID, String attributeName) {
        Preconditions.checkArgument(Strings.isNullOrEmpty(sessionID), "sessionID不能为空");
        Preconditions.checkArgument(Strings.isNullOrEmpty(attributeName), "attributeName不能为空");
        byte[] value = sessionJedisAccessor.get(SerializationUtils.serialize(sessionID));
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
        byte[] value = sessionJedisAccessor.get(SerializationUtils.serialize(sessionID));
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
        byte[] value = sessionJedisAccessor.get(SerializationUtils.serialize(sessionID));
        UserSession userSession = (UserSession) SerializationUtils.deserialize(value);
        if (userSession != null) {
            userSession.getAttributeMap().remove(attributeName);
            setSession(sessionID, userSession);
            return true;
        }
        return false;
    }
}
