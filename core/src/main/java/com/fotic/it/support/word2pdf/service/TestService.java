package com.fotic.it.support.word2pdf.service;

import com.fotic.it.support.word2pdf.dao.entity.Word2pdfInfoLog;
import com.fotic.it.support.word2pdf.dao.mapper.TestMapper;
import com.fotic.it.support.word2pdf.dao.mapper.Word2pdfInfoLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 具体业务层
 */
@Service
public class TestService {
    @Autowired
    TestMapper testMapper;

    @Autowired
    Word2pdfInfoLogMapper word2pdfInfoLogMapper;

    public void testDao()
    {
        System.out.println(testMapper.findCount());
    }

    public void testDao1()
    {
//        List<Word2pdfInfoLog> word2pdfInfoLog = word2pdfInfoLogMapper.findAll();
//        System.out.println(word2pdfInfoLog== null?"Null":word2pdfInfoLog.toString());
    }

}
