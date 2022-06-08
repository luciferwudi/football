package com.jeesite.modules.vo;


import com.jeesite.common.utils.excel.annotation.ExcelField;
import com.jeesite.common.utils.excel.annotation.ExcelFields;

import javax.validation.Valid;


public class RqVo {
    private String ls;
    private String num;
    private String sj;
    private String zd;
    private String kd;


    @Valid
    @ExcelFields({
            @ExcelField(title="联赛", attrName="ls", align= ExcelField.Align.CENTER, sort=30),
            @ExcelField(title="次序", attrName="num", align= ExcelField.Align.LEFT, sort=50),
            @ExcelField(title="时间", attrName="sj", align= ExcelField.Align.CENTER, sort=40),
            @ExcelField(title="主队", attrName="zd", align= ExcelField.Align.LEFT, sort=50),
            @ExcelField(title="客队", attrName="kd", align= ExcelField.Align.LEFT, sort=50),
            @ExcelField(title="让球", attrName="rq", align= ExcelField.Align.LEFT, sort=50),
            @ExcelField(title="结果", attrName="result", align= ExcelField.Align.LEFT, sort=50),

    })
    public RqVo getrqVo(){
        RqVo rqVo=new RqVo();
        return rqVo;
    }

    public String getLs() {
        return ls;
    }

    public void setLs(String ls) {
        this.ls = ls;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getSj() {
        return sj;
    }

    public void setSj(String sj) {
        this.sj = sj;
    }

    public String getZd() {
        return zd;
    }

    public void setZd(String zd) {
        this.zd = zd;
    }

    public String getKd() {
        return kd;
    }

    public void setKd(String kd) {
        this.kd = kd;
    }

    public String getRq() {
        return rq;
    }

    public void setRq(String rq) {
        this.rq = rq;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    private String rq;
    private String result;
}
