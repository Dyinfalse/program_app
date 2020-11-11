package com.wechat.program.app.config;

import com.wechat.program.app.entity.AppPermission;
import com.wechat.program.app.entity.AppRole;
import com.wechat.program.app.exception.ProgramException;
import com.wechat.program.app.service.AppUserService;
import com.wechat.program.app.vo.AppUserVo;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Objects;

public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private AppUserService loginService;

    /**
     * @MethodName doGetAuthorizationInfo
     * @Description 权限配置类
     * @Param [principalCollection]
     * @Return AuthorizationInfo
     * @Author WangShiLin
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户名
        String phone = principalCollection.getPrimaryPrincipal().toString();
        //查询用户名称
        AppUserVo vo = loginService.selectByShiroName(phone);
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (AppRole role : vo.getAppRoleList()) {
            //添加角色
            simpleAuthorizationInfo.addRole(role.getName());
            //添加权限
            for (AppPermission permissions : role.getAppPermissionList()) {
                simpleAuthorizationInfo.addStringPermission(permissions.getCode());
            }
        }
        return simpleAuthorizationInfo;
    }

    /**
     * @MethodName doGetAuthenticationInfo
     * @Description 认证配置类
     * @Param [authenticationToken]
     * @Return AuthenticationInfo
     * @Author WangShiLin
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (StringUtils.isEmpty(authenticationToken.getPrincipal())) {
            return null;
        }
        //获取用户信息
        String phone = authenticationToken.getPrincipal().toString();
        AppUserVo user = loginService.selectByShiroName(phone);
        if (Objects.isNull(user)) {
            throw new ProgramException("用户已下线！");
        } else {
            String password = new String((char[])authenticationToken.getCredentials());
            //这里验证authenticationToken和simpleAuthenticationInfo的信息
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(phone, password, getName());
            return simpleAuthenticationInfo;
        }
    }
}
