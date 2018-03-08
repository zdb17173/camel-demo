package org.fran.cameldemo.spring.test;

import org.apache.camel.CamelContext;
import org.apache.camel.Message;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.direct.DirectEndpoint;
import org.apache.camel.component.http.HttpComponent;
import org.apache.camel.component.jetty9.JettyHttpComponent9;
import org.apache.camel.http.common.HttpMessage;
import org.apache.camel.impl.DefaultCamelContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: fran
 * @Date: Created on 2018/3/7
 */
public class TestCamelMain {

    public String test(Bean body){
        System.out.println(body);
        return "fufufu";
    }



    public static void main(String[] args) throws Exception {

        TestCamelMain b = new TestCamelMain();

        RouteBuilder builder = new RouteBuilder() {
            public void configure() {
                from("direct:a")
                        .id("aaa")
                        .transform()
                        .body((a,b)->{
                            Bean xx = new Bean();
                            xx.setB(a.toString());
                            return xx;
                        })
                        .bean(b, "test")
                        .transform()
                        .body((a,b)->{
                            System.out.println(a);
                            return null;
                        }).end();
                        /*.process((ex) -> {{
                            Message in = ex.getIn();
                            Bean bb = (Bean)in.getBody();
                            bb.setB(bb.getB() + "dsadasd");
                            bb.setA(11);
                            System.out.println(in);
                        }});*/

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

            private Object test(Object o, Object oo) {
                return null;
            }
        };
        final CamelContext myCamelContext = new DefaultCamelContext();
        myCamelContext.addComponent("jetty", new JettyHttpComponent9());
        myCamelContext.addComponent("http", new HttpComponent());
        myCamelContext.addRoutes(builder);
        myCamelContext.start();
        System.out.println("started");
        ProducerTemplate temp = myCamelContext.createProducerTemplate();

        temp.sendBody("direct:a", "dsadasd");

        /*Thread t = new Thread(new Runnable() {
            @Override
            public void run() {


                for(int i = 0; i < 100000; i ++) {
                    Bean bb = new Bean();
                    bb.setB("222222222");
                    temp.sendBody("direct:a", bb);
                    System.out.println(bb.getB() + "" + i);
                }
            }
        });
        t.start();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 100000; i ++) {
                    Bean bb = new Bean();
                    bb.setB("11111111");
                    temp.sendBody("direct:a", bb);
                    System.out.println(bb.getB() + "" + i);
                }
            }
        });
        t1.start();*/


        System.out.println("");
        Thread.sleep(100000);
    }


    private static class Bean{
        int a = 0;
        String b = "";

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }
    }
}
