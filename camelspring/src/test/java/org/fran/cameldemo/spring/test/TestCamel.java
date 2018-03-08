package org.fran.cameldemo.spring.test;

import org.apache.camel.*;
import org.apache.camel.main.Main;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Objects;

/**
 * @Author: fran
 * @Date: Created on 2018/3/7
 */
/**
 * @Author: fran
 * @Date: Created on 2018/3/7
 */
@ContextConfiguration(locations = "classpath*:applicationContext-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestCamel extends AbstractJUnit4SpringContextTests {

    @Test
    public void test(){

    }
}
