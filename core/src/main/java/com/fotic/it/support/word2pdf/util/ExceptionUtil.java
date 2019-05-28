package com.fotic.it.support.word2pdf.util;

/**
 * @Author: mfh
 * @Date: 2019-05-13 15:20
 **/
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.fotic.it.support.word2pdf.exception.ResultStatusCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 返回异常的处理
 */
public class ExceptionUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionUtil.class);

    private ExceptionUtil() {
    }

    /**
     * 获取异常信息
     *
     * @param e 异常
     */
    public static String getErrorMessage(Exception e) {
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            // 将出错的栈信息输出到printWriter中
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException ex) {
                    logger.error(ex.getMessage());
                }
            }
            if (pw != null) {
                pw.close();
            }
        }
        return sw.toString();
    }
    /**
     * 异常信息-->json
     *
     */
    public static String resultToJSON(ResultStatusCodeEnum exStatusCode) {
        SerializeConfig config = new SerializeConfig();
        config.configEnumAsJavaBean(ResultStatusCodeEnum.class);
        return JSON.toJSONString(exStatusCode, config);
    }

    /**
     * 异常信息--> String
     * @param exStatusCode
     * @return
     */
    public static String resultOf(ResultStatusCodeEnum exStatusCode) {
        return String.valueOf(exStatusCode.getCode());
    }
}
