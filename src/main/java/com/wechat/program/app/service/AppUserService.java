package com.wechat.program.app.service;

import com.wechat.program.app.core.IService;
import com.wechat.program.app.entity.AppDeskUser;
import com.wechat.program.app.entity.AppUser;
import com.wechat.program.app.entity.AppUserCombo;
import com.wechat.program.app.request.AppUserDTO;
import com.wechat.program.app.request.AppUserTotalTimeDTO;
import com.wechat.program.app.vo.AppUserVo;
import com.wechat.program.app.vo.UserConsumptionStatisticsVO;

import java.util.List;

public interface AppUserService extends IService<AppUser> {


    AppUserVo add(AppUserDTO dto) ;

    List<AppUser> searchKey (String keyword);

    List<AppUser> members(Integer type, Integer page, Integer pageSize);

    List<AppUser> members(Integer type);

    AppUser selectNameAndPassword(String name, String password);

    AppUser selectByPhone(String phone);

    AppUser updateAppUser(AppUserDTO dto);

    AppUserVo selectByShiroName(String phone);

    Integer getComboOfDuration(Integer userId);

    List<AppUser> selectListByType(Integer type);


    List<UserConsumptionStatisticsVO> consumptionStatistics(Integer start, Integer pageSize);


    Boolean selectByToken(String token);

    void updateToken(String token, Integer id);

    Integer selectByTokenAndPermissionCode(String token, String code);

    AppUser selectByPhoneAndPassword(String phone, String password);

    void updateByPrimaryKeyOverSelective(AppUser appUser);

    AppUser updateTotalTime(AppUserTotalTimeDTO dto);
}
