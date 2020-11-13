package com.wechat.program.app.mapper;

import com.wechat.program.app.core.IBaseMapper;
import com.wechat.program.app.entity.AppUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppUserMapper extends IBaseMapper<AppUser> {

    List<AppUser> selectByKey(@Param("keyword") String keyword);

    List<AppUser> selectMembers(@Param("type") Integer type);

    AppUser selectNameAndPassword(@Param("name") String name, @Param("passWord") String passWord);

    AppUser selectByPhone(@Param("phone") String phone);

    Integer getComboOfDuration(@Param("userId") Integer userId);

    List<AppUser> selectListByType(@Param("type") Integer type);

    AppUser selectNoMembersByPhone(String phone);

    Integer selectByToken(@Param("token") String token);

    void updateToken(AppUser appUser);

    Integer selectByTokenAndPermissionCode(@Param("token")String token, @Param("code")String code);

    AppUser selectByPhoneAndPassword(@Param("phone") String phone, @Param("password")String password);

    int updateByPrimaryKeyOverSelective(AppUser record);
}
