package com.fotic.it.support.word2pdf.api;

import com.fotic.it.support.word2pdf.dao.entity.Word2pdfInfoLog;
import com.fotic.it.support.word2pdf.service.Word2pdfInfoLogService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(tags = "获取日志信息")
@Controller
public class Word2pdfInfoLogApi {

    @Autowired
    private Word2pdfInfoLogService word2pdfInfoLogService;

    @ApiOperation(value="获取日志信息方法")
    @ResponseBody
    @GetMapping(value = "/coreIndex")
    public PageInfo<Word2pdfInfoLog> index (HttpServletRequest request,
                                        HttpServletResponse response){
                int pageNum = Integer.valueOf(request.getParameter("pageNum"));
                int pageSize = Integer.valueOf(request.getParameter("pageSize"));
                String fileName = request.getParameter("fileName");
                String code = request.getParameter("code");
        PageInfo<Word2pdfInfoLog> word2pdfInfoLogList = word2pdfInfoLogService.findAll(pageNum,pageSize,fileName,code);
        return word2pdfInfoLogList;
    }

}
