package org.fran.cameldemo.camelspringboot.service.impl;

import org.fran.cameldemo.camelspringboot.anno.FtlMethod;
import org.fran.cameldemo.camelspringboot.anno.FtlService;
import org.fran.cameldemo.camelspringboot.service.TestService;
import org.fran.cameldemo.camelspringboot.vo.BaseRequestPager;
import org.fran.cameldemo.camelspringboot.vo.ServiceFtlResultVO;
import org.springframework.stereotype.Service;

/**
 * @Author: fran
 * @Date: Created on 2018/3/7
 */
@FtlService(getPath = "test")
@Service
public class TestServiceImpl implements TestService {

    @Override
    @FtlMethod(path = "test1", parameterType = BaseRequestPager.class)
    public void test1(ServiceFtlResultVO<BaseRequestPager> res){
        BaseRequestPager req = res.getRequest();
        String page = req.getCurPage();
        String pageSize = req.getPageSize();
        res.setResult("test1 page["+ page +"] ["+ pageSize +"]");
    }

    @Override
    @FtlMethod(path = "test2", parameterType = BaseRequestPager.class)
    public void test2(ServiceFtlResultVO<BaseRequestPager> res){
        BaseRequestPager req = res.getRequest();
        String page = req.getCurPage();
        String pageSize = req.getPageSize();
        res.setResult("test2 page["+ page +"] ["+ pageSize +"]");
    }

}
