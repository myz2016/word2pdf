package com.fotic.it.support.word2pdf.manager.handle;

import com.fotic.it.support.word2pdf.config.PathConfig;
import com.fotic.it.support.word2pdf.config.cons.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: mfh
 * @Date: 2019-04-29 19:19
 **/
@Component
public abstract class PathHandler {
    private static Logger logger = LoggerFactory.getLogger(PathHandler.class);

    private Map<String, String> map;
    private PathConfig config;

    public PathHandler(PathConfig config) {
        this.config = config;
    }

    public PathHandler() {
    }

    /**
     * 验证文件路径是否以正确的方式结尾
     *
     * @return
     */
    public boolean isLegalInputPathEnd() {
        return this.getInputPath().endsWith(Constant.FORWARD_SLASH) || this.getInputPath().endsWith(Constant.BACK_SLASH);
    }

    public String getInputFileName() {
        return this.map.get(Constant.INPUT_FILE_NAME);
    }

    public String getInputPath() {
        return this.map.get(Constant.INPUT_PATH);
    }

    public String getFullInputPath() {
        // D:\NAS
        return replaceSeparator(config.getNas() + this.map.get(Constant.INPUT_PATH) + this.map.get(Constant.INPUT_FILE_NAME));
    }

    public String getOutputPathForExecute() {
        return replaceSeparator(config.getNas() + this.map.get(Constant.INPUT_PATH) + this.map.get(Constant.OUTPUT_FILE_NAME));
    }

    public String getOutputPathForCheck() {
        // D:\NAS + \contractRepository\contract_store\HTSP-20190428-1-NO.4\ + & + 130039&rt + & + O2019042812435124696.docx.pdf
        return replaceSeparator(config.getNas() + this.map.get(Constant.INPUT_PATH) + Constant.PATH_SEPARATOR_RT + this.map.get(Constant.ID) + Constant.PATH_SEPARATOR_RT + this.map.get(Constant.SYS) + Constant.PATH_SEPARATOR_RT + this.map.get(Constant.INPUT_FILE_NAME) + ".pdf");
    }

    public String getFilePath() {
        return this.map.get(Constant.FILE_PATH);
    }

    public String getFileName() {
        return this.map.get(Constant.FILE_NAME);
    }

    public String getId() {
        return this.map.get(Constant.ID);
    }

    public String getSystem() {
        return this.map.get(Constant.SYS);
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    private String replaceSeparator(String path) {
        return path.replace("/", "\\");
    }

    public boolean isRtSystem() {
        return Constant.RT.equals(this.getSystem());
    }

    public String getWordToPdfLocalPathXin() {
        return config.getWordToPdfLocalPathXin();
    }

    public String getOutputPathJoinOutputFileName() {
        return this.map.get(Constant.OUTPUT_PATH) + this.map.get(Constant.OUTPUT_FILE_NAME);
    }
    public String getInputPathJoinInputFileName() {
        return this.map.get(Constant.INPUT_PATH) + this.map.get(Constant.INPUT_FILE_NAME);
    }
    /**
     * 解析路径
     *
     * @param accessPath
     * @return
     */
    public abstract void parse(String accessPath);
}
