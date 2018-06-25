package com.disarch.service.uuid;

import com.disarch.cache.UUIDJedisAccessor;
import com.disarch.entity.UUIDPeriodType;

import javax.annotation.Resource;

public class UUIDService implements IUUIDService {
    @Resource
    private UUIDJedisAccessor uUIDJedisAccessor;

    @Override
    public Long getUUID(String name, UUIDPeriodType uUIDPeriodType) {
        return uUIDJedisAccessor.getUUID(name, uUIDPeriodType == null ? null : uUIDPeriodType.getSeconds());
    }
}
