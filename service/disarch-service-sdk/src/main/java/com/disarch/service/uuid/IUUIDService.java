package com.disarch.service.uuid;

import com.disarch.entity.UUIDPeriodType;

public interface IUUIDService {
    String getUUIDOfString(String name, UUIDPeriodType uUIDPeriodType);

    Long getUUIDOfNumber(String name, UUIDPeriodType uUIDPeriodType);
}
