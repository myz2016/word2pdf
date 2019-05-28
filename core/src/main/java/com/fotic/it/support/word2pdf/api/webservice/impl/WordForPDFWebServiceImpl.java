package com.fotic.it.support.word2pdf.api.webservice.impl;

import com.fotic.it.support.word2pdf.api.SystemSourceEnum;
import com.fotic.it.support.word2pdf.api.webservice.WordForPDFWebService;
import com.fotic.it.support.word2pdf.config.PathConfig;
import com.fotic.it.support.word2pdf.dao.entity.SignConfigurationInfoEntity;
import com.fotic.it.support.word2pdf.dao.mapper.EosDictEntryMapper;
import com.fotic.it.support.word2pdf.exception.ResultStatusCodeEnum;
import com.fotic.it.support.word2pdf.manager.handle.PathHandler;
import com.fotic.it.support.word2pdf.manager.handle.ConvertHandler;
import com.fotic.it.support.word2pdf.manager.handle.ReqPathHandler;
import com.fotic.it.support.word2pdf.service.Word2PfdConvertService;
import com.fotic.it.support.word2pdf.service.Word2pdfInfoLogService;
import com.fotic.it.support.word2pdf.util.FtpFileOperator;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @Author: mfh
 * @Date: 2019-05-10 19:40
 **/
@javax.jws.WebService(serviceName = "WordForPDFWebService",
        targetNamespace = "http://xfire.webservice.com/WordForPDFWebService",
        endpointInterface = "com.fotic.it.support.word2pdf.api.webservice.WordForPDFWebService")
@Component
public class WordForPDFWebServiceImpl implements WordForPDFWebService {
    private static final Logger logger = LoggerFactory.getLogger(WordForPDFWebService.class);
    @Resource
    private EosDictEntryMapper eosDictEntryMapper;
    //TODO 为什么这里必须用 @Resource 注解，使用 @Autowire 就会出现空值？
    @Resource
    private PathConfig config;
    @Resource
    private ConvertHandler convertHandler;
    @Resource
    private Word2pdfInfoLogService word2pdfInfoLogService;

    @Resource
    private Word2PfdConvertService word2PfdConvertService;

    @Override
    public String CheckWordForPDF(String sys, String id, String inputPath, String inputFileName, String outputFileName) throws Exception {
        if (!StringUtils.isEmpty(inputPath) && !StringUtils.isEmpty(inputFileName) && !StringUtils.isEmpty(outputFileName)) {
            String space = " ";
            if (inputFileName.contains(space) || outputFileName.contains(space)) {
                word2pdfInfoLogService.recordLog(inputPath, inputFileName, outputFileName, ResultStatusCodeEnum.PARAMETER_EXIST_SPACE.getCode());
                return String.valueOf(ResultStatusCodeEnum.FAIL.getCode());
            } else {
                logger.info("新平台调用 webservice 接口 CheckWordForPDF：inputPath:{},inputFileName:{},outputFileName:{}", inputPath, inputFileName, outputFileName);
                String accessPath = paramsToJsonArray(inputPath, inputFileName, outputFileName);
                PathHandler reqPathHandler = new ReqPathHandler(config);
                reqPathHandler.parse(accessPath);
                if (reqPathHandler.isLegalInputPathEnd()) {
                    if (downFile(reqPathHandler.getInputPath(), reqPathHandler.getInputFileName())) {
                        /*convertHandler.convertWithListener(reqPathHandler.getWordToPdfLocalPathXin() + inputFileName,
                                reqPathHandler.getWordToPdfLocalPathXin() + "&" + id + "&" + sys + "&.pdf", SystemSourceEnum.NEW_PORTAL.getName());
                        logger.info("异步执行，转换任务已加入到任务池, 被转换的文件：" + reqPathHandler.getInputFileName());
                        return String.valueOf(ResultStatusCodeEnum.SUCCESS.getCode());*/
                        return word2PfdConvertService.executeConvertNewPortal(reqPathHandler.getWordToPdfLocalPathXin() + inputFileName,
                                reqPathHandler.getWordToPdfLocalPathXin() + "&" + id + "&" + sys + "&.pdf", SystemSourceEnum.NEW_PORTAL.getName());
                    } else {
                        return String.valueOf(ResultStatusCodeEnum.FAIL.getCode());
                    }
                } else {
                    word2pdfInfoLogService.recordLog(inputPath, inputFileName, outputFileName, ResultStatusCodeEnum.ILLEGAL_END_PATH.getCode());
                    logger.error(reqPathHandler.getInputFileName() + " 路径没有以 / 结尾");
                    return String.valueOf(ResultStatusCodeEnum.FAIL.getCode());
                }
            }
        }
        word2pdfInfoLogService.recordLog(inputPath, inputFileName, outputFileName, ResultStatusCodeEnum.ILLEGAL_PARAMETER.getCode());
        return String.valueOf(ResultStatusCodeEnum.FAIL.getCode());
    }

