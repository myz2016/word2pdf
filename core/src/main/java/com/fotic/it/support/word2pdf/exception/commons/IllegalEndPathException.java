package com.fotic.it.support.word2pdf.exception.commons;

/**
 * @Author: mfh
 * @Date: 2019-05-16 17:09
 **/
public class IllegalEndPathException extends BusinessException {
    public IllegalEndPathException(String errorCode, String errorMsg, Throwable cause) {
        super(errorCode, errorMsg, cause);
    }

    public IllegalEndPathException(String errMsg, Throwable cause) {
        super(errMsg, cause);
    }

    public IllegalEndPathException(String errorCode, String errMsg) {
        super(errorCode, errMsg);
    }


    public IllegalEndPathException(String errMsg) {
        super(errMsg);
    }
}
