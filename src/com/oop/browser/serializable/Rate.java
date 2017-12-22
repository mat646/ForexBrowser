package com.oop.browser.serializable;

import java.io.Serializable;
import java.util.Date;

/**
 * POJO for JSON received from nbp.api.pl
 * @see com.oop.browser.builders.modules.NBPWebAPIClient
 * @see java.io.Serializable
 */
public class Rate implements Serializable {

    private String no;
    private Date effectiveDate;
    private Double bid;
    private Double ask;
    private String currency;
    private String code;
    private Double mid;

    public Double getMid() {
        return mid;
    }

    public void setMid(Double mid) {
        this.mid = mid;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Double getBid() {
        return bid;
    }

    public void setBid(Double bid) {
        this.bid = bid;
    }

    public Double getAsk() {
        return ask;
    }

    public void setAsk(Double ask) {
        this.ask = ask;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
