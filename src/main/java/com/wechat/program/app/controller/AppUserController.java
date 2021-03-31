package com.wechat.program.app.controller;

import com.wechat.program.app.constant.PermissionConstant;
import com.wechat.program.app.core.AjaxResult;
import com.wechat.program.app.entity.AppUser;
import com.wechat.program.app.request.AppUserDTO;
import com.wechat.program.app.request.AppUserTotalTimeDTO;
import com.wechat.program.app.request.SendSmsDto;
import com.wechat.program.app.service.AppUserService;
import com.wechat.program.app.service.SmsService;
import com.wechat.program.app.vo.AppUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Api( tags = {"用户"})
@RestController
@RequestMapping("/app-user")
public class AppUserController {


    private AppUserService appUserService;
    private SmsService smsService;

    @RequiresPermissions(PermissionConstant.MEMBERS_ADD)
    @ApiOperation("会员注册")
    @PostMapping("/registry")
    public AjaxResult registry(@RequestBody AppUserDTO dto){
        AppUser appUser = appUserService.selectByPhone(dto.getPhone());
        if (Objects.nonNull(appUser)) {
            return AjaxResult.failed("此客户已存在！");
        }
        AppUserVo vo = appUserService.add(dto);
        return AjaxResult.success(vo);
    }

    @RequiresPermissions(PermissionConstant.MEMBERS_LIST)
    @ApiOperation("关键字查询，手机号或者名称")
    @GetMapping("/keyword")
    public AjaxResult searchKey(@RequestParam("keyword") String keyword) {
        return AjaxResult.success(appUserService.searchKey(keyword));
    }

    @RequiresPermissions(PermissionConstant.MEMBERS_LIST)
    @ApiOperation("会员或非会员列表")
    @GetMapping("/members")
    public AjaxResult members(@RequestParam(name = "type", defaultValue = "0") Integer type ) {
        return AjaxResult.success(appUserService.members(type));
    }

    @RequiresPermissions(PermissionConstant.MEMBERS_UPDATE)
    @ApiOperation("会员编辑")
    @PostMapping("/update")
    public AjaxResult update(@RequestBody AppUserDTO dto) {
        AppUser appUser = appUserService.selectByKey(dto.getId());
        if (Objects.isNull(appUser)) {
            return AjaxResult.failed("此客户不存在！");
        }
        return AjaxResult.success(appUserService.updateAppUser(dto));
    }

    @ApiOperation("管理员或工作人员列表，1 ：员工，2：管理员")
    @GetMapping("/no-customer")
    public AjaxResult noCustomer(@RequestParam("type") Integer type) {
        return AjaxResult.success(appUserService.selectListByType(type));
    }

    @RequiresPermissions(PermissionConstant.CONSUMPTION_STATISTICS)
    @ApiOperation("消费统计")
    @GetMapping("/statistics")
    public AjaxResult consumptionStatistics() {
        return AjaxResult.success(appUserService.consumptionStatistics());
    }


    @RequiresPermissions(PermissionConstant.MEMBERS_UPDATE)
    @ApiOperation("修改剩余时长")
    @PostMapping("/update-total-time")
    public AjaxResult updateTotalTime(@RequestBody AppUserTotalTimeDTO dto) {
        return AjaxResult.success(appUserService.updateTotalTime(dto));
    }

    @ApiOperation("发送短信")
    @PostMapping("/sendUserMsg")
    public AjaxResult sendUserMsg (@RequestBody SendSmsDto dto) {
        smsService.sendSmsCode(dto);
        return AjaxResult.success("发送成功");
    }

    @Autowired
    public void setAppUserService(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Autowired
    public void setSmsService(SmsService smsService) {
        this.smsService = smsService;
    }
}
