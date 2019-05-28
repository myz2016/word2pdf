package com.fotic.it.support.word2pdf.util;

import com.fotic.it.support.word2pdf.config.cons.Constant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * @Author: mfh
 * @Date: 2019-05-11 15:51
 **/
public class CharacterEncodingUtil {
    /**
     * 将 request 与 response 转换成 gbk 编码
     * @param req
     * @param resp
     * @throws UnsupportedEncodingException
     */
    public static final void setGBKCharacterEncoding(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        req.setCharacterEncoding(Constant.CHARACTER_ENCODING_GBK);
        resp.setCharacterEncoding(Constant.CHARACTER_ENCODING_GBK);
    }
}