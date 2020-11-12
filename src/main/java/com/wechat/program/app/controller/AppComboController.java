package com.wechat.program.app.controller;

import com.wechat.program.app.core.AjaxResult;
import com.wechat.program.app.entity.AppCombo;
import com.wechat.program.app.request.AppComboDTO;
import com.wechat.program.app.service.AppComboService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api( tags = {"用户选择的套餐"})
@RestController
@RequestMapping("/app-combo")
public class AppComboController {


    private AppComboService appComboService;

    @ApiOperation("添加套餐")
    @PostMapping("/add")
    public AjaxResult add(@RequestBody AppComboDTO dto) {
        AppCombo combo = appComboService.add(dto);
        return AjaxResult.success(combo);
    }

    @ApiOperation("套餐列表")
    @GetMapping("/list")
    public AjaxResult list() {
        return AjaxResult.success(appComboService.selectAll());
    }



    @Autowired
    public void setAppComboService(AppComboService appComboService) {
        this.appComboService = appComboService;
    }
}
