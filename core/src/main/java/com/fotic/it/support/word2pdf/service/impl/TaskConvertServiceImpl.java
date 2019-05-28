package com.fotic.it.support.word2pdf.service.impl;

import com.fotic.it.support.word2pdf.api.SystemSourceEnum;
import com.fotic.it.support.word2pdf.config.PathConfig;
import com.fotic.it.support.word2pdf.config.cons.Constant;
import com.fotic.it.support.word2pdf.dao.entity.SignConfigurationInfoEntity;
import com.fotic.it.support.word2pdf.dao.mapper.EosDictEntryMapper;
import com.fotic.it.support.word2pdf.exception.ResultStatusCodeEnum;
import com.fotic.it.support.word2pdf.manager.handle.ConvertResultWebServiceHandler;
import com.fotic.it.support.word2pdf.manager.handle.OutputPathHandler;
import com.fotic.it.support.word2pdf.manager.handle.PathHandler;
import com.fotic.it.support.word2pdf.service.TaskConvertService;
import com.fotic.it.support.word2pdf.service.Word2pdfInfoLogService;
import com.fotic.it.support.word2pdf.util.CommonUtil;
import com.fotic.it.support.word2pdf.util.FtpFileOperator;
import com.google.common.util.concurrent.ListenableFuture;
import com.istyle.document.convert.SPDocumentConvert;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: mfh
 * @Date: 2019-05-28 9:04
 **/
@Service
public class TaskConvertServiceImpl implements TaskConvertService {
    private static Logger logger = LoggerFactory.getLogger(TaskConvertServiceImpl.class);
    private static String lineSeparator = System.getProperty("line.separator");
    @Autowired
    private PathConfig config;
    @Autowired
    private EosDictEntryMapper mapper;
    @Autowired
    private Word2pdfInfoLogService word2pdfInfoLogService;

    @Async("asyncExecutor")
    @Override
    public void executeAsync(String inputPath, String outputPath, String sysChannel) {
        logger.info("开始异步转换 inputPath: {}, outputPath: {}", inputPath, outputPath);
        SPDocumentConvert.nativeInitialize(0);
        int convertResult = SPDocumentConvert.nativeWordToPDF(inputPath, outputPath);
        logger.info("转换结果：{}", convertResult);
        AsyncResult<Integer> result = new AsyncResult<>(convertResult);
        result.addCallback(new ListenableFutureCallback<Integer>() {
            @Override
            public void onFailure(Throwable ex) {

            }

            @Override
            public void onSuccess(Integer result) {
                if (result == ResultStatusCodeEnum.SUCCESS.getCode()) {
                    PathHandler processor = new OutputPathHandler(config);
                    ConvertResultWebServiceHandler webServiceHandle = new ConvertResultWebServiceHandler(processor, mapper, result);
                    processor.parse(outputPath);
                    if (SystemSourceEnum.RT_CONTRACT.getName().equals(sysChannel)) {
                        resultForContract(inputPath, outputPath, result, sysChannel, processor, webServiceHandle);
                    } else if (SystemSourceEnum.RT_INFO_DISCLOSURE.getName().equals(sysChannel)) {
                        /** 处理信息披露回调结果*/
                        resultForInfoDisclosure(inputPath, outputPath, result, sysChannel);
                    } else if (SystemSourceEnum.NEW_PORTAL.getName().equals(sysChannel)) {
                        /** 处理新平台回调结果*/
                        resultForNewPortal(inputPath, outputPath, result, sysChannel, webServiceHandle);
                    }

                } else {
                    //TODO 记录日志
                    word2pdfInfoLogService.recordLog(inputPath, getFileName(inputPath), outputPath, result);
                }
            }
        });
    }

