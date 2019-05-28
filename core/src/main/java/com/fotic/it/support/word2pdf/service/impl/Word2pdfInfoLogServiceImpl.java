package com.fotic.it.support.word2pdf.service.impl;

import com.fotic.it.support.word2pdf.dao.entity.Word2pdfInfoLog;
import com.fotic.it.support.word2pdf.dao.mapper.Word2pdfInfoLogMapper;
import com.fotic.it.support.word2pdf.exception.ResultStatusCodeEnum;
import com.fotic.it.support.word2pdf.service.Word2pdfInfoLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class Word2pdfInfoLogServiceImpl implements Word2pdfInfoLogService {


    @Autowired
    private Word2pdfInfoLogMapper word2pdfInfoLogMapper;
    private Logger logger = LoggerFactory.getLogger(Word2pdfInfoLogServiceImpl.class);

    /**
     * 获取全部数据
     * @param pageNum
     * @param pageSize
     * @param fileName
     * @param code
     * @return
     */
    @Override
    public PageInfo<Word2pdfInfoLog> findAll(int pageNum, int pageSize,
                                             String fileName,
                                             String code){
        Word2pdfInfoLog.Builder builder = new Word2pdfInfoLog.Builder();
        if (fileName != null && !"".equals(fileName)){
            builder = new Word2pdfInfoLog.Builder().setFilename(fileName);
        }
        if (code != null && !"".equals(code)){
            builder.setCode(code);
        }
        PageHelper.startPage(Integer.valueOf(pageNum),Integer.valueOf(pageSize));
        List<Word2pdfInfoLog> word2pdfInfoLogs = word2pdfInfoLogMapper.findAll(builder.build());
        PageInfo<Word2pdfInfoLog> pageInfoLog = new PageInfo(word2pdfInfoLogs);
        return pageInfoLog;
    }

    /**
     * 组装word2pdfInfoLog实体类
     * @param inputPath     输入路径
     * @param outputPath    输出路径
     * @param convertState  转换状态
     * @return
     */
    @Override
    public int recordLog(String inputPath, String inputFileName, String outputPath, int convertState) {
        logger.info("开始构建 word2pdfInfoLog, inputPath: {}; inputFileName: {}; outputPath: {}; convertState: {}", inputPath, inputFileName, outputPath, convertState);
        Word2pdfInfoLog log = new Word2pdfInfoLog.Builder()
                .setId(String.valueOf(UUID.randomUUID()))
                .setCode(String.valueOf(convertState))
                .setFilename(this.replacementNull(inputFileName, "源文件文件名为空"))
                .setInfo(ResultStatusCodeEnum.getDescription(convertState))
                .setInputPath(this.replacementNull(inputPath, "输入路径为空"))
                .setOutputPath(this.replacementNull(outputPath, "输出路径为空"))
                .setCreatetime(new Date())
                .setModifytime(new Date())
                .build();
        logger.info("结束构建 word2pdfInfoLog");
        return this.insert(log);
    }

    /**
     * 插入
     * @param word2pdfInfoLog
     * @return
     */
    private int insert(Word2pdfInfoLog word2pdfInfoLog) {
        int resultcount = 0;
        try {
            resultcount = word2pdfInfoLogMapper.insert(word2pdfInfoLog);
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultcount;
    }

    /**
     * 替换空值
     * @param str       被检查的值
     * @param expectVal 如果数据不合规，期望替换成的值
     * @return
     */
    private String replacementNull(String str, String expectVal) {
        return null == str || "".equals(str) ? expectVal : str;
    }
}
