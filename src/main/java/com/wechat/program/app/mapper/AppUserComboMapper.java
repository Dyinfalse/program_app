package com.wechat.program.app.mapper;

import com.wechat.program.app.entity.AppUserCombo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppUserComboMapper{

    int insertAppUserCombo(AppUserCombo appUserCombo);

    int updateUsed(AppUserCombo appUserCombo);

    List<AppUserCombo> selectByUserId(@Param("usedId") Integer usedId);

    Integer selectComboIdByUserId(@Param("usedId")Integer userId);
}
