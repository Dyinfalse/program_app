package com.wechat.program.app.service;

import com.wechat.program.app.core.IService;
import com.wechat.program.app.entity.AppRole;
import com.wechat.program.app.entity.AppUser;
import com.wechat.program.app.request.AppUserDTO;
import com.wechat.program.app.vo.AppUserVo;

import java.util.List;

public interface AppRoleService extends IService<AppRole> {

    List<AppRole> selectRoles(List<Integer> roleIds);
}
