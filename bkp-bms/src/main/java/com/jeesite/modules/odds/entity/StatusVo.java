package com.jeesite.modules.odds.entity;

import java.util.Map;

public class StatusVo {
    private String maxcount;
    private String last_updated;
    private Map allup;

    public String getMaxcount() {
        return maxcount;
    }

    public void setMaxcount(String maxcount) {
        this.maxcount = maxcount;
    }

    public String getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(String last_updated) {
        this.last_updated = last_updated;
    }

    public Map getAllup() {
        return allup;
    }

    public void setAllup(Map allup) {
        this.allup = allup;
    }
}
