package com.disarch.web.controller;

import com.disarch.entity.UserSession;
import com.disarch.service.order.IOrderService;
import com.disarch.web.common.Constans;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Resource
    private IOrderService orderService;

    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        UserSession userSession = getSession(request, response);
        Preconditions.checkNotNull(userSession, Constans.USER_SESSION_EXPIRED);
        model.put("userId", userSession.getUserId());
        return new ModelAndView("order/index").addAllObjects(model);
    }
}
