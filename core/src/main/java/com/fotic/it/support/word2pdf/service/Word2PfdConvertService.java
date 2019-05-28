package com.fotic.it.support.word2pdf.service;

import com.fotic.it.support.word2pdf.manager.handle.PathHandler;

/**
 * @Author: mfh
 * @Date: 2019-05-09 15:48
 **/
public interface Word2PfdConvertService {

    /**
     * 检查是否能转换
     * @param reqPath
     * @return
     */
    String check(String reqPath, String sysChannel) throws Exception;

    /**
     * 执行 word 转 pdf
     * @param reqPath
     * @return
     */
    String executeConvert(String reqPath, String sysChannel) throws Exception;

    /**
     * 信息披露 word 转 pdf 接口
     * @param reqPath
     * @return
     */
    String executeConvertInfoDisclosure(String reqPath, String sysChannel) throws Exception;

    /**
     * 新平台 word 转 pdf 接口
     * @param inputPath
     * @param outputPath
     * @param sysChannel
     * @return
     */
    String executeConvertNewPortal(String inputPath, String outputPath, String sysChannel) throws Exception;
}
