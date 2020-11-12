package com.wechat.program.app.config;

import com.wechat.program.app.service.AppUserService;
import com.wechat.program.app.support.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static com.wechat.program.app.constant.PermissionConstant.CONSUMPTION_STATISTICS;


public class LoginHandlerInterceptor implements HandlerInterceptor {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        if (uri.contains("/login") || uri.contains("error")||uri.contains("swagger"))return true;
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            returnJson(response, "token不能为空！", "500");
            return false;

        }
        AppUserService appUserService = SpringContextHolder.getBean(AppUserService.class);
        if (!appUserService.selectByToken(token)) {
            returnJson(response, "恶意攻击！", "500");
            return false;
        }
        if (request.getRequestURI().equals("/app-user/statistics")) {
            Integer count  = appUserService.selectByTokenAndPermissionCode(token, CONSUMPTION_STATISTICS);
            if (count < 1) {
                returnJson(response, "权限不足！", "403");
                return false;
            }
        }
        return true;
    }

    private void returnJson(HttpServletResponse response, String message, String code){
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            writer = response.getWriter();
            Map<String, Object> result = new HashMap<>();
            result.put("data", null);
            result.put("message", message);
            result.put("code", code);
            writer.print(result);
        } catch (IOException e){
            logger.error(e.getMessage());
        } finally {
            if(writer != null){
                writer.close();
            }
        }
    }

}
