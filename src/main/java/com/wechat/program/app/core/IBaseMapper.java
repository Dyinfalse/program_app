package com.wechat.program.app.core;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface IBaseMapper<Entity extends IEntity> extends Mapper<Entity>, MySqlMapper<Entity> {
}
