package com.disarch.mq.dao;

import com.disarch.mq.dao.entity.DbMessage;

public interface MessageMapper {
    void insert(DbMessage dbMessage);

    void delete(long id);
}
