package com.wechat.program.app.exception;

import com.wechat.program.app.core.AjaxResult;
//import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常处理器
 */
@RestControllerAdvice
public class BDExceptionHandler {
//

    /**
     * 自定义异常
     */
    @ExceptionHandler(ProgramException.class)
    public AjaxResult handleBDException(ProgramException e) {
        return AjaxResult.failed(e.getMessage());
    }
//
//    @ExceptionHandler(DuplicateKeyException.class)
//    public R handleDuplicateKeyException(DuplicateKeyException e) {
//        logger.error(e.getMessage(), e);
//        return R.error("数据库中已存在该记录");
//    }
//
//    @ExceptionHandler(org.springframework.web.servlet.NoHandlerFoundException.class)
//    public R noHandlerFoundException(org.springframework.web.servlet.NoHandlerFoundException e) {
//        logger.error(e.getMessage(), e);
//        return R.error(404, "没找找到页面");
//    }

//    @ExceptionHandler(AuthorizationException.class)
//    public AjaxResult handleAuthorizationException(AuthorizationException e, HttpServletRequest request, HttpServletResponse response) {
////        logger.error(e.getMessage(), e);
////        if (HttpServletUtils.jsAjax(request)) {
////            return R.error(403, "未授权");
////        }
//        return AjaxResult.failed("权限不足！", "403");
//    }


}
