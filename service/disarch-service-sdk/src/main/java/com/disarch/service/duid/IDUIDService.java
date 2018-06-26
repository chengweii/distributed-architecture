package com.disarch.service.duid;

import com.disarch.entity.DUIDPeriodType;

public interface IDUIDService {
    Long getDUID(String name, DUIDPeriodType uUIDPeriodType);
}
