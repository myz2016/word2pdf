package com.fotic.it.support.admin.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags = "后台支撑系统服务接口文档")
@Controller
@CrossOrigin
public class Word2pdfController {

    @ApiOperation(value="访问admin主页面")
    @GetMapping("/")
    public String index(String s)
    {
        return "index";
    }
}
