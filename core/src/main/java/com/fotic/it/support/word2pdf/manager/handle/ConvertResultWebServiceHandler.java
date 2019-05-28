package com.fotic.it.support.word2pdf.manager.handle;

import com.fotic.it.support.word2pdf.dao.mapper.EosDictEntryMapper;
import com.fotic.it.support.word2pdf.exception.ResultStatusCodeEnum;
import com.fotic.it.support.word2pdf.manager.model.WordPdfResultDto;
import com.fotic.it.support.word2pdf.manager.webservice.IWordPDfResultWebService;
import com.fotic.it.support.word2pdf.manager.webservice.IWordPDfResultWebServiceNewPortal;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: mfh
 * @Date: 2019-04-29 19:49
 **/

public class ConvertResultWebServiceHandler {
    // TODO 对于webservice的调用，此类可以作为抽象类
    private static Logger logger = LoggerFactory.getLogger(ConvertResultWebServiceHandler.class);
    /**
     * 换行符标准姿势
     */
    private static final String lineSeparator = System.getProperty("line.separator");
    private PathHandler processor;
    private int convertState;
    private EosDictEntryMapper mapper;

    public ConvertResultWebServiceHandler(PathHandler processor, EosDictEntryMapper mapper, int convertState) {
        this.processor = processor;
        this.mapper = mapper;
        this.convertState = convertState;
    }
    public void handleIWordPDfResultWebService() {
        //TODO 与 handleIWordPDfResultWebServiceNewPortal 相似度较高，可考虑复用
        logger.info("回调软通系统的 webservice：准备开始将 word 转 pdf 的执行结果通过 webservice 发送给软通系统");
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        addLogInterceptor(jaxWsProxyFactoryBean);
        jaxWsProxyFactoryBean.setAddress(getWebServiceUrl());
        jaxWsProxyFactoryBean.setServiceClass(IWordPDfResultWebService.class);
        IWordPDfResultWebService service = jaxWsProxyFactoryBean.create(IWordPDfResultWebService.class);
        service.getWordPDFResult(createWordPdfResult(processor.getId(), convertState));
    }

    public void handleIWordPDfResultWebServiceNewPortal() {
        logger.info("回调新平台系统的 webservice：准备开始将 word 转 pdf 的执行结果通过 webservice 发送给新平台系统");
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        addLogInterceptor(jaxWsProxyFactoryBean);
        jaxWsProxyFactoryBean.setAddress(getWebServiceUrl());
        jaxWsProxyFactoryBean.setServiceClass(IWordPDfResultWebServiceNewPortal.class);
        IWordPDfResultWebServiceNewPortal service = jaxWsProxyFactoryBean.create(IWordPDfResultWebServiceNewPortal.class);
        service.getWordPDFResult(createWordPdfResult(processor.getId(), convertState));
    }
    private String getWebServiceUrl() {
        String url;
        if (processor.isRtSystem()) {
            url = getRtWebServiceUrl();
            logger.info("要回调的 {} 系统的 webservice 路径：{}", "软通", url);
        } else {
            url = getNewPortalServiceUrl();
            logger.info("要回调的 {} 系统的 webservice 路径：{}", "新平台", url);
        }
        return url;
    }

    /**
     * 获取软通 webservice url
     *
     * @return
     */
    private String getRtWebServiceUrl() {
        logger.info("获取要回调的软通的 webservice 路径");
        return this.mapper.getDictName("signature_ftp_info_wordpdf", "wordpdf_url_rt");
    }

    /**
     * 获取新平台 webservice url
     *
     * @return
     */
    private String getNewPortalServiceUrl() {
        logger.info("获取要回调的新平台的 webservice url");
        return this.mapper.getDictName("signature_ftp_info_wordpdf", "wordpdf_url_xin");
    }

    private WordPdfResultDto createWordPdfResult(String id, int convertState) {
        logger.info("创建 webservice 要返回的转换结果实体");
        WordPdfResultDto resultDto = new WordPdfResultDto();
        resultDto.setId(id);
        resultDto.setResultType(String.valueOf(convertState));
        resultDto.setMessage(ResultStatusCodeEnum.getDescription(convertState));
        logger.info("转换结果实体创建完成：" + resultDto);
        return resultDto;
    }
    private void addLogInterceptor(JaxWsProxyFactoryBean jaxWsProxyFactoryBean) {
        jaxWsProxyFactoryBean.getInInterceptors().add(new LoggingOutInterceptor());
        jaxWsProxyFactoryBean.getInInterceptors().add(new LoggingInInterceptor());
    }
    public String getWordToPdfLocalPathXin() {
       return this.processor.getWordToPdfLocalPathXin();
    }

}
