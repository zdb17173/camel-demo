package org.fran.cameldemo.camelspringboot.controller;

import org.apache.camel.ProducerTemplate;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.fran.cameldemo.camelspringboot.config.DynamicConfig;
import org.fran.cameldemo.camelspringboot.config.WebSiteConfig;
import org.fran.cameldemo.camelspringboot.vo.ServiceFtlResultVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
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
    WebSiteConfig webSiteConfig;
    @Resource
    ProducerTemplate producerTemplate;

    @RequestMapping("/{address}")
    public ModelAndView selectTest(
            HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable(name = "address") String address){

        Enumeration<String> ns = request.getParameterNames();
        Map<String, Object> values = new HashMap<>();
        while(ns.hasMoreElements()) {
            String param = ns.nextElement();
            values.put(param, request.getParameter(param));
        }

        Map<String, WebSiteConfig.Route> routes = webSiteConfig.getRoutes();
        WebSiteConfig.Route route = routes.get(address);
        if(route != null){
            String service = route.getService();
            Map<String, Object> param = route.getParameters();
            Map<String, Object> reqMap = null;
            if(param == null)
                reqMap = new HashMap<>();
            else
                reqMap = new HashMap<>(param);
            if(values.size() > 0)
                reqMap.putAll(values);

            String template = route.getTemplate();
            ServiceFtlResultVO resService = new ServiceFtlResultVO(reqMap);
            producerTemplate.sendBody("direct:" + service, resService);

            Map<String, Object> map = new HashMap<>();
            map.put("data", resService.getResult());
            map.put("configs", dynamicConfig.getConfigs());
            return new ModelAndView(template, map);
        }else {
            Map<String, Object> map = new HashMap<>();
            return new ModelAndView("/404", map);
        }
    }
}
