package com.fotic.it.support.word2pdf.exception.commons;

/**
 * @Author: mfh
 * @Date: 2019-05-14 21:15
 **/
public class BusinessException extends RuntimeException {
    private String errorCode;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public BusinessException(String errorCode, String errorMsg, Throwable cause){
        super(errorMsg, cause);
        this.errorCode = errorCode;
    }

    public BusinessException(String errMsg, Throwable cause){
        super(errMsg, cause);
    }

    public BusinessException(String errorCode, String errMsg){
        super(errMsg);
        this.errorCode = errorCode;
    }

    public BusinessException(String errMsg){
        super(errMsg);
    }
}
