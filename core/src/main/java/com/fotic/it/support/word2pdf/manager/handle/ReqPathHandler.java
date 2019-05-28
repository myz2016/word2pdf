package com.fotic.it.support.word2pdf.manager.handle;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fotic.it.support.word2pdf.config.PathConfig;
import com.fotic.it.support.word2pdf.config.cons.Constant;
import com.fotic.it.support.word2pdf.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Map;

/**
 * @Author: mfh
 * @Date: 2019-04-26 21:30
 **/
@Component("reqPathProcessor")
public class ReqPathHandler extends PathHandler {

    private static Logger logger = LoggerFactory.getLogger(ReqPathHandler.class);

    public ReqPathHandler() {}

    public ReqPathHandler(PathConfig config) {
        super(config);
    }

    @Override
    public void parse(String accessPath) {
        //TODO 这是入口，后期需要指定参数的规则，比如json串的格式
        //[{"id":"131382","outputFileName":"","inputPath":"\\contractRepository\\contract_store\\HTSP-20190423-3-NO.7\\","inputFileName":"O2019042314372724991.docx","sys":"isRtSystem"}]
        logger.info("解析远端请求路径：{}", accessPath);
        JSONArray parse = accessPath.contains(Constant.BACK_SLASH) ?
                JSONArray.parseArray(CommonUtil.replaceBackSlash2ForwardSlash(accessPath)) :
                JSONArray.parseArray(accessPath);
        Map map = JSONObject.toJavaObject(parse.getJSONObject(0), Map.class);
        logger.info("解析远端请求路径结果：{}", map);
        setMap(map);
    }
}
