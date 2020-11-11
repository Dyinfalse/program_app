package com.wechat.program.app.service;

import com.wechat.program.app.core.IService;
import com.wechat.program.app.entity.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

public abstract class ABaseService<T extends BaseEntity> implements IService<T> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    protected Mapper<T> mapper;
    @Autowired
    protected MySqlMapper<T> mySqlMapper;

    public ABaseService() {
    }

    public Mapper<T> getMapper() {
        return this.mapper;
    }

    public List<T> select(T record) {
        return this.mapper.select(record);
    }

    public T selectByKey(Object key) {
        return this.mapper.selectByPrimaryKey(key);
    }

    public Boolean existsWithPrimaryKey(Object key) {
        return this.mapper.existsWithPrimaryKey(key);
    }

    public List<T> selectAll() {
        return this.mapper.selectAll();
    }

    public T selectOne(T record) {
        return this.mapper.selectOne(record);
    }

    public int selectCount(T record) {
        return this.mapper.selectCount(record);
    }

    public int selectCount(Example example) {
        return this.mapper.selectCountByExample(example);
    }

    public int insert(T record) {
        return this.mapper.insertSelective(record);
    }

    public int batchInsert(List<T> list) {
        return this.batchInsertList(list);
    }

    public int batchInsertList(List<T> list) {
        if (list != null && list.size() > 0) {
            Iterator var2 = list.iterator();

            while(var2.hasNext()) {
                T record = (T) var2.next();
                Date date = new Date();
                record.setCreateTime(date);
                record.setUpdateTime(date);
            }

            return this.mySqlMapper.insertList(list);
        } else {
            return 0;
        }
    }

    public int update(T entity) {
        entity.setCreateTime((Date)null);
        entity.setUpdateTime((Date)null);
        return this.mapper.updateByPrimaryKeySelective(entity);
    }

    @Transactional
    public int batchUpdate(List<T> list) {
        int result = 0;

        int count;
        for(Iterator var3 = list.iterator(); var3.hasNext(); result += count) {
            T record = (T) var3.next();
            count = this.update(record);
        }

        return result;
    }

    public int delete(T record) {
        return this.mapper.delete(record);
    }

    public int batchDelete(List<T> list) {
        int result = 0;

        int count;
        for(Iterator var3 = list.iterator(); var3.hasNext(); result += count) {
            T record = (T) var3.next();
            count = this.mapper.delete(record);
            if (count < 1) {
                this.logger.error("删除数据失败");
                throw new RuntimeException("删除数据失败");
            }
        }

        return result;
    }

    public int deleteByKey(Object key) {
        return this.mapper.deleteByPrimaryKey(key);
    }

    public T selectOneByExample(Object example) {
        return this.mapper.selectOneByExample(example);
    }

    public List<T> selectByExample(Object example) {
        return this.mapper.selectByExample(example);
    }

    public int selectCountByExample(Object example) {
        return this.mapper.selectCountByExample(example);
    }
}
