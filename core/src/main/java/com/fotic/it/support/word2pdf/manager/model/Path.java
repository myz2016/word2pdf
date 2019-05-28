package com.fotic.it.support.word2pdf.manager.model;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author: mfh
 * @Date: 2019-04-28 17:21
 **/
public class Path {
    private String id;
    private String system;
    private String filePath;
    private String fileName;
    private String outputPath;
    private String outputFileName;
    private String inputPath;
    private String inputFileName;
    private String fileSuffix;

    public void setId(String id) {
        this.id = id;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    private Path() {

    }
    private Path(Builder builder) {
        this.id = builder.id;
        this.system = builder.system;
        this.filePath = builder.filePath;
        this.fileName = builder.fileName;
        this.outputPath = builder.outputPath;
        this.outputFileName = builder.outputFileName;
        this.inputPath = builder.inputPath;
        this.inputFileName = builder.inputFileName;
        this.fileSuffix = builder.fileSuffix;
    }

    public String toJsonString() {
        return JSONObject.toJSONString(this);
    }

    public static class Builder {
        private String id;
        private String system;
        private String filePath;
        private String fileName;
        private String outputPath;
        private String outputFileName;
        private String inputPath;
        private String inputFileName;
        private String fileSuffix;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setSystem(String system) {
            this.system = system;
            return this;
        }

        public Builder setFilePath(String filePath) {
            this.filePath = filePath;
            return this;
        }

        public Builder setFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder setOutputPath(String outputPath) {
            this.outputPath = outputPath;
            return this;
        }

        public Builder setOutputFileName(String outputFileName) {
            this.outputFileName = outputFileName;
            return this;
        }

        public Builder setInputPath(String inputPath) {
            this.inputPath = inputPath;
            return this;
        }

        public Builder setInputFileName(String inputFileName) {
            this.inputFileName = inputFileName;
            return this;
        }

        public Builder setFileSuffix(String fileSuffix) {
            this.fileSuffix = fileSuffix;
            return this;
        }

        public Path build() {
            return new Path(this);
        }
    }
    public String getId() {
        return id;
    }

    public String getSystem() {
        return system;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public String getInputPath() {
        return inputPath;
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }
}
