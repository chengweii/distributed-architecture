package com.disarch.app.util;

import com.disarch.util.MD5Utils;
import com.disarch.util.RegexUtils;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

public class ApiValidateUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiValidateUtil.class);

    private static final long API_REQUEST_TIMEOUT = 60 * 1000;
    private static final String PARTNER_SECURITY_KEY = "disarch";

    /**
     * 验证请求参数签名合法性
     *
     * @param request
     * @return
     */
    public static String validateRequestParams(HttpServletRequest request) {
        Map<String, String> requestParams = getSimpleRequestParamMap(request);
        Long nowTime = new Date().getTime() / 1000;
        String errorMsg = "";
        if (null == requestParams) {
            errorMsg = "参数不能为空";
        } else if (StringUtils.isBlank(requestParams.get("sign"))) {
            errorMsg = "签名不能为空";
        } else if (StringUtils.isBlank(requestParams.get("time"))) {
            errorMsg = "时间不能为空";
        } else if (RegexUtils.check(requestParams.get("time"), RegexUtils.CheckType.NUMBER) && API_REQUEST_TIMEOUT < (nowTime - Long.parseLong(requestParams.get("time")))) {
            errorMsg = "请求已过期";
        } else {
            String sign = requestParams.get("sign");
            Map<String, String> requestParamsNew = new HashMap<String, String>();

            for (String key : requestParams.keySet()) {
                requestParamsNew.put(key.toLowerCase().replaceAll("_", ""), requestParams.get(key));
            }

            List<String> params = new ArrayList<String>();
            for (String key : requestParamsNew.keySet()) {
                if ("sign".equals(key)) {
                    continue;
                }
                params.add(key);
            }

            Collections.sort(params);

            StringBuilder sb = new StringBuilder();
            for (String paramKey : params) {
                sb.append(paramKey).append("=").append(requestParamsNew.get(paramKey)).append("&");
            }
            String encodeStr = sb.substring(0, sb.length() - 1);
            try {
                encodeStr = URLEncoder.encode(encodeStr.replaceAll("\\(", "").replaceAll("\\)", ""), "UTF-8").toLowerCase();
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("字符串URL编码出错：", e);
            }

            String maySign = MD5Utils.getMD5String(PARTNER_SECURITY_KEY + MD5Utils.getMD5String(encodeStr));
            if (!sign.equals(maySign)) {
                errorMsg = "签名验证失败";
            }
        }

        if (Strings.isNullOrEmpty(errorMsg))
            return errorMsg;

        LOGGER.error("请求[" + request.getRequestURI() + "]方法时，方法签名验证失败，请求参数：[" + requestParams + "]，失败信息：" + errorMsg);
        return errorMsg;
    }

    private static Map<String, String> getSimpleRequestParamMap(HttpServletRequest request) {
        @SuppressWarnings("unchecked")
        Map<String, String[]> srcParamMap = request.getParameterMap();
        Map<String, String> paramMap = new HashMap<String, String>();
        for (String key : srcParamMap.keySet()) {
            if (srcParamMap.get(key).length == 1) {
                String value = srcParamMap.get(key)[0];
                paramMap.put(key, StringUtils.trimToEmpty(value));
            } else {
                paramMap.put(key, String.valueOf(srcParamMap.get(key)));
            }
        }
        return paramMap;
    }
}
