package com.fotic.it.support.word2pdf.manager.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WordPdfResultDto", namespace = "http://dto.webservice.iss.com", propOrder = {
        "date",
        "id",
        "message",
        "resultType",
        "sys"
})
public class WordPdfResultDto {

    @XmlElement(namespace = "http://dto.webservice.iss.com", nillable = true)
    protected String date;
    @XmlElement(namespace = "http://dto.webservice.iss.com", nillable = true)
    protected String id;
    @XmlElement(namespace = "http://dto.webservice.iss.com", nillable = true)
    protected String message;
    @XmlElement(namespace = "http://dto.webservice.iss.com", nillable = true)
    protected String resultType;
    @XmlElement(namespace = "http://dto.webservice.iss.com", nillable = true)
    protected String sys;

    /**
     * 获取date属性的值。
     *
     * @return possible object is
     * {@link String }
     */
    public String getDate() {
        return date;
    }

    /**
     * 设置date属性的值。
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDate(String value) {
        this.date = value;
    }

    /**
     * 获取id属性的值。
     *
     * @return possible object is
     * {@link String }
     */
    public String getId() {
        return id;
    }

    /**
     * 设置id属性的值。
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * 获取message属性的值。
     *
     * @return possible object is
     * {@link String }
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置message属性的值。
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * 获取resultType属性的值。
     *
     * @return possible object is
     * {@link String }
     */
    public String getResultType() {
        return resultType;
    }

    /**
     * 设置resultType属性的值。
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setResultType(String value) {
        this.resultType = value;
    }

    /**
     * 获取sys属性的值。
     *
     * @return possible object is
     * {@link String }
     */
    public String getSys() {
        return sys;
    }

    /**
     * 设置sys属性的值。
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSys(String value) {
        this.sys = value;
    }

    @Override
    public String toString() {
        return "WordPdfResultDto{" +
                "date='" + date + '\'' +
                ", id='" + id + '\'' +
                ", message='" + message + '\'' +
                ", resultType='" + resultType + '\'' +
                ", sys='" + sys + '\'' +
                '}';
    }
}