    @Override
    public String IstyleWordPDF(String inputPath, String inputFileName, String outputFileName) throws Exception {
        if (!StringUtils.isEmpty(inputPath) && !StringUtils.isEmpty(inputFileName) && !StringUtils.isEmpty(outputFileName)) {
            String space = " ";
            if (inputFileName.contains(space) || outputFileName.contains(space)) {
                word2pdfInfoLogService.recordLog(inputPath, inputFileName, outputFileName, ResultStatusCodeEnum.PARAMETER_EXIST_SPACE.getCode());
                return String.valueOf(ResultStatusCodeEnum.FAIL.getCode());
            } else {
                logger.info("新平台调用 webservice 接口 IstyleWordPDF：inputPath:{},inputFileName:{},outputFileName:{}", inputPath, inputFileName, outputFileName);
                String accessPath = paramsToJsonArray(inputPath, inputFileName, outputFileName);
                PathHandler reqPathHandler = new ReqPathHandler(config);
                reqPathHandler.parse(accessPath);
                if (reqPathHandler.isLegalInputPathEnd()) {
                    if (downFile(reqPathHandler.getInputPath(), reqPathHandler.getInputFileName())) {
                        String rePath = inputPath.replace("/", "$$");
                       /* convertHandler.convertWithListener(reqPathHandler.getWordToPdfLocalPathXin() + inputFileName,
                                reqPathHandler.getWordToPdfLocalPathXin() + "&" + rePath + "&" + outputFileName, SystemSourceEnum.NEW_PORTAL.getName());
                        logger.info("异步执行，转换任务已加入到任务池, 被转换的文件：" + reqPathHandler.getInputFileName());
                        return String.valueOf(ResultStatusCodeEnum.SUCCESS.getCode());*/
                        logger.info("异步执行，转换任务已加入到任务池, 被转换的文件：" + reqPathHandler.getInputFileName());
                        return word2PfdConvertService.executeConvertNewPortal(reqPathHandler.getWordToPdfLocalPathXin() + inputFileName,
                               reqPathHandler.getWordToPdfLocalPathXin() + "&" + rePath + "&" + outputFileName, SystemSourceEnum.NEW_PORTAL.getName());
                    } else {
                        word2pdfInfoLogService.recordLog(inputPath, inputFileName, outputFileName, ResultStatusCodeEnum.FAIL.getCode());
                        logger.error(reqPathHandler.getInputFileName() + " 下载失败");
                        return String.valueOf(ResultStatusCodeEnum.FAIL.getCode());
                    }
                } else {
                    word2pdfInfoLogService.recordLog(inputPath, inputFileName, outputFileName, ResultStatusCodeEnum.ILLEGAL_END_PATH.getCode());
                    logger.error(reqPathHandler.getInputFileName() + " 路径没有以 / 结尾");
                    return String.valueOf(ResultStatusCodeEnum.FAIL.getCode());
                }
            }
        }
        word2pdfInfoLogService.recordLog(inputPath, inputFileName, outputFileName, ResultStatusCodeEnum.ILLEGAL_PARAMETER.getCode());
        return String.valueOf(ResultStatusCodeEnum.FAIL.getCode());
    }

    private boolean downFile(String inputPath, String inputFileName) {
        SignConfigurationInfoEntity entity = eosDictEntryMapper.getSignConfigurationInfoEntity();
        FtpFileOperator ftpOperator = new FtpFileOperator();
//        FTPClient ftpClient = ftpOperator.getConnectionFTP(entity.getFtpAddress(), entity.getFtpPort().intValue(), entity.getFtpUser(), entity.getFtpPwd());
        FTPClient ftpClient = ftpOperator.getConnectionFTP("10.7.104.14", entity.getFtpPort().intValue(), "isig", "isig");
        return ftpOperator.downFile(ftpClient, inputPath, inputFileName, config.getWordToPdfLocalPathXin(), word2pdfInfoLogService);
    }

    /**
     * 将请求路径转换成 json 数组字符串
     * @param inputPath
     * @param inputFileName
     * @param outputFileName
     * @return
     */
    private String paramsToJsonArray(String inputPath, String inputFileName, String outputFileName) {
        return "[{\"id\":\"\",\"outputFileName\":\"" + outputFileName + "\",\"inputPath\":\"" + inputPath + "\",\"inputFileName\":\"" + inputFileName + "\", \"sys\":\"\" }]";
    }
}
