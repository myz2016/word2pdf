package com.fotic.it.support.admin.entity;

import java.util.Date;

/**
 * 老接口对应数据信息存储表
 * @author wz
 */
public class Word2pdfInfoLog {
    /**
     * 主键id
     */
    private String id;
    /**
     * 返回码值
     * 1：转换失败2：OFFICE软件未安装3：OFFICE版本太低4：文档不存在'
     */
    private String code;

    /**
     * 文件名称
     */
    private String filename;
    /**
     * 输入的绝对路径
     */
    private String inputPath;
    /**
     * 输出的绝对路径
     */
    private String outputPath;
    /**
     * 详细的信息
     */
    private String info;
    /**
     * 数据创建时间
     */
    private Date createtime;

    /**
     * 数据创建时间 String类型负责前端展示
     */
    private String createTimeStr;

    /**
     * '数据修改时间
     */
    private Date modifytime;

    /**
     * '数据修改时间
     */
    private String modifytimeStr;

    public Word2pdfInfoLog() {}
    private Word2pdfInfoLog(Builder builder) {
        this.id = builder.getId();
        this.code = builder.getCode();
        this.filename = builder.getFilename();
        this.inputPath = builder.getInputPath();
        this.outputPath = builder.getOutputPath();
        this.info = builder.getInfo();
        this.createtime = builder.getCreatetime();
        this.modifytime = builder.getModifytime();
    }


    public static class Builder {
        private String id;
        private String code;
        private String filename;
        private String inputPath;
        private String outputPath;
        private String info;
        private Date createtime;
        private Date modifytime;
        private String createTimeStr;
        private String modifytimeStr;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        private String getCreateTimeStr(){
            return createTimeStr;
        }

        public String getModifytimeStr() {
            return modifytimeStr;
        }

        private String getId() {
            return id;
        }

        private String getCode() {
            return code;
        }

        public Builder setCode(String code) {
            this.code = code;
            return this;
        }

        private String getFilename() {
            return filename;
        }

        public Builder setFilename(String filename) {
            this.filename = filename;
            return this;
        }

        private String getInputPath() {
            return inputPath;
        }

        public Builder setInputPath(String inputPath) {
            this.inputPath = inputPath;
            return this;
        }

        private String getOutputPath() {
            return outputPath;
        }

        public Builder setOutputPath(String outputPath) {
            this.outputPath = outputPath;
            return this;
        }

        private String getInfo() {
            return info;
        }

        public Builder setInfo(String info) {
            this.info = info;
            return this;
        }

        private Date getCreatetime() {
            return createtime;
        }

        public Builder setCreatetime(Date createtime) {
            this.createtime = createtime;
            return this;
        }

        private Date getModifytime() {
            return modifytime;
        }

        public Builder setModifytime(Date modifytime) {
            this.modifytime = modifytime;
            return this;
        }

        public Word2pdfInfoLog build() {
            return new Word2pdfInfoLog(this);
        }
    }

    @Override
    public String toString() {
        return "Word2pdfInfoLog{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", filename='" + filename + '\'' +
                ", inputPath='" + inputPath + '\'' +
                ", outputPath='" + outputPath + '\'' +
                ", info='" + info + '\'' +
                ", createtime=" + createtime +
                ", createTimeStr='" + createTimeStr + '\'' +
                ", modifytime=" + modifytime +
                ", modifytimeStr='" + modifytimeStr + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getFilename() {
        return filename;
    }

    public String getInputPath() {
        return inputPath;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public String getInfo() {
        return info;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public Date getModifytime() {
        return modifytime;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public String getModifytimeStr() {
        return modifytimeStr;
    }

}