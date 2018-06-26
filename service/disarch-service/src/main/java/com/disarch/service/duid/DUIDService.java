package com.disarch.service.duid;

import com.disarch.cache.DUIDJedisAccessor;
import com.disarch.entity.DUIDPeriodType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DUIDService implements IDUIDService {
    @Resource
    private DUIDJedisAccessor dUIDJedisAccessor;

    @Override
    public Long getDUID(String name, DUIDPeriodType uUIDPeriodType) {
        return dUIDJedisAccessor.getDUID(name, uUIDPeriodType == null ? Integer.MAX_VALUE : uUIDPeriodType.getSeconds());
    }
}
