package com.disarch.service.uuid;

import com.disarch.entity.UUIDPeriodType;

public interface IUUIDService {
    Long getUUID(String name, UUIDPeriodType uUIDPeriodType);
}
