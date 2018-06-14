package com.disarch.app.interceptors;

import com.disarch.app.common.CommonActionResult;
import com.disarch.app.controller.BaseController;
import com.disarch.app.util.ApiValidateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 方法签名验证拦截器
 */
public class CheckSignInterceptor extends HandlerInterceptorAdapter {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CheckSignInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        try {
            String errorMsg = ApiValidateUtil.validateRequestParams(request);
            if (StringUtils.isNotBlank(errorMsg)) {
                BaseController.ajaxResponse(response, CommonActionResult.VERIFY_SIGNATURE_FAILURE.getStatus(), CommonActionResult.VERIFY_SIGNATURE_FAILURE.getMsg() + errorMsg, null);
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("请求[" + request.getRequestURI() + "]方法时，程序出现异常", e);
            BaseController.ajaxResponse(response, CommonActionResult.VERIFY_SIGNATURE_FAILURE.getStatus(), CommonActionResult.VERIFY_SIGNATURE_FAILURE.getMsg() + "服务端异常", null);
            return false;
        }
        return true;
    }
}
