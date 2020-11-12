package com.wechat.program.app.controller;

import com.wechat.program.app.constant.PermissionConstant;
import com.wechat.program.app.core.AjaxResult;
import com.wechat.program.app.entity.AppDeskUser;
import com.wechat.program.app.request.AppDeskUserDTO;
import com.wechat.program.app.request.AppDeskUserStatusDTO;
import com.wechat.program.app.service.AppDeskUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api( tags = {"桌子"})
@RestController
@RequestMapping("/desk")
public class DeskController {

    private AppDeskUserService appDeskUserService;

    @RequiresPermissions(PermissionConstant.DESK_USER_ADD)
    @ApiOperation("添加桌子")
    @PostMapping("/add")
    public AjaxResult add(@RequestBody AppDeskUserDTO dto) {
        AppDeskUser deskUser = appDeskUserService.add(dto);
        return AjaxResult.success(deskUser);
    }

    @ApiOperation("游戏状态，0:暂停(默认)，1：使用，2：结束'")
    @PostMapping("/updateStatus")
    public AjaxResult updateStatus(@RequestBody AppDeskUserStatusDTO dto) {
        appDeskUserService.updateStatus(dto);
        return AjaxResult.success();
    }

    @RequiresPermissions(PermissionConstant.DESK_LIST)
    @ApiOperation("桌子列表")
    @GetMapping("/list")
    public AjaxResult list() {
        return AjaxResult.success(appDeskUserService.selectDeskList());
    }

    @Autowired
    public void setAppDeskUserService(AppDeskUserService appDeskUserService) {
        this.appDeskUserService = appDeskUserService;
    }
}
