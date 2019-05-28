package com.fotic.it.support.word2pdf.util;

import com.fotic.it.support.word2pdf.config.cons.Constant;
import com.fotic.it.support.word2pdf.exception.ResultStatusCodeEnum;
import com.fotic.it.support.word2pdf.service.Word2pdfInfoLogService;
import org.apache.commons.net.ftp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;

/**
 * Ftp 操作类
 */
public class FtpFileOperator {

    private Logger logger = LoggerFactory.getLogger(FtpFileOperator.class);

    /**
     * 获得连接-FTP方式
     *
     * @param hostName FTP服务器地址
     * @param port     FTP服务器端口
     * @param userName FTP登录用户名
     * @param passWord FTP登录密码
     * @return FTPClient
     */
    public FTPClient getConnectionFTP(String hostName, int port, String userName, String passWord) {
        //创建FTPClient对象
        FTPClient ftp = new FTPClient();
        try {
            //连接FTP服务器
            ftp.connect(hostName, port);
            //下面三行代码必须要，而且不能改变编码格式，否则不能正确下载中文文件
            ftp.setControlEncoding(Constant.CHARACTER_ENCODING_GBK);
            FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
            conf.setServerLanguageCode(Constant.LANGUAGE_CODE);
            //登录ftp
            ftp.login(userName, passWord);
            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                ftp.disconnect();
                logger.error("连接服务器失败");
            }
            logger.info("登陆服务器成功");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return ftp;
    }

    public FTPClient connect(String host, String user, String pwd) throws IOException {
        FTPClient ftpclient = new FTPClient();
        ftpclient.setControlEncoding(Constant.CHARACTER_ENCODING_GBK);
        ftpclient.connect(host);
        ftpclient.login(user, pwd);
        ftpclient.setFileType(FTPClient.BINARY_FILE_TYPE);
        return ftpclient;
    }


    /**
     * 关闭连接-FTP方式
     *
     * @param ftp FTPClient对象
     * @return boolean
     */
    public boolean closeFTP(FTPClient ftp) {
        if (ftp.isConnected()) {
            try {
                ftp.disconnect();
                logger.info("FTP 连接关闭");
                return true;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 上传文件-FTP方式
     *
     * @param ftp         FTPClient对象
     * @param path        FTP服务器上传地址
     * @param fileName    本地文件路径
     * @param inputStream 输入流
     * @return boolean
     */
    public boolean uploadFile(FTPClient ftp, String path, String fileName, InputStream inputStream) {
        boolean success = false;
        try {
            //转移到指定FTP服务器目录
            ftp.changeWorkingDirectory(path);
            //得到目录的相应文件列表
            FTPFile[] fs = ftp.listFiles();
            fileName = FtpFileOperator.changeName(fileName, fs);
            fileName = new String(fileName.getBytes(Constant.CHARACTER_ENCODING_GBK), Constant.CHARACTER_ENCODING_8859);
            path = new String(path.getBytes(Constant.CHARACTER_ENCODING_GBK), Constant.CHARACTER_ENCODING_8859);
            //转到指定上传目录
            ftp.changeWorkingDirectory(path);
            //将上传文件存储到指定目录
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            //如果缺省该句 传输txt正常 但图片和其他格式的文件传输出现乱码
            ftp.storeFile(fileName, inputStream);
            //关闭输入流
            inputStream.close();
            //退出ftp
            ftp.logout();
            //表示上传成功
            success = true;
            logger.info("{} 已成功上传到 {}", fileName, path);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return success;
    }

    /**
     * 删除文件-FTP方式
     *
     * @param ftp      FTPClient对象
     * @param path     FTP服务器上传地址
     * @param fileName FTP服务器上要删除的文件名
     * @return
     */
    public boolean deleteFile(FTPClient ftp, String path, String fileName) {
        boolean success = false;
        try {
            //转移到指定FTP服务器目录
            ftp.changeWorkingDirectory(path);
            fileName = new String(fileName.getBytes(Constant.CHARACTER_ENCODING_GBK), Constant.CHARACTER_ENCODING_8859);
            ftp.deleteFile(fileName);
            ftp.logout();
            logger.info("{} 文件被删除", fileName);
            success = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return success;
    }

    /**
     * 上传文件-FTP方式
     *
     * @param ftp       FTPClient对象
     * @param path      FTP服务器上传地址
     * @param fileName  本地文件路径
     * @param localPath 本里存储路径
     * @return boolean
     */
    public boolean downFile(FTPClient ftp, String path, String fileName, String localPath, Word2pdfInfoLogService logService) {
        logger.info("FTP 下载文件：准备将 {} 的目录里的 {} 文件 下载到 {}", path, fileName, localPath);
        boolean success = false;
        try {
            //转移到FTP服务器目录
            ftp.changeWorkingDirectory(path);
            //得到目录的相应文件列表
            FTPFile[] fs = ftp.listFiles();
            //TODO 应该验证文件是否存在，如果不存在就没有下载的必要了
            for (FTPFile ff : fs) {
                if (ff.getName().equals(fileName)) {
                    File localFile = new File(localPath + "\\" + ff.getName());
                    OutputStream outputStream = new FileOutputStream(localFile);
                    //将文件保存到输出流outputStream中
                    ftp.retrieveFile(new String(ff.getName().getBytes(Constant.CHARACTER_ENCODING_GBK), Constant.CHARACTER_ENCODING_8859), outputStream);
                    outputStream.flush();
                    outputStream.close();
                    logger.info("FTP 下载文件完成：{} 已下载到 {}", fileName, localPath);
                    success = true;
                }
            }
            if (!success) {
                /**如果 FTP 服务器上没有找到指定文件，则记录日志*/
                logService.recordLog(path, fileName, localPath, ResultStatusCodeEnum.FILE_NOT_EXIST.getCode());
            }
            ftp.logout();
        } catch (Exception e) {
            logger.error("发生异常, 异常信息：" + e.getMessage() + ", 文件：" + fileName);
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    logger.error("发生异常, 异常信息：" + e.getMessage() + ", 文件：" + fileName);
                    e.printStackTrace();
                }
            }
        }
        return success;
    }

    /**
     * 判断是否有重名文件
     *
     * @param fileName
     * @param fs
     * @return
     */
    public static boolean isFileExist(String fileName, FTPFile[] fs) {
        for (int i = 0; i < fs.length; i++) {
            FTPFile ff = fs[i];
            if (ff.getName().equals(fileName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据重名判断的结果 生成新的文件的名称
     * @param fileName
     * @param fs
     * @return
     */
    public static String changeName(String fileName, FTPFile[] fs) {
        int n = 0;
        while (isFileExist(fileName, fs)) {
            n++;
            String a = "[" + n + "]";
            int b = fileName.lastIndexOf(".");
            int c = fileName.lastIndexOf("[");
            if (c < 0) {
                c = b;
            }
            StringBuffer name = new StringBuffer(fileName.substring(0, c));
            StringBuffer suffix = new StringBuffer(fileName.substring(b + 1));
            fileName = name.append(a) + "." + suffix;
        }
        return fileName;
    }
}