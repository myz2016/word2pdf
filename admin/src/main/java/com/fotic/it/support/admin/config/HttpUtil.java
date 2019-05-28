package com.fotic.it.support.admin.config;/*
 *
 *@date 2018/12/5
 *@author TangNan
 */

//import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fotic.it.support.admin.entity.Word2pdfInfoLog;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Http工具类
 * @author TangNan
 * @date 2018-12-5
 */
public class HttpUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private static final String HTTP_CHARSET = "UTF-8";
    private static Object HashMap;

    /**
     * 产生一个Closeable的并且不需要SSL验证的httpclient
     *
     * @return 返回一个可关闭的，不需要的SSL验证的HTTPClient，HttpClient的关闭由调用者负责
     * @Author TangNan
     */
    public static CloseableHttpClient getCloasbleWithoutSSLHttpClient()
    {
        HttpClientBuilder builder = HttpClientBuilder.create();
        CloseableHttpClient httpClient = null;
        try
        {
            builder.setSSLContext(new SSLContextBuilder().loadTrustMaterial(new TrustSelfSignedStrategy()).build());
            // 不进行SSL验证
            builder.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE);
            httpClient = builder.build();
        }
        catch (Exception e)
        {
            logger.error("创建不需要SSL验证的httpclient失败，原因为：{}", e.getMessage());
        }
        return httpClient;
    }

    /**
     * 将返回报文体内容转换成Map对象
     *
     * @param httpResponse
     * @return 转换后的Map对象
     * @Author TangNan
     */
    public static List<Map<String, Object>> getResponsBodyToMap(HttpResponse httpResponse)
    {
        List<Map<String, Object>> ls = new ArrayList<Map<String, Object>>();
        Map<String, Object> contentMap = null;
        if (httpResponse == null)
        {
            return ls;
        }

        HttpEntity httpEntity = httpResponse.getEntity();
        StringBuilder sb = new StringBuilder();
        if (httpEntity != null)
        {
            BufferedReader reader = null;
            try
            {
                reader = new BufferedReader(new InputStreamReader(httpEntity.getContent(), HTTP_CHARSET), 10 * 1024);
                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line);
                }
                // 使用fastjson进行数据转换
//                JSONArray jsonArray =JSON.parseArray(sb.toString());
                JSONObject jsonObject = JSON.parseObject(sb.toString());
                Object ob = null;
                    contentMap = new HashMap<String, Object>(10);
                    for(String key:jsonObject.keySet()){
                        ob = jsonObject.get(key);
                        contentMap.put(key, ob);
                    }
                    ls.add(contentMap);
//                contentMap = JSON.parseObject(sb.toString());
            }
            catch (Exception e)
            {
                logger.error("将HttpResponse返回信息转换为Map出错:{}", e);
            }
            finally
            {
                if (reader != null)
                {
                    try
                    {
                        // 关闭流
                        reader.close();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }

        }
        return ls;
    }

    /**
     * 自己管理连接的doget方法
     *
     * @param client        可关闭的httpClient
     * @param url           不需要参数部分的url地址
     * @param headerMap     需要往请求头里写的信息
     * @param doGetParamMap doGet方法需要在URL里拼装的参数信息
     * @return 返回HttpResponse信息，需要自己解析内容，自己关闭
     * @Author TangNan
     */
    public static HttpResponse doGet(
            CloseableHttpClient client, String url, Map<String, Object> headerMap, Map<String, Object> doGetParamMap)
    {
        // 如果参数不为空，那么意味着URL需要拼装
        if (doGetParamMap != null)
        {
            StringBuilder sb = new StringBuilder();
            Set<String> setBody = doGetParamMap.keySet();
            Iterator<String> itBody = setBody.iterator();
            while (itBody.hasNext())
            {
                String key = itBody.next();
                Object objValue = doGetParamMap.get(key);
                String value = objValue == null ? "" : objValue.toString();
                sb.append(key).append("=").append(value).append("&");
            }
            // 拼装报文
            url += "?" + sb.substring(0, sb.length() - 1);
        }

        HttpGet httpGet = new HttpGet(url);

        // 如果报文头参数不为空，那么添加报文头信息
        if (headerMap != null)
        {
            Set<String> setHeader = headerMap.keySet();
            Iterator<String> itHeader = setHeader.iterator();
            while (itHeader.hasNext())
            {
                String key = itHeader.next();
                Object objValue = headerMap.get(key);
                String value = objValue == null ? "" : objValue.toString();
                // 装配Header
                httpGet.addHeader(key, value);
            }
        }

        HttpResponse httpResponse = null;
        try
        {
            httpResponse = client.execute(httpGet);
        }
        catch (ClientProtocolException e)
        {
            logger.error("客户端协议异常{}", e);
        }
        catch (IOException e)
        {
            logger.error("IO异常{}", e);
        }
        return httpResponse;
    }

    /**
     * 封装的doGet方法
     *
     * @param url           baseURL
     * @param headerMap     需要往请求头里写的信息息
     * @param doGetParamMap doGet方法需要在URL里拼装的参数信息
     * @return 返回解析好的结构体信息
     * @Author TangNan
     */
    public static List<Map<String, Object>> doGet(
            String url, Map<String, Object> headerMap, Map<String, Object> doGetParamMap)
    {
        CloseableHttpClient httpClient = null;
        List<Map<String, Object>> retMap = null;
        try
        {
            httpClient = getCloasbleWithoutSSLHttpClient();
            HttpResponse httpResponse = doGet(httpClient, url, headerMap, doGetParamMap);
            if (httpResponse == null)
            {
                throw new Exception("httpResponse对象为空");
            }
            retMap = getResponsBodyToMap(httpResponse);
        }
        catch (Exception e)
        {
            logger.error("doGet方法异常:{}", e.getMessage());
            logger.error("doGet方法异常:{}", e);
        }
        finally
        {
            if (httpClient != null)
            {
                try
                {

                    httpClient.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return retMap;
    }

    /**
     * 使用doPost的方式提交带form的请求
     *
     * @param client     可关闭的httpClient
     * @param url        不需要参数部分的url地址
     * @param headerMap  需要往请求头里写的信息
     * @param doGetParam 提交到post请求时，需要再URL中拼装的参数信息
     * @param formParam  form中的参数信息
     * @return 返回HttpResponse信息，需要自己解析内容，自己关闭
     * @Author TangNan
     */
    public static HttpResponse doPostWithForm(
            CloseableHttpClient client, String url, Map<String, Object> headerMap, Map<String, Object> doGetParam, Map<String, Object> formParam)
    {
        HttpResponse httpResponse = null;
        try
        {
            if (doGetParam != null)
            {
                StringBuilder sb = new StringBuilder();
                Set<String> setBody = doGetParam.keySet();
                Iterator<String> itBody = setBody.iterator();
                while (itBody.hasNext())
                {
                    String key = itBody.next();
                    Object objValue = doGetParam.get(key);
                    String value = objValue == null ? "" : objValue.toString();
                    sb.append(key).append("=").append(value).append("&");
                }
                // 拼装报文
                url += "?" + sb.substring(0, sb.length() - 1);
            }

            HttpPost httpPost = new HttpPost(url);
            if (headerMap != null)
            {
                Set<String> setHeader = headerMap.keySet();
                Iterator<String> itHeader = setHeader.iterator();
                while (itHeader.hasNext())
                {
                    String key = itHeader.next();
                    Object objValue = headerMap.get(key);
                    String value = objValue == null ? "" : objValue.toString();
                    // 装配Header
                    httpPost.addHeader(key, value);
                }
            }

            List<NameValuePair> bodylist = new LinkedList<NameValuePair>();
            if (formParam != null)
            {
                Set<String> setBody = formParam.keySet();
                Iterator<String> itBody = setBody.iterator();
                while (itBody.hasNext())
                {
                    String key = itBody.next();
                    Object objValue = formParam.get(key);
                    String value = objValue == null ? "" : objValue.toString();
                    bodylist.add(new BasicNameValuePair(key, value));
                }
                HttpEntity entity = null;
                try
                {
                    entity = new UrlEncodedFormEntity(bodylist, HTTP_CHARSET);
                    // 装配实体内容
                    httpPost.setEntity(entity);
                }
                catch (UnsupportedEncodingException e)
                {
                    logger.error("编码方式有误{}", e);
                    return null;
                }
            }
            try
            {
                httpResponse = client.execute(httpPost);
            }
            catch (ClientProtocolException e)
            {
                logger.error("客户端协议异常{}", e);
            }
            catch (IOException e)
            {
                logger.error("IO异常{}", e);
            }
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }
        return httpResponse;
    }

    /**
     * 封装的doPostWithForm方法
     *
     * @param url        baseURL
     * @param headerMap  需要往请求头里写的信息
     * @param doGetParam 提交到post请求时，需要再URL中拼装的参数信息
     * @param formParam  form中的参数信息
     * @return 返回封装的Map信息
     */
    public static List<Map<String, Object>> doPostWithForm(
            String url, Map<String, Object> headerMap, Map<String, Object> doGetParam, Map<String, Object> formParam)
    {
        CloseableHttpClient httpClient = null;
        List<Map<String, Object>> retMap = null;
        try
        {
            httpClient = getCloasbleWithoutSSLHttpClient();
            HttpResponse httpResponse = doPostWithForm(httpClient, url, headerMap, doGetParam, formParam);
            retMap = getResponsBodyToMap(httpResponse);
        }
        catch (Exception e)
        {
            logger.error("doPostWithForm方法异常:{}", e);
        }
        finally
        {
            if (httpClient != null)
            {
                try
                {
                    httpClient.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return retMap;
    }

    /**
     * 自己管理连接的doPostWithJson方法
     *
     * @param client     可关闭的httpClient
     * @param url        不需要参数部分的url地址
     * @param headerMap  需要往请求头里写的信息
     * @param doGetParam 提交到post请求时，需要再URL中拼装的参数信息
     * @param bodyMap    写入JSON的信息体
     * @return 返回HttpResponse信息，需要自己解析内容，自己关闭
     * @Author TangNan
     */
    public static HttpResponse doPostWithJson(
            CloseableHttpClient client, String url, Map<String, Object> headerMap, Map<String, Object> doGetParam, Map<String, Object> bodyMap)
    {
        HttpResponse httpResponse = null;
        try
        {
            if (doGetParam != null)
            {
                StringBuilder sb = new StringBuilder();
                Set<String> setBody = doGetParam.keySet();
                Iterator<String> itBody = setBody.iterator();
                while (itBody.hasNext())
                {
                    String key = itBody.next();
                    Object objValue = doGetParam.get(key);
                    String value = objValue == null ? "" : objValue.toString();
                    sb.append(key).append("=").append(value).append("&");
                }
                // 拼装报文
                url += "?" + sb.substring(0, sb.length() - 1);
            }

            HttpPost httpPost = new HttpPost(url);

            if (headerMap == null)
            {
                headerMap = new HashMap<>(10);
            }
            headerMap.put("Content-Type", "application/json;charset=UTF-8");
            Set<String> setHeader = headerMap.keySet();
            Iterator<String> itHeader = setHeader.iterator();
            while (itHeader.hasNext())
            {
                String key = itHeader.next();
                Object objValue = headerMap.get(key);
                String value = objValue == null ? "" : objValue.toString();
                // 装配Header
                httpPost.addHeader(key, value);
            }

            if (bodyMap != null)
            {
                String jsonStr = "123";
                HttpEntity entity = null;
                entity = new StringEntity(jsonStr, "utf-8");
                // 装配实体内容
                httpPost.setEntity(entity);
            }

            try
            {
                httpResponse = client.execute(httpPost);
            }
            catch (ClientProtocolException e)
            {
                logger.error("客户端协议异常");
                logger.error(e.getMessage());
            }
            catch (IOException e)
            {
                logger.error("IO异常");
                logger.error(e.getMessage());
            }
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }
        return httpResponse;
    }

    /**
     * 封装的doPostWithJson方法
     *
     * @param url        baseURL
     * @param headerMap  需要往请求头里写的信息
     * @param doGetParam 提交到post请求时，需要在URL中拼装的参数信息
     * @param bodyMap    写入JSON的信息体
     * @return 返回封装的Map信息
     */
    public static List<Map<String, Object>> doPostWithJson(
            String url, Map<String, Object> headerMap, Map<String, Object> doGetParam, Map<String, Object> bodyMap)
    {
        CloseableHttpClient httpClient = null;
        List<Map<String, Object>> retMap = null;
        try
        {
            httpClient = getCloasbleWithoutSSLHttpClient();
            HttpResponse httpResponse = doPostWithJson(httpClient, url, headerMap, doGetParam, bodyMap);
            retMap = getResponsBodyToMap(httpResponse);
        }
        catch (Exception e)
        {
            logger.error("doPostWithJson方法异常:{}", e);
        }
        finally
        {
            if (httpClient != null)
            {
                try
                {
                    httpClient.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return retMap;
    }

    /**
     * 自己管理连接的doPost利用Form上传文件的方法
     *
     * @param client          可关闭的httpClient
     * @param url             不需要参数部分的base_url
     * @param headerMap       需要往请求头里写的信息
     * @param doGetParam      doGet方法需要在URL里拼装的参数信息
     * @param contentBodyName 送入的文件流在http请求中的名称
     * @param file            文件句柄
     * @return 返回HttpResponse信息，需要自己解析内容，自己关闭
     * @Author TangNan
     */
    public static HttpResponse doPostWithFormAndFile(
            CloseableHttpClient client, String url, Map<String, Object> headerMap, Map<String, Object> doGetParam, String contentBodyName, File file)
    {
        HttpResponse httpResponse = null;
        try
        {
            if (doGetParam != null)
            {
                StringBuilder sb = new StringBuilder();
                Set<String> setBody = doGetParam.keySet();
                Iterator<String> itBody = setBody.iterator();
                while (itBody.hasNext())
                {
                    String key = itBody.next();
                    Object objValue = doGetParam.get(key);
                    String value = objValue == null ? "" : objValue.toString();
                    sb.append(key).append("=").append(value).append("&");
                }
                // 拼装报文
                url += "?" + sb.substring(0, sb.length() - 1);
            }

            HttpPost httpPost = new HttpPost(url);
            if (headerMap != null)
            {
                Set<String> setHeader = headerMap.keySet();
                Iterator<String> itHeader = setHeader.iterator();
                while (itHeader.hasNext())
                {
                    String key = itHeader.next();
                    Object objValue = headerMap.get(key);
                    String value = objValue == null ? "" : objValue.toString();
                    // 装配Header
                    httpPost.addHeader(key, value);
                }
            }


            FileBody fileBody = new FileBody(file);

            MultipartEntityBuilder meb = MultipartEntityBuilder.create();
            meb.setMode(HttpMultipartMode.RFC6532);
            // 针对企业微信封装的内容，待调整
            meb.addPart(contentBodyName, fileBody);
            httpPost.setEntity(meb.build());
            httpResponse = client.execute(httpPost);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }
        return httpResponse;

    }

    /**
     * @param url             不需要参数部分的base_url
     * @param headerMap       需要往请求头里写的信息
     * @param doGetParam      doGet方法需要在URL里拼装的参数信息
     * @param contentBodyName 送入的文件流在http请求中的名称
     * @param file            文件句柄
     * @return 返回封装的Map信息
     * @Author TangNan
     */
    public static List<Map<String, Object>> doPostWithFormAndFile(
            String url, Map<String, Object> headerMap, Map<String, Object> doGetParam, String contentBodyName, File file)
    {
        CloseableHttpClient httpClient = null;
        List<Map<String, Object>> retMap = null;
        try
        {
            httpClient = getCloasbleWithoutSSLHttpClient();
            HttpResponse httpResponse = doPostWithFormAndFile(httpClient, url, headerMap, doGetParam, contentBodyName, file);
            retMap = getResponsBodyToMap(httpResponse);
        }
        catch (Exception e)
        {
            logger.error("doPostWithFormAndFile方法异常:{}", e);
        }
        finally
        {
            if (httpClient != null)
            {
                try
                {
                    httpClient.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return retMap;
    }

    /**
     * 专门用于企业微信附件上传的，自己管理连接的doPost利用Form上传文件的方法
     * @param client          可关闭的httpClient
     * @param url             不需要参数部分的base_url
     * @param headerMap       需要往请求头里写的信息
     * @param doGetParam      doGet方法需要在URL里拼装的参数信息
     * @param contentBodyName 送入的文件流在http请求中的名称
     * @param file            文件句柄
     * @return 返回HttpResponse信息，需要自己解析内容，自己关闭
     * @Author TangNan
     */
    public static HttpResponse doPostWithFormAndFileForWeChat(
            CloseableHttpClient client, String url, Map<String, Object> headerMap, Map<String, Object> doGetParam, String contentBodyName, File file)
    {
        HttpResponse httpResponse = null;
        String boundary = "--------" + System.currentTimeMillis();
        try
        {
            if (doGetParam != null)
            {
                StringBuilder sb = new StringBuilder();
                Set<String> setBody = doGetParam.keySet();
                Iterator<String> itBody = setBody.iterator();
                while (itBody.hasNext())
                {
                    String key = itBody.next();
                    Object objValue = doGetParam.get(key);
                    String value = objValue == null ? "" : objValue.toString();
                    sb.append(key).append("=").append(value).append("&");
                }
                // 拼装报文
                url += "?" + sb.substring(0, sb.length() - 1);
            }


            HttpPost httpPost = new HttpPost(url);
            if (headerMap != null)
            {
                Set<String> setHeader = headerMap.keySet();
                Iterator<String> itHeader = setHeader.iterator();
                while (itHeader.hasNext())
                {
                    String key = itHeader.next();
                    Object objValue = headerMap.get(key);
                    String value = objValue == null ? "" : objValue.toString();
                    if (key.contains("Content-Type"))
                    {
                        value = value + "; boundary=" + boundary;
                    }
                    // 装配Header
                    httpPost.addHeader(key, value);
                }
            }

            // 根据企业微信的要求，指定disposition的内容
            FileBody fileBody = new FileBody(file, ContentType.APPLICATION_OCTET_STREAM, contentBodyName);

            MultipartEntityBuilder meb = MultipartEntityBuilder.create();
            meb.setCharset(Charset.forName("utf-8"));
            meb.setBoundary(boundary).setMode(HttpMultipartMode.RFC6532);
            // 针对企业微信封装的内容
            meb.addPart("media", fileBody);
            httpPost.setEntity(meb.build());
            httpResponse = client.execute(httpPost);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }
        return httpResponse;

    }

    /**
     * 专门为企业微信封装的http上传类
     * @param url             不需要参数部分的base_url
     * @param headerMap       需要往请求头里写的信息
     * @param doGetParam      doGet方法需要在URL里拼装的参数信息
     * @param contentBodyName 送入的文件流在http请求中的名称
     * @param file            文件句柄
     * @return 返回封装的Map信息
     * @Author TangNan
     */
    public static List<Map<String, Object>> doPostWithFormAndFileForWeChat(
            String url, Map<String, Object> headerMap, Map<String, Object> doGetParam, String contentBodyName, File file)
    {
        CloseableHttpClient httpClient = null;
        List<Map<String, Object>> retMap = null;
        try
        {
            httpClient = getCloasbleWithoutSSLHttpClient();
            HttpResponse httpResponse = doPostWithFormAndFileForWeChat(httpClient, url, headerMap, doGetParam, contentBodyName, file);
            retMap = getResponsBodyToMap(httpResponse);
        }
        catch (Exception e)
        {
            logger.error("doPostWithFormAndFile方法异常:{}", e);
        }
        finally
        {
            if (httpClient != null)
            {
                try
                {
                    httpClient.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return retMap;
    }

}
