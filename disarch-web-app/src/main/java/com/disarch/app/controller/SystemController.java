package com.disarch.app.controller;

import com.disarch.app.common.CommonActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class SystemController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemController.class);

    @RequestMapping(value = "/error.htm")
    public void error(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        ajaxResponse(response, CommonActionResult.SYSTEM_EXCEPTION.getStatus(), CommonActionResult.SYSTEM_EXCEPTION.getMsg(), model);
    }
}
