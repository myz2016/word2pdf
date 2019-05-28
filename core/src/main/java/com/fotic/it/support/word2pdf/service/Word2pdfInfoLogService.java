package com.fotic.it.support.word2pdf.service;

import com.fotic.it.support.word2pdf.dao.entity.Word2pdfInfoLog;
import com.github.pagehelper.PageInfo;
import oracle.sql.DATE;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * word2pdf业务层
 */
public interface Word2pdfInfoLogService{

    /**
     * 获取全部数据
     *
     * @return
     */
    PageInfo<Word2pdfInfoLog> findAll(int pageNum, int pageSize,
                                      String fileName,
                                      String code
    );

    /**
     * 记录日志
     * @param inputPath     源路径
     * @param inputFileName 源文件名
     * @param outputPath    目标路径
     * @param convertState  转换状态
     * @return
     */
    int recordLog(String inputPath, String inputFileName, String outputPath, int convertState);
}
