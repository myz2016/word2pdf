package com.fotic.it.support.word2pdf.util;

import com.fotic.it.support.word2pdf.config.cons.Constant;
import org.springframework.util.Assert;

/**
 * @Author: mfh
 * @Date: 2019-05-17 21:19
 **/
public class CommonUtil {
    /**
     * 将字符串中的反斜杠替换成正斜杠
     *
     * @param path
     * @return
     */
    public final static String replaceBackSlash2ForwardSlash(String path) {
        Assert.notNull(path, "传入参数为null");
        return path.replace(Constant.BACK_SLASH, Constant.FORWARD_SLASH);
    }

    /**
     * 将字符串中的正斜杠替换成反斜杠
     *
     * @param path
     * @return
     */
    public final static String replaceForwardSlash2BackSlash(String path) {
        Assert.notNull(path, "传入参数为null");
        return path.replace(Constant.FORWARD_SLASH, Constant.BACK_SLASH);
    }
}
