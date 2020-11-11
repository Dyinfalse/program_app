package com.wechat.program.app.service;

import java.util.List;

public interface AppUserRoleService {


    List<Integer> selectRoleIdByUserId(Integer userId);
}
