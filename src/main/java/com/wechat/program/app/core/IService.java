package com.wechat.program.app.core;

import com.wechat.program.app.entity.BaseEntity;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

public interface IService<T extends BaseEntity> {
    List<T> select(T var1);

    T selectByKey(Object var1);

    Boolean existsWithPrimaryKey(Object var1);

    List<T> selectAll();

    T selectOne(T var1);

    int selectCount(T var1);

    int selectCount(Example var1);

    int insert(T var1);

    @Transactional(
            rollbackFor = {Exception.class}
    )
    int batchInsert(List<T> var1);

    @Transactional(
            rollbackFor = {Exception.class}
    )
    int batchInsertList(List<T> var1);

    int update(T var1);

    int batchUpdate(List<T> var1);

    int delete(T var1);

    int batchDelete(List<T> var1);

    int deleteByKey(Object var1);

    T selectOneByExample(Object var1);

    List<T> selectByExample(Object var1);

    int selectCountByExample(Object var1);
}
