package com.fotic.it.support.word2pdf.dao.entity;

/**
 * 老接口
 */
public class SignConfigurationInfoEntity {
    private String dicTypeId;
    private String sealServeType;
    private String sealServeVersion;
    private String sealServePath;
    private String ftpAddress;
    private Integer ftpPort;
    private String ftpUser;
    private String ftpPwd;
    private String wordPdUrlRt;
    private String wordPdUrlXpt;

    public String getWordPdUrlRt() {
        return wordPdUrlRt;
    }

    public void setWordPdUrlRt(String wordPdUrlRt) {
        this.wordPdUrlRt = wordPdUrlRt;
    }

    public String getWordPdUrlXpt() {
        return wordPdUrlXpt;
    }

    public void setWordPdUrlXpt(String wordPdUrlXpt) {
        this.wordPdUrlXpt = wordPdUrlXpt;
    }

    public String getSealServeType() {
        return sealServeType;
    }

    public void setSealServeType(String sealServeType) {
        this.sealServeType = sealServeType;
    }

    public String getSealServeVersion() {
        return sealServeVersion;
    }

    public void setSealServeVersion(String sealServeVersion) {
        this.sealServeVersion = sealServeVersion;
    }

    public String getSealServePath() {
        return sealServePath;
    }

    public void setSealServePath(String sealServePath) {
        this.sealServePath = sealServePath;
    }

    public String getFtpAddress() {
        return ftpAddress;
    }

    public void setFtpAddress(String ftpAddress) {
        this.ftpAddress = ftpAddress;
    }

    public Integer getFtpPort() {
        return ftpPort;
    }

    public void setFtpPort(Integer ftpPort) {
        this.ftpPort = ftpPort;
    }

    public String getFtpUser() {
        return ftpUser;
    }

    public void setFtpUser(String ftpUser) {
        this.ftpUser = ftpUser;
    }

    public String getFtpPwd() {
        return ftpPwd;
    }

    public void setFtpPwd(String ftpPwd) {
        this.ftpPwd = ftpPwd;
    }

    public String getDicTypeId() {
        return dicTypeId;
    }

    public void setDicTypeId(String dicTypeId) {
        this.dicTypeId = dicTypeId;
    }


    @Override
    public String toString() {
        return "SignConfigurationInfoEntity{" +
                "dicTypeId='" + dicTypeId + '\'' +
                ", sealServeType='" + sealServeType + '\'' +
                ", sealServeVersion='" + sealServeVersion + '\'' +
                ", sealServePath='" + sealServePath + '\'' +
                ", ftpAddress='" + ftpAddress + '\'' +
                ", ftpPort=" + ftpPort +
                ", ftpUser='" + ftpUser + '\'' +
                ", ftpPwd='" + ftpPwd + '\'' +
                ", wordPdUrlRt='" + wordPdUrlRt + '\'' +
                ", wordPdUrlXpt='" + wordPdUrlXpt + '\'' +
                '}';
    }
}
