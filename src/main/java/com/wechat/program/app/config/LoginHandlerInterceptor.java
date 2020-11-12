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


public class LoginHandlerInterceptor implements HandlerInterceptor {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getRequestURI().equals("/login"))return true;
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            returnJson(response, "token不能为空！");
            return false;

        }
        AppUserService appUserService = SpringContextHolder.getBean(AppUserService.class);
        if (!appUserService.selectByToken(token)) {
            returnJson(response, "恶意攻击！");
            return false;
        }
        return true;
    }

    private void returnJson(HttpServletResponse response, String message){
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            writer = response.getWriter();
            Map<String, Object> result = new HashMap<>();
            result.put("data", null);
            result.put("message", message);
            result.put("code", 500);
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
