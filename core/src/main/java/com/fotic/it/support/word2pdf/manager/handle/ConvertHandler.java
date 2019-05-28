package com.fotic.it.support.word2pdf.manager.handle;

import com.fotic.it.support.word2pdf.manager.ConvertResultListener;
import com.istyle.document.convert.SPDocumentConvert;
import com.istyle.document.convert.SPWordToPDFTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: mfh
 * @Date: 2019-05-09 20:46
 **/
@Component
public class ConvertHandler {
    private static Logger logger = LoggerFactory.getLogger(ConvertHandler.class);
    private static boolean isCreateTaskPool = false;
    /**
     * 带回调监听的 word 转 pdf
     *
     * @param inputPath
     * @param outputPath
     */
    public void convertWithListener(String inputPath, String outputPath, String sysSource) {
        logger.info("准备开始转换，输入路径：{}，输出路径：{}", inputPath, outputPath);
        SPDocumentConvert.mOnResultListener = ConvertResultListener.CALL_BACK_LISTENER;
        initializeTaskPool();
        convert(new SPWordToPDFTask(inputPath, outputPath, -1, sysSource));
    }

    /**
     * 不带回调监听的 word 转 pdf
     * @param inputPath
     * @param outputPath
     */
    public void convertWithNoneListener(String inputPath, String outputPath) {
        logger.info("准备开始转换，输入路径：{}，输出路径：{}", inputPath, outputPath);
        initializeTaskPool();
        convert(new InfoDisWordToPDFTask(inputPath, outputPath));
    }

    /**
     * 不包括回调函数的 word 转 pdf
     * @param t
     * @param <T>
     */
    private <T extends Runnable> void convert(T t) {
        SPDocumentConvert.getInstance().addTask(t);
        initialize();
    }

    private void initialize() {
        logger.info("开始转换！");
        SPDocumentConvert.nativeInitialize(0);
    }

    /**
     * 初始化 word 转 pdf 的线程池，只初始化一次
     */
    private void initializeTaskPool() {
        int initializationCapacity = 5;
        if (!isCreateTaskPool) {
            logger.info("创建线程池，线程池初始化大小：{}", initializationCapacity);
            SPDocumentConvert.getInstance().createTaskPool(initializationCapacity);
            isCreateTaskPool = true;
        }
    }
}