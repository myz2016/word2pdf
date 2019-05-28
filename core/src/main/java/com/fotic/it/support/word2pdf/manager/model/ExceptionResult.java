package com.fotic.it.support.word2pdf.manager.model;

/**
 * @Author: mfh
 * @Date: 2019-05-15 9:37
 **/
public class ExceptionResult<T> {
    private Integer code;
    private String url;
    private String simple;
    private T detail;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSimple() {
        return simple;
    }

    public void setSimple(String simple) {
        this.simple = simple;
    }

    public T getDetail() {
        return detail;
    }

    public void setDetail(T detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "ExceptionResult{" +
                "code=" + code +
                ", url='" + url + '\'' +
                ", simple='" + simple + '\'' +
                ", detail=" + detail +
                '}';
    }
}
