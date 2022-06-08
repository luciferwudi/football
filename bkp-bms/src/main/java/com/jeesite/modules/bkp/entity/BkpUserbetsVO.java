package com.jeesite.modules.bkp.entity;

public class BkpUserbetsVO {
    private String id;
    private String createtime;		// 创建时间
    private String bettingmethod;		// 玩法
    private String pick;		// 用户选择
    private String matchresults;		// 此次比赛结果
    private Long betamount;		// 投注金额
    private String earnings;		// 盈利预期
    private Long multiple;		// 倍数
    private String totalloss;		// 当前批次亏损金额
    private String profitratioafterdeductinglosses;		// 扣除亏损盈利比例
    private String profitafterdeductinglosses;		// 扣除亏损盈利金额
    private String currentprofitratio;		// 当期最大盈利比
    private String currentprofitamount;		// 当期最大盈利金额
    private Long   combination;
    private String tzjg;
    private String gdmsmc;
    private Integer dqpc;
    private String dqpcxh;
    private String dqykje;
    private String llzg;
    private String pctyj;
    private String pctylbl;

    public String getLlzg() {
        return llzg;
    }

    public void setLlzg(String llzg) {
        this.llzg = llzg;
    }

    public String getPctyj() {
        return pctyj;
    }

    public void setPctyj(String pctyj) {
        this.pctyj = pctyj;
    }

    public String getPctylbl() {
        return pctylbl;
    }

    public void setPctylbl(String pctylbl) {
        this.pctylbl = pctylbl;
    }

    public String getXzsm() {
        return xzsm;
    }

    public void setXzsm(String xzsm) {
        this.xzsm = xzsm;
    }

    private String xzsm;

    public String getDqykje() {
        return dqykje;
    }

    public void setDqykje(String dqykje) {
        this.dqykje = dqykje;
    }

    public String getGdmsmc() {
        return gdmsmc;
    }

    public void setGdmsmc(String gdmsmc) {
        this.gdmsmc = gdmsmc;
    }

    public Integer getDqpc() {
        return dqpc;
    }

    public void setDqpc(Integer dqpc) {
        this.dqpc = dqpc;
    }

    public String getDqpcxh() {
        return dqpcxh;
    }

    public void setDqpcxh(String dqpcxh) {
        this.dqpcxh = dqpcxh;
    }

    public String getTzjg() {
        return tzjg;
    }

    public void setTzjg(String tzjg) {
        this.tzjg = tzjg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCombination() {
        return combination;
    }

    public void setCombination(Long combination) {
        this.combination = combination;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getBettingmethod() {
        return bettingmethod;
    }

    public void setBettingmethod(String bettingmethod) {
        this.bettingmethod = bettingmethod;
    }

    public String getPick() {
        return pick;
    }

    public void setPick(String pick) {
        this.pick = pick;
    }

    public String getMatchresults() {
        return matchresults;
    }

    public void setMatchresults(String matchresults) {
        this.matchresults = matchresults;
    }

    public Long getBetamount() {
        return betamount;
    }

    public void setBetamount(Long betamount) {
        this.betamount = betamount;
    }

    public String getEarnings() {
        return earnings;
    }

    public void setEarnings(String earnings) {
        this.earnings = earnings;
    }

    public Long getMultiple() {
        return multiple;
    }

    public void setMultiple(Long multiple) {
        this.multiple = multiple;
    }

    public String getTotalloss() {
        return totalloss;
    }

    public void setTotalloss(String totalloss) {
        this.totalloss = totalloss;
    }

    public String getProfitratioafterdeductinglosses() {
        return profitratioafterdeductinglosses;
    }

    public void setProfitratioafterdeductinglosses(String profitratioafterdeductinglosses) {
        this.profitratioafterdeductinglosses = profitratioafterdeductinglosses;
    }

    public String getProfitafterdeductinglosses() {
        return profitafterdeductinglosses;
    }

    public void setProfitafterdeductinglosses(String profitafterdeductinglosses) {
        this.profitafterdeductinglosses = profitafterdeductinglosses;
    }

    public String getCurrentprofitratio() {
        return currentprofitratio;
    }

    public void setCurrentprofitratio(String currentprofitratio) {
        this.currentprofitratio = currentprofitratio;
    }

    public String getCurrentprofitamount() {
        return currentprofitamount;
    }

    public void setCurrentprofitamount(String currentprofitamount) {
        this.currentprofitamount = currentprofitamount;
    }
}
