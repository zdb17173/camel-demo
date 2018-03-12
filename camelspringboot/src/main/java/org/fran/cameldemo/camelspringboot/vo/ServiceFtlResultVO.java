package org.fran.cameldemo.camelspringboot.vo;

/**
 * @Author: fran
 * @Date: Created on 2018/3/7
 */
public class ServiceFtlResultVO<T> {
    private int status;
    private Object result;
    private T request;
    private String description;

    public ServiceFtlResultVO(T request) {
        this.request = request;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public T getRequest() {
        return request;
    }

    public void setRequest(T request) {
        this.request = request;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}