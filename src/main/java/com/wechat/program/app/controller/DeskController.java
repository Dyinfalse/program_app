package com.wechat.program.app.controller;

import com.wechat.program.app.config.MessageSwitch;
import com.wechat.program.app.constant.PermissionConstant;
import com.wechat.program.app.core.AjaxResult;
import com.wechat.program.app.entity.AppDeskUser;
import com.wechat.program.app.request.AppDeskUserDTO;
import com.wechat.program.app.request.AppDeskUserStatusDTO;
import com.wechat.program.app.request.AppMessageEnableDTO;
import com.wechat.program.app.service.AppDeskUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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
        return AjaxResult.success(appDeskUserService.updateStatus(dto));
    }

    @RequiresPermissions(PermissionConstant.DESK_LIST)
    @ApiOperation("桌子列表")
    @GetMapping("/list")
    public AjaxResult list() {
        return AjaxResult.success(appDeskUserService.selectDeskList());
    }


    @ApiOperation("单个用户消费记录")
    @GetMapping("/statistics-by-user")
    public AjaxResult consumptionStatisticsByUser(@RequestParam("userId") Integer userId) {
        return AjaxResult.success(appDeskUserService.consumptionStatisticsByUserId(userId));
    }

    @ApiOperation("修改短信功能")
    @PostMapping("/set-message")
    public AjaxResult setMessageEnable(@RequestBody AppMessageEnableDTO dto) {
        MessageSwitch.setMessageEnable(dto.getMessageEnable());
        return AjaxResult.success(MessageSwitch.getMessageEnable());
    }

    @ApiOperation("获取短信功能开关")
    @GetMapping("/get-message")
    public AjaxResult getMessageEnable() {
        return AjaxResult.success(MessageSwitch.getMessageEnable());
    }

    @Autowired
    public void setAppDeskUserService(AppDeskUserService appDeskUserService) {
        this.appDeskUserService = appDeskUserService;
    }
}
