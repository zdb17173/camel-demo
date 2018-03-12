package org.fran.cameldemo.camelspringboot.vo;

/**
 * @Author: fran
 * @Date: Created on 2018/3/9
 */
public class BaseRequestPager implements BaseRequest{
    String curPage;
    String pageSize;

    public String getCurPage() {
        return curPage;
    }

    public void setCurPage(String curPage) {
        this.curPage = curPage;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }
}
