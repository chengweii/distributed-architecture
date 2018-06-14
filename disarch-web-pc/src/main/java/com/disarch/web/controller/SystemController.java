package com.disarch.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class SystemController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemController.class);

    @RequestMapping(value = "/error.htm")
    public ModelAndView error(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        return new ModelAndView("error/error").addAllObjects(model);
    }
}
