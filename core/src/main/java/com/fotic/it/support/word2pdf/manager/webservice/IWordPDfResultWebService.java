package com.fotic.it.support.word2pdf.manager.webservice;

import com.fotic.it.support.word2pdf.manager.model.WordPdfResultDto;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * 专用 webservice 老接口
 */
@WebService(targetNamespace = "http://webservice.iss.com")
public interface IWordPDfResultWebService {
    /**
     * 获取转换结果
     * @param wordPdfResultDto 转换结果实体对象
     */
    @WebMethod
    void getWordPDFResult(@WebParam(name = "wordPdfResultDto") WordPdfResultDto wordPdfResultDto);

    @WebMethod
    @WebResult(name="resultMsg")
    int getWordPDFResultForContract(@WebParam(name = "wordPdfResultDto") WordPdfResultDto wordPdfResultDto);
}
