package com.disarch.service.uuid;

import com.disarch.entity.UUIDPeriodType;
import com.disarch.service.cache.ICacheService;

import javax.annotation.Resource;

public class UUIDService implements IUUIDService {
    @Resource
    private ICacheService cacheService;

    @Override
    public String getUUIDOfString(String name, UUIDPeriodType uUIDPeriodType) {
        return null;
    }

    @Override
    public Long getUUIDOfNumber(String name, UUIDPeriodType uUIDPeriodType) {
        return null;
    }
}
