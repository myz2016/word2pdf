package com.fotic.it.support.word2pdf.service.impl;

import com.fotic.it.support.word2pdf.api.SystemSourceEnum;
import com.fotic.it.support.word2pdf.config.PathConfig;
import com.fotic.it.support.word2pdf.config.cons.Constant;
import com.fotic.it.support.word2pdf.exception.ResultStatusCodeEnum;
import com.fotic.it.support.word2pdf.exception.commons.BusinessException;
import com.fotic.it.support.word2pdf.exception.commons.IllegalEndPathException;
import com.fotic.it.support.word2pdf.manager.handle.ConvertHandler;
import com.fotic.it.support.word2pdf.manager.handle.PathHandler;
import com.fotic.it.support.word2pdf.manager.handle.ReqPathHandler;
import com.fotic.it.support.word2pdf.service.TaskConvertService;
import com.fotic.it.support.word2pdf.service.Word2PfdConvertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.Future;


/**
 * @Author: mfh
 * @Date: 2019-05-09 16:09
 **/
@Service
public class Word2PfdConvertServiceImpl implements Word2PfdConvertService {
    private Logger logger = LoggerFactory.getLogger(Word2PfdConvertServiceImpl.class);
    @Autowired
    private ConvertHandler convertHandler;
    @Autowired
    private PathConfig config;
    @Autowired
    private TaskConvertService taskConvertService;
    @Override
    public String check(String reqPath, String sysChannel) throws Exception {
        verifyPath(reqPath);
        ReqPathHandler reqPathHandler = parsePath(reqPath);
//        taskConvertService.executeAsync(reqPathHandler.getFullInputPath(), reqPathHandler.getOutputPathForCheck());
        return this.preConvertWithListener(reqPathHandler, reqPathHandler.getOutputPathForCheck(), sysChannel);
    }

    @Override
    public String executeConvert(String path, String sysChannel) throws Exception {
        verifyPath(path);
        ReqPathHandler reqPathHandler = parsePath(path);
        return this.preConvertWithListener(reqPathHandler, reqPathHandler.getOutputPathForExecute(), sysChannel);
    }

    @Override
    public String executeConvertInfoDisclosure(String contractPath, String sysChannel) throws Exception {
        verifyPath(contractPath);
        ReqPathHandler reqPathHandler = this.parsePath(replaceKeyAdaptor(contractPath));
        return this.preConvertWithNoneListener(reqPathHandler, sysChannel);
    }

    @Override
    public String executeConvertNewPortal(String inputPath, String outputPath, String sysChannel) {
        int code;
        try {
            taskConvertService.executeAsync(inputPath, outputPath, sysChannel);
            code = ResultStatusCodeEnum.SUCCESS.getCode();
        } catch (Exception e) {
            code = ResultStatusCodeEnum.FAIL.getCode();
        }
        return String.valueOf(code);
    }

    private ReqPathHandler parsePath(String s) {
        ReqPathHandler reqPathHandler = new ReqPathHandler(config);
        reqPathHandler.parse(s);
        return reqPathHandler;
    }

    /**
     * 带回调监听的word 转 pdf
     * @param outPutPath pdf 文件输出路径
     * @return
     */
    private String preConvertWithListener(PathHandler reqPathHandler, String outPutPath, String sysChannel) throws Exception {
        if (reqPathHandler.isLegalInputPathEnd()) {
            try {
//                convertHandler.convertWithListener(reqPathHandler.getFullInputPath(), outPutPath, sysChannel);
                taskConvertService.executeAsync(reqPathHandler.getFullInputPath(), outPutPath, sysChannel);
                //TODO 其实这里并不知道是否执行成功，因为转换的方式是异步的
                logger.info("异步执行，转换任务已加入到任务池, 被转换的文件：" + reqPathHandler.getInputFileName());
                return String.valueOf(ResultStatusCodeEnum.SUCCESS.getCode());
            } catch (RuntimeException e) {
                throw new BusinessException("异常信息：" + e.getMessage() +", 文件路径：" + reqPathHandler.getInputFileName());
            }
        }
        throw new IllegalEndPathException(reqPathHandler.getInputFileName() + " 路径没有以 / 或 \\ 结尾");
    }

    /**
     * 不带回调监听的 word 转 pdf
     * @param reqPathHandler
     * @return
     */
    private String preConvertWithNoneListener(PathHandler reqPathHandler, String sysSource)  throws Exception {
        if (reqPathHandler.isLegalInputPathEnd()) {
            try {
//                convertHandler.convertWithNoneListener(reqPathHandler.getInputPathJoinInputFileName(), reqPathHandler.getOutputPathJoinOutputFileName());
//                convertHandler.convertWithListener(reqPathHandler.getInputPathJoinInputFileName(), reqPathHandler.getOutputPathJoinOutputFileName(), sysSource);
                taskConvertService.executeAsync(reqPathHandler.getInputPathJoinInputFileName(), reqPathHandler.getOutputPathJoinOutputFileName(), sysSource);
                logger.info("异步执行，转换任务已加入到任务池, 被转换的文件：" + reqPathHandler.getInputFileName());
                return String.valueOf(ResultStatusCodeEnum.SUCCESS.getCode());
            } catch (Exception e) {
                throw new BusinessException("异常信息：" + e.getMessage() + ", 文件路径：" + reqPathHandler.getInputPathJoinInputFileName());
            }
        }
        throw new IllegalEndPathException(reqPathHandler.getInputFileName() + " 路径没有以 / 或 \\ 结尾");
    }

    /**
     * 检查 path 是否为null，如果为 null，抛出空指针异常
     * @param reqPath
     */
    private void verifyPath(String reqPath) {
        if (StringUtils.isEmpty(reqPath)) {
            throw new NullPointerException("路径参数为 Null");
        }
    }

    private String replaceKeyAdaptor(String path) {
        /**
         * [{"outPath":"D:/test/","inPath":"D:/test/","destFileName":"123.pdf","srcFileName":"123.doc"}]
         */
        return path.replace("outPath", Constant.OUTPUT_PATH)
                .replace("destFileName", Constant.OUTPUT_FILE_NAME)
                .replace("inPath", Constant.INPUT_PATH)
                .replace("srcFileName", Constant.INPUT_FILE_NAME);
    }

}
