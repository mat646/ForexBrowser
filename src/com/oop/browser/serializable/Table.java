package com.oop.browser.serializable;

import java.io.Serializable;
import java.util.Date;

/**
 * POJO for JSON received from nbp.api.pl
 * @see com.oop.browser.builders.modules.NBPWebAPIClient
 * @see java.io.Serializable
 */
public class Table implements Serializable {

    private String table;
    private String currency;
    private String code;
    private String no;
    private Date tradingDate;
    private Date effectiveDate;
    private Rate[] rates;

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
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

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Date getTradingDate() {
        return tradingDate;
    }

    public void setTradingDate(Date tradingDate) {
        this.tradingDate = tradingDate;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Rate[] getRates() {
        return rates;
    }

    public void setRates(Rate[] rates) {
        this.rates = rates;
    }

}
