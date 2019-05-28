package com.fotic.it.support.word2pdf.api;

import com.fotic.it.support.word2pdf.service.Word2PfdConvertService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: mfh
 * @Date: 2019-05-05 14:58
 **/
@Api(tags = "word转pdf功能服务接口文档")
@Controller
public class Word2PdfApi {
    @Autowired
    private Word2PfdConvertService service;

    @ApiOperation(value = "合同检查word能否转换成pdf功能")
    @RequestMapping("/isignweb/WordForPdfServlet")
    @ResponseBody
    public String check(@RequestParam String contractPath) throws Exception {
        return service.check(contractPath, SystemSourceEnum.RT_CONTRACT.getName());
    }

    @ApiOperation(value = "合同执行word转pdf功能")
    @RequestMapping("/isignweb/IstyleWordPDFServlet")
    @ResponseBody
    public String execute(@RequestParam String contractPath) throws Exception {
        return service.executeConvert(contractPath, SystemSourceEnum.RT_CONTRACT.getName());
    }

    @ApiOperation(value = "信息披露执行word转pdf功能")
    @RequestMapping("/isignweb/WorkToPdfForInfoDisclosureServlet")
    @ResponseBody
    public String executeConvertInfoDisclosure(@RequestParam String contractPath) throws Exception {
        return service.executeConvertInfoDisclosure(contractPath, SystemSourceEnum.RT_INFO_DISCLOSURE.getName());
    }
}
