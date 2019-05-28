package com.fotic.it.support.word2pdf.service;

import java.util.concurrent.Future;

/**
 * @Author: mfh
 * @Date: 2019-05-28 9:03
 **/
public interface TaskConvertService {
    void executeAsync(String inputPath, String outputPath, String sysChannel);
}
