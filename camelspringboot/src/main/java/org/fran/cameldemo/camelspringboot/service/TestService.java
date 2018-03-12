package org.fran.cameldemo.camelspringboot.service;

import org.fran.cameldemo.camelspringboot.vo.BaseRequestPager;
import org.fran.cameldemo.camelspringboot.vo.ServiceFtlResultVO;

/**
 * @Author: fran
 * @Date: Created on 2018/3/12
 */
public interface TestService {
    public void test1(ServiceFtlResultVO<BaseRequestPager> res);
    public void test2(ServiceFtlResultVO<BaseRequestPager> res);
}
