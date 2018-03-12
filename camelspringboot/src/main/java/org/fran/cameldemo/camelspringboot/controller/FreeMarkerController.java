package org.fran.cameldemo.camelspringboot.controller;

import org.apache.camel.ProducerTemplate;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.fran.cameldemo.camelspringboot.config.DynamicConfig;
import org.fran.cameldemo.camelspringboot.vo.ServiceFtlResultVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fran on 2018/1/22.
 */
@Controller
@RequestMapping("/l")
public class FreeMarkerController {

    @Resource
    DynamicConfig dynamicConfig;
    @Resource
    ProducerTemplate producerTemplate;

    @RequestMapping("/{service}/{method}/{template}")
    public ModelAndView selectTest(
            HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable(name = "service") String service,
            @PathVariable(name = "method") String method,
            @PathVariable(name = "template") String template){

        Enumeration<String> ns = request.getParameterNames();
        Map<String, Object> values = new HashMap<>();
        while(ns.hasMoreElements()) {
            String param = ns.nextElement();
            values.put(param, request.getParameter(param));
        }

        ServiceFtlResultVO resService = new ServiceFtlResultVO(values);
        producerTemplate.sendBody("direct:" + service + "/" + method, resService);

        Map<String, Object> map = new HashMap<>();
        map.put("data", resService.getResult());
        map.put("configs", dynamicConfig.getConfigs());
        return new ModelAndView("/" + service + "/" + template, map);
    }
}
