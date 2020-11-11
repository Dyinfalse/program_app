package com.wechat.program.app.mapper;

import com.wechat.program.app.core.IBaseMapper;
import com.wechat.program.app.entity.AppCombo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppComboMapper extends IBaseMapper<AppCombo> {

    AppCombo selectByName(@Param("name") String name);
}
