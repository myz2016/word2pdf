package com.fotic.it.support.admin.web;

import com.fotic.it.support.admin.config.HttpUtil;
import com.fotic.it.support.admin.entity.Word2pdfInfoLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Api(tags = "table2页面访问controller")
@Controller
@CrossOrigin
public class Word2pdfInfoController {

    @ApiOperation(value="index2访问core端入口")
    @ResponseBody
    @GetMapping(value = "/table2")
    public Map<String, Object> index(
            @RequestParam(required=true,defaultValue="1") Integer pageNumber,
            @RequestParam(required=false,defaultValue="20") Integer pageSize,
            @RequestParam String filename,
            @RequestParam String code)  {
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("application.yml"));
        Properties properties = yaml.getObject();
        String coreUrl = properties.getProperty("coreUrl.baseUrl")+properties.getProperty("coreUrl.coreIndexUrl");
        Map<String, Object> reqMap = new HashMap<String, Object>();

        //搜索框功能
        //当查询条件中包含中文时，get请求默认会使用ISO-8859-1编码请求参数，在服务端需要对其解码
        if (null != filename) {
              try {
                  filename = new String(filename.getBytes("ISO-8859-1"), "UTF-8");
              } catch (Exception e) {
                    e.printStackTrace();
              }
        }

        reqMap.put("pageNum",pageNumber);
        reqMap.put("pageSize",pageSize);
        reqMap.put("fileName",filename);
        reqMap.put("code",code);
//        reqMap.put("createTimeStart",createTimeStart);
//        reqMap.put("createTimeEnd",createTimeEnd);



        List<Map<String, Object>> mapList = HttpUtil.doGet(coreUrl,null,reqMap);
        Map<String, Object> respMap = new HashMap<String, Object>();
        if (respMap != null){
            respMap.put("total",Long.valueOf(mapList.get(0).get("total").toString()));
            respMap.put("rows",mapList.get(0).get("list"));
        }
//        Word2pdfInfoLog word2pdfInfoLog = null;
//        List<Word2pdfInfoLog> retList = new ArrayList<>();
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//        Date date = null;
//        SimpleDateFormat df1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
//        Date date1 = null;
//        Date date2 = null;
//        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        for(int i=0; i<mapList.size(); i++){
//            word2pdfInfoLog = new Word2pdfInfoLog();
//            word2pdfInfoLog.setId(mapList.get(i).get("id").toString());
//            word2pdfInfoLog.setCode((Integer) mapList.get(i).get("code"));
//            word2pdfInfoLog.setFilename((String) mapList.get(i).get("filename"));
//            word2pdfInfoLog.setInputPath((String) mapList.get(i).get("inputPath"));
//            word2pdfInfoLog.setOutputPath((String) mapList.get(i).get("outputPath"));
//            word2pdfInfoLog.setInfo((String) mapList.get(i).get("info"));
//            date = df.parse(mapList.get(i).get("createtime").toString());
//            date1 = df1.parse(date.toString());
//            word2pdfInfoLog.setCreatetime(df2.parse(df2.format(date1)));
//            date = df.parse(mapList.get(i).get("modifytime").toString());
//            date2 = df1.parse(date.toString());
//            word2pdfInfoLog.setModifytime(df2.parse(df2.format(date2)));
//            retList.add(word2pdfInfoLog);
//        }
//        JSONObject json =new JSONObject(map);
        return respMap;
    }
}
