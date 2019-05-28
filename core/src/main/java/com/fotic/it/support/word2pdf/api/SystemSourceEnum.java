package com.fotic.it.support.word2pdf.api;

/**
 * @Author: mfh
 * @Date: 2019-05-27 16:25
 **/
public enum SystemSourceEnum {
    RT_CONTRACT("软通-合同"),
    RT_INFO_DISCLOSURE("软通-信息披露"),
    NEW_PORTAL("新平台"),;
    private String name;

    SystemSourceEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
