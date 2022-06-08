package com.jeesite.modules.utils;

import com.jeesite.common.utils.excel.annotation.ExcelField;
import com.jeesite.common.utils.excel.annotation.ExcelFields;
import com.jeesite.common.utils.excel.fieldtype.CompanyType;
import com.jeesite.common.utils.excel.fieldtype.OfficeType;

import javax.validation.Valid;

public class MemberVo {

    private String ls;

    private String sj;

    private String zd;

    private String kd;

    private String xs;

    private String szcl;

    private String pzcl;

    private String fzcl;

    private String total;

    private String single;

    public String getSingle() {
        return single;
    }

    public void setSingle(String single) {
        this.single = single;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSzcl() {
        return szcl;
    }

    public void setSzcl(String szcl) {
        this.szcl = szcl;
    }

    public String getPzcl() {
        return pzcl;
    }

    public void setPzcl(String pzcl) {
        this.pzcl = pzcl;
    }

    public String getFzcl() {
        return fzcl;
    }

    public void setFzcl(String fzcl) {
        this.fzcl = fzcl;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    private String num;


    @Valid
    @ExcelFields({
            @ExcelField(title="联赛", attrName="ls", align= ExcelField.Align.CENTER, sort=30),
            @ExcelField(title="次序", attrName="num", align= ExcelField.Align.LEFT, sort=50),
            @ExcelField(title="时间", attrName="sj", align= ExcelField.Align.CENTER, sort=40),
            @ExcelField(title="主队", attrName="zd", align= ExcelField.Align.LEFT, sort=50),
            @ExcelField(title="客队", attrName="kd", align= ExcelField.Align.LEFT, sort=50),
            @ExcelField(title="胜支持数", attrName="szcl", align= ExcelField.Align.LEFT, sort=50),
            @ExcelField(title="平支持数", attrName="pzcl", align= ExcelField.Align.LEFT, sort=50),
            @ExcelField(title="负支持数", attrName="fzcl", align= ExcelField.Align.LEFT, sort=50),
            @ExcelField(title="系数", attrName="xs", align= ExcelField.Align.CENTER, sort=70),
            @ExcelField(title="是否有单关", attrName="single", align= ExcelField.Align.CENTER, sort=70),
            @ExcelField(title="总数", attrName="total", align= ExcelField.Align.CENTER, sort=70),
    })
    public MemberVo getMemberVo(){
        MemberVo memberVo=new MemberVo();
        return memberVo;
    }

    public String getLs() {
        return ls;
    }

    public void setLs(String ls) {
        this.ls = ls;
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

    public String getXs() {
        return xs;
    }

    public void setXs(String xs) {
        this.xs = xs;
    }
}
