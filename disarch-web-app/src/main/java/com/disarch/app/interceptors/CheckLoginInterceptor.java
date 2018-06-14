package com.disarch.app.interceptors;

import com.disarch.app.common.CommonActionResult;
import com.disarch.app.common.Constans;
import com.disarch.app.controller.BaseController;
import com.disarch.entity.UserSession;
import com.disarch.service.session.ISessionService;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 */
public class CheckLoginInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private ISessionService sessionService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String sessionId = request.getParameter(Constans.SESSION_ID_CACHE_KEY);
        UserSession session = sessionService.getSession(sessionId);
        if (session == null) {
            BaseController.ajaxResponse(response, CommonActionResult.USER_SESSION_EXPIRED.getStatus(), CommonActionResult.USER_SESSION_EXPIRED.getMsg(), null);
            return false;
        }
        return true;
    }
}
