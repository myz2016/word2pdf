package com.fotic.it.support.word2pdf.dao.mapper;

import com.fotic.it.support.word2pdf.dao.entity.Word2pdfInfoLog;
import com.github.pagehelper.Page;
import oracle.sql.DATE;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author 王征
 * @Date:
 */
@Mapper
@Component
public interface Word2pdfInfoLogMapper
{
    /**
     * 查询全匹配信息
     * @return 全量全匹配信息
     */
    List<Word2pdfInfoLog> findAll(Word2pdfInfoLog word2pdfInfoLog);

    /**
     *
     * @return int
     */
    int insert(Word2pdfInfoLog word2pdfInfoLog);

}
