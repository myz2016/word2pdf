package com.fotic.it.support.word2pdf.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: mfh
 * @Date: 2019-05-06 14:51
 **/
@Configuration
@ConfigurationProperties(prefix = "path")
public class PathConfig {
    private String nas;
    private String wordToPdfLocalPathXin;
    public String getNas() {
        return nas;
    }
    public void setNas(String nas) {
        this.nas = nas;
    }

    public String getWordToPdfLocalPathXin() {
        return wordToPdfLocalPathXin;
    }

    public void setWordToPdfLocalPathXin(String wordToPdfLocalPathXin) {
        this.wordToPdfLocalPathXin = wordToPdfLocalPathXin;
    }
}
