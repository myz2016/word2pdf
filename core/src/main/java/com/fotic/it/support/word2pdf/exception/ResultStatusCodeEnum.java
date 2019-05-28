package com.fotic.it.support.word2pdf.exception;

/**
 * @Author: mfh
 * @Date: 2019-04-26 14:25
 **/
public enum ResultStatusCodeEnum {
    /**
     * 转换成功
     */
    SUCCESS(0, "成功"),
    /**
     * 转换失败
     */
    FAIL(1, "失败"),
    /**
     * OFFICE软件未安装
     */
    SOFTWARE_UNINSTALL(2, "OFFICE软件未安装"),
    /**
     * "OFFICE版本太低"
     */
    SOFTWARE_VERSION_TOO_LOW(3, "OFFICE版本太低"),
    /**
     * 文档不存在
     */
    FILE_NOT_EXIST(4, "文档不存在"),

    ILLEGAL_PARAMETER(5, "非法参数"),

    PARAMETER_EXIST_SPACE(6, "文件名或者路径中存在空格"),

    ILLEGAL_END_PATH(7, "非法路径结尾"),

    WEBSERVICE_EXCEPTION(8, "webservice 异常"),

    DUPLICATE_FILE_NAME(9, "文件名重复")
    ;
    private int code;
    private String description;

    ResultStatusCodeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public static String getDescription(int code) {
        for (ResultStatusCodeEnum eachState : values()) {
            if(code == eachState.code){
                return eachState.description;
            }
        }
        return FAIL.description;
    }
}