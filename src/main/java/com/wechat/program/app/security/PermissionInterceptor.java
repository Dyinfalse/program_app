//package com.wechat.program.app.security;
//
//import com.wechat.program.app.service.AppPermissionService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//public class PermissionInterceptor implements HandlerInterceptor {
//
//
//    @Autowired
//    private AppPermissionService appPermissionService;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if(checkPermission(request, response, handler)) {
//            return true;
//        }
//        response.sendError(HttpServletResponse.SC_FORBIDDEN, "没有权限");
//        return false;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//
//    }
//
//    private boolean checkPermission(HttpServletRequest request, HttpServletResponse response, Object handler) {
//        AccountSet accountSet = accountSetHolder.getCurrentAccountSet();
//        if (handler instanceof HandlerMethod) {
//            HandlerMethod handlerMethod = (HandlerMethod) handler;
//            // 首先获取控制器方法上的权限注解
//            RequiredPermission requiredPermission = handlerMethod.getMethod().getAnnotation(RequiredPermission.class);
//            // 如果方法上没有权限注解，则获取类上的权限注解
//            if (requiredPermission == null) {
//                requiredPermission = handlerMethod.getMethod().getDeclaringClass().getAnnotation(RequiredPermission.class);
//            }
//            EnterpriseUser user = authenticationFacade.getUser();
//            // 如果标记了注解，则判断权限
//            if (requiredPermission != null && StringUtils.isNotBlank(requiredPermission.value())) {
//                return permissionService.validatePermissionCodeExist(user, requiredPermission.value(), accountSet);
//            }
//            if(requiredPermission != null && requiredPermission.values().length > 0) {
//                boolean hasPermission = false;
//                for(String p : requiredPermission.values()) {
//                    hasPermission = permissionService.validatePermissionCodeExist(user, p, accountSet);
//                    if(hasPermission) break;
//                }
//                return hasPermission;
//            }
//        }
//        return true;
//    }
//}