    /**
     * 软通-合同 处理 word 转 pdf 回调处理
     * @param inputPath
     * @param outputPath
     * @param convertState
     * @param sysSource
     * @param processor
     * @param webServiceHandle
     */
    private void resultForContract(String inputPath, String outputPath, int convertState, String sysSource, PathHandler processor, ConvertResultWebServiceHandler webServiceHandle) {
        logger.info("软通系统-合同，转换完成，执行回调函数，输出参数分别为：inputPath:{}，outputPath:{}，convertState:{}，系统来源:{}", inputPath, outputPath, convertState, sysSource);
        String[] outputPathSplit = outputPath.split(Constant.PATH_SEPARATOR_RT);
        logger.info("outputPath(pdf file path) contain '&' num : {}", outputPathSplit.length);
        if (outputPathSplit.length == 1) {
            logger.info("outputPath(pdf file path) contain '&' num : {} , word2pdf , not check", outputPathSplit.length);
        } else {
            logger.info("outputPath(pdf file path) contain '&' num : " + outputPathSplit.length + lineSeparator + "  split : filePath = " + processor.getFilePath() + ", fileName = " + processor.getFileName() + ", id=" + processor.getId() + ", sys=" + processor.getSystem());
            // TODO 如果这里抛出异常，执行将会中断，比如本地测试时，软通系统不启动的情况下，这里就会出现异常
            try {
                webServiceHandle.handleIWordPDfResultWebService();
            } catch (Exception e) {
                e.printStackTrace();
                word2pdfInfoLogService.recordLog(inputPath, getFileName(inputPath), outputPath, ResultStatusCodeEnum.WEBSERVICE_EXCEPTION.getCode());
                logger.error("webservice 异常");
                throw new RuntimeException("webservice 异常");
            }
            moveToOtherFolders(processor.getFilePath(), processor.getFilePath() + "tmppdf" + File.separator, processor.getFileName());
            logger.info("word 转 pdf 执行完成!! filePath = {}, fileName = {}, id= {}, sys= {}", processor.getFilePath(), processor.getFileName(), processor.getId(), processor.getSystem());
        }
        word2pdfInfoLogService.recordLog(inputPath, getFileName(inputPath), outputPath, convertState);
    }

    /**
     * 软通-信息披露 处理 word 转 pdf 回调处理
     * @param inputPath
     * @param outputPath
     * @param convertState
     * @param sysSource
     */
    private void resultForInfoDisclosure(String inputPath, String outputPath, int convertState, String sysSource) {
        logger.info("软通系统-信息披露，转换完成，执行回调函数，输出参数分别为：inputPath:{}，outputPath:{}，convertState:{}，系统来源:{}", inputPath, outputPath, convertState, sysSource);
    }

    /**
     * 新平台 处理 word 转 pdf 回调处理
     *
     * @param inputPath
     * @param outputPath
     * @param convertState
     * @param webServiceHandle
     */
    private void resultForNewPortal(String inputPath, String outputPath, int convertState, String sysSource, ConvertResultWebServiceHandler webServiceHandle) {
        //TODO 新平台转换结果不可使用OutputPathHandler这个解析器，应该新建一子类，因为新平台的输出路径对于现在OutputPathHandler的parse方法是不适用的
        logger.info("新平台系统，转换完成，执行回调函数，输出参数分别为：inputPath:{} outputPath:{} convertState:{} 系统来源:{}", inputPath + lineSeparator, outputPath + lineSeparator, convertState, sysSource);
        String[] outPutPathArray = outputPath.split("&");
        if (outPutPathArray.length == 3) {
            if (convertState == ResultStatusCodeEnum.SUCCESS.getCode()) {
                String replaceInputPath = outPutPathArray[1].replace("$$", "/");
                String outputFileName = outPutPathArray[2];
                String outFile = webServiceHandle.getWordToPdfLocalPathXin() + outputFileName;
                File oldFile = new File(outputPath);
                File newFile = new File(outFile);
                if (!oldFile.exists()) {
                    logger.info("{} 中包含的文件名与 {} 没有匹配成功，出现此种情况不代表一定就是 pdf 文件生成失败。", outputPath, outputFileName);
                    word2pdfInfoLogService.recordLog(inputPath, getFileName(inputPath), outputPath, ResultStatusCodeEnum.FILE_NOT_EXIST.getCode());
                    return;
                }
                if (newFile.exists()) {
                    logger.info("{} 已存在", newFile);
                    word2pdfInfoLogService.recordLog(inputPath, getFileName(inputPath), outputPath, ResultStatusCodeEnum.DUPLICATE_FILE_NAME.getCode());
                } else {
                    oldFile.renameTo(newFile);
                    try {
                        uploadFile(replaceInputPath, outputFileName, outFile);
                        word2pdfInfoLogService.recordLog(inputPath, getFileName(inputPath), outputPath, ResultStatusCodeEnum.SUCCESS.getCode());
                    } catch (FileNotFoundException e) {
                        logger.error("{} 路径下不存在文件", outFile);
                        word2pdfInfoLogService.recordLog(inputPath, getFileName(inputPath), outputPath, ResultStatusCodeEnum.FILE_NOT_EXIST.getCode());
                    }
                }
                deleteFile(inputPath);
                deleteFile(newFile);
            } else {
                word2pdfInfoLogService.recordLog(inputPath, getFileName(inputPath), outputPath, ResultStatusCodeEnum.FAIL.getCode());
            }
        } else {
            try {
                webServiceHandle.handleIWordPDfResultWebServiceNewPortal();
                word2pdfInfoLogService.recordLog(inputPath, getFileName(inputPath), outputPath, ResultStatusCodeEnum.DUPLICATE_FILE_NAME.getCode());
            } catch (Exception e) {
                word2pdfInfoLogService.recordLog(inputPath, getFileName(inputPath), outputPath, ResultStatusCodeEnum.WEBSERVICE_EXCEPTION.getCode());
                logger.error("webservice 异常");
            } finally {
                deleteFile(inputPath);
                deleteFile(outputPath);
            }
        }
    }

