package com.oop.browser.serializable;

import java.io.Serializable;

public class Table implements Serializable {

    private String table;
    private String currency;
    private String code;
    private String no;
    private String tradingDate;
    private String effectiveDate;
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

    public String getTradingDate() {
        return tradingDate;
    }

    public void setTradingDate(String tradingDate) {
        this.tradingDate = tradingDate;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Rate[] getRates() {
        return rates;
    }

    public void setRates(Rate[] rates) {
        this.rates = rates;
    }

}
