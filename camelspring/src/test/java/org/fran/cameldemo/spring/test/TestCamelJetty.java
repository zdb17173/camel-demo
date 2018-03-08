package org.fran.cameldemo.spring.test;

import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http.HttpComponent;
import org.apache.camel.component.jetty9.JettyHttpComponent9;
import org.apache.camel.http.common.HttpMessage;
import org.apache.camel.impl.DefaultCamelContext;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Author: fran
 * @Date: Created on 2018/3/7
 */
public class TestCamelJetty {

    public void test(String body){
        System.out.println(body);
    }

    public static void main(String[] args) throws Exception {

        TestCamelJetty b = new TestCamelJetty();

        RouteBuilder builder = new RouteBuilder() {
            public void configure() {
                from("jetty://http://127.0.0.1:8080/cc")
                        .id("server3")
                        .process((ex) -> {
                            HttpMessage in = (HttpMessage)ex.getIn();
                            HttpServletRequest req = in.getRequest();
                            Enumeration<String> ns = req.getParameterNames();
                            Map<String, Object> v = new HashMap<>();
                            while(ns.hasMoreElements()) {
                                String param = ns.nextElement();
                                v.put(param, req.getParameter(param));
                            }
                            System.out.println(req.getPathInfo() + ":" + v);
                        })
                ;

                from("jetty://http://127.0.0.1:8080/bb")
                        .id("server2")
                        .process((ex) -> {
                            HttpMessage in = (HttpMessage)ex.getIn();
                            HttpServletRequest req = in.getRequest();
                            Enumeration<String> ns = req.getParameterNames();
                            Map<String, Object> v = new HashMap<>();
                            while(ns.hasMoreElements()) {
                                String param = ns.nextElement();
                                v.put(param, req.getParameter(param));
                            }
                            System.out.println(req.getPathInfo() + ":" + v);
                        });

                from("jetty://http://127.0.0.1:8080/aa")
                        .id("server1")
                        .process((ex) -> {
                            HttpMessage in = (HttpMessage)ex.getIn();
                            HttpServletRequest req = in.getRequest();
                            Enumeration<String> ns = req.getParameterNames();
                            Map<String, Object> v = new HashMap<>();
                            while(ns.hasMoreElements()) {
                                String param = ns.nextElement();
                                v.put(param, req.getParameter(param));
                            }
                            System.out.println(req.getPathInfo() + ":" + v);
                        });

                /*from("direct:a")
                        .id("aaa")
                        .bean(b);*/

                /*from("direct:a")
                        .transform()
                        .exchange((ex) -> {
                            return "dasdadsa";
                        })
                        .process((ex) -> {{
                            Message in = ex.getIn();
                            System.out.println(in);
                        }});*/
            }
        };
        final CamelContext myCamelContext = new DefaultCamelContext();
        myCamelContext.addComponent("jetty", new JettyHttpComponent9());
        myCamelContext.addComponent("http", new HttpComponent());
        myCamelContext.addRoutes(builder);
        myCamelContext.start();
        System.out.println("started");

        System.out.println("");
    }
}