    private String getFileName(String inputPath) {
        String fileName = CommonUtil.replaceBackSlash2ForwardSlash(inputPath);
        return fileName.substring(fileName.lastIndexOf("/") + 1);
    }

    /**
     * 根据当前时间，获取编码主体
     *
     * @return
     */
    private String getCode() {
        SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_PATTERN);
        return sdf.format(new Date());
    }

    private void moveToOtherFolders(String originPath, String targetPath, String fileName) {
        logger.info("移动 {} 文件到 {}", originPath + fileName, targetPath);
        try {
            File originFile = new File(originPath + fileName);
            //获取文件夹路径
            File targetFile = new File(targetPath);
            //判断文件夹是否创建，没有创建则创建新文件夹
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            String name = fileName.replace(suffix, "");
            int nameLen = (name.indexOf("@") > -1) ? name.lastIndexOf("@") : name.length();
            String nameStr = name.substring(0, nameLen);
            fileName = nameStr + "@" + getCode() + suffix;
            File file = new File(targetPath + fileName);
            if (originFile.renameTo(file)) {
                logger.info("文件移动成功!!" + lineSeparator + " originPath folder = " + originPath + lineSeparator + " target folder = " + targetPath + lineSeparator + " file name = " + fileName);
            } else {
                logger.info("文件移动失败!!" + lineSeparator + " originPath folder = " + originPath + lineSeparator + " target folder = " + targetPath + lineSeparator + " file name = " + fileName);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.error("文件移动发生异常!!" + lineSeparator + " originPath folder = " + originPath + lineSeparator + " target folder = " + targetPath + lineSeparator + " file name = " + fileName);
        }
    }

    private boolean deleteFile(String path) {
        return deleteFile(new File(path));
    }

    private boolean deleteFile(File file) {
        logger.info("FTP 删除文件: {}", file.getAbsolutePath());
        return file.delete();
    }

    private void uploadFile(String replaceInputPath, String outputFileName, String outFile) throws FileNotFoundException {
        logger.info("FTP 上传文件: 将 {} 路径下的文件上传到 {}，并将文件重命名为 {}", outFile, replaceInputPath, outputFileName);
        FtpFileOperator ftpOperator = new FtpFileOperator();
        SignConfigurationInfoEntity signCofInfo = mapper.getSignConfigurationInfoEntity();
        FTPClient client = ftpOperator.getConnectionFTP(signCofInfo.getFtpAddress(), signCofInfo.getFtpPort().intValue(), signCofInfo.getFtpUser(), signCofInfo.getFtpPwd());
        ftpOperator.uploadFile(client, replaceInputPath, outputFileName, new FileInputStream(new File(outFile)));
    }
}
