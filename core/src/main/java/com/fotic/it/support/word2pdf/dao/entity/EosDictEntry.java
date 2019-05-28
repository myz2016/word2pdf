package com.fotic.it.support.word2pdf.dao.entity;

/**
 * @Author: mfh
 * @Date: 2019-05-06 13:56
 **/
public class EosDictEntry {
    private String dicTypeId;
    private String dicId;
    private String dicName;
    private Integer status;
    private Integer sortNo;
    private Integer rank;
    private String parentId;
    private String seqNo;
    private String filter1;
    private String filter2;

    public String getDicTypeId() {
        return dicTypeId;
    }

    public void setDicTypeId(String dicTypeId) {
        this.dicTypeId = dicTypeId;
    }

    public String getDicId() {
        return dicId;
    }

    public void setDicId(String dicId) {
        this.dicId = dicId;
    }

    public String getDicName() {
        return dicName;
    }

    public void setDicName(String dicName) {
        this.dicName = dicName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getFilter1() {
        return filter1;
    }

    public void setFilter1(String filter1) {
        this.filter1 = filter1;
    }

    public String getFilter2() {
        return filter2;
    }

    public void setFilter2(String filter2) {
        this.filter2 = filter2;
    }

    @Override
    public String toString() {
        return "EosDictEntry{" +
                "dicTypeId='" + dicTypeId + '\'' +
                ", dicId='" + dicId + '\'' +
                ", dicName='" + dicName + '\'' +
                ", status=" + status +
                ", sortNo=" + sortNo +
                ", rank=" + rank +
                ", parentId='" + parentId + '\'' +
                ", seqNo='" + seqNo + '\'' +
                ", filter1='" + filter1 + '\'' +
                ", filter2='" + filter2 + '\'' +
                '}';
    }
}
