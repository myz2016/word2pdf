package com.fotic.it.support.word2pdf.exception;

import com.fotic.it.support.word2pdf.exception.commons.BusinessException;
import com.fotic.it.support.word2pdf.exception.commons.IllegalEndPathException;
import com.fotic.it.support.word2pdf.util.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @Author: mfh
 * @Date: 2019-05-13 15:25
 **/
@ControllerAdvice(basePackages = "com.fotic.it.support.word2pdf.api")
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public String businessExceptionHandler(BusinessException e) {
        logger.error("业务异常:{}", e.getMessage());
        return this.resultOfFail();
    }

    @ExceptionHandler(IllegalEndPathException.class)
    @ResponseBody
    public String illegalEndPathExceptionHandler(IllegalEndPathException e) {
        logger.error("非法路径结尾异常: {}", e.getMessage());
        return this.resultOfFail();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public String nullPointExceptionHandler(RuntimeException e) {
        logger.error("空指针异常: {}", e.getMessage());
        return this.resultOfFail();
    }

    private String resultOfFail() {
        return ExceptionUtil.resultOf(ResultStatusCodeEnum.FAIL);
    }
}
