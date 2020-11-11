package com.wechat.program.app.controller;


import com.wechat.program.app.core.AjaxResult;
import com.wechat.program.app.request.AppUserDTO;
import com.wechat.program.app.request.UserLoginDTO;
import com.wechat.program.app.service.AppUserService;
import com.wechat.program.app.utils.SHAUtil;
import com.wechat.program.app.utils.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api( tags = {"登录"})
@RestController
public class LoginController {

    private AppUserService appUserService;


    @ApiOperation("登录接口")
    @PostMapping("/login")
    public AjaxResult login(@RequestBody UserLoginDTO dto) {
        if (StringUtils.isEmpty(dto.getPhone()) || StringUtils.isEmpty(dto.getPassword())) {
            return AjaxResult.failed("请输入用户名和密码！");
        }
        String password = SHAUtil.SHA256(dto.getPassword());
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                dto.getPhone(),
                password
        );
        try {
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(usernamePasswordToken);
//            subject.checkRole("admin");
//            subject.checkPermissions("query", "add");
        } catch (UnknownAccountException e) {

            return AjaxResult.failed("用户名不存在！");
        } catch (AuthenticationException e) {
            return AjaxResult.failed("账号或密码错误！");
        } catch (AuthorizationException e) {
            return AjaxResult.failed("没有权限！");
        }
        return AjaxResult.success();
    }

    @GetMapping("/logout")
    public String logout() {
        ShiroUtils.logout();
        return "redirect:/login";
    }


    @Autowired
    public void setAppUserService(AppUserService appUserService) {
        this.appUserService = appUserService;
    }
}
