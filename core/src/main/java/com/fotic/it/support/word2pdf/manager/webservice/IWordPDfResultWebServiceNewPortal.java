package com.fotic.it.support.word2pdf.manager.webservice;

import com.fotic.it.support.word2pdf.manager.model.WordPdfResultDto;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * 新平台专用 webservice 老接口
 */
@WebService(targetNamespace = "http://www.primeton.com/IWordPDfResultWebService", name = "IWordPDfResultWebService")
public interface IWordPDfResultWebServiceNewPortal {
    /**
     * 获取转换结果
     * @param wordPdfResultDto 转换结果实体对象
     */
    @WebMethod
    void getWordPDFResult(@WebParam(name = "in0", targetNamespace = "http://www.primeton.com/IWordPDfResultWebService") WordPdfResultDto wordPdfResultDto);


    @WebMethod
    @WebResult(name="resultMsg")
    int getWordPDFResultForContract(@WebParam(name = "wordPdfResultDto") WordPdfResultDto wordPdfResultDto);
}
