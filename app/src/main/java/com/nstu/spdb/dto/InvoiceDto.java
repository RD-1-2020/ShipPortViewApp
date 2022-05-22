package com.nstu.spdb.dto;

import com.nstu.spdb.enums.InvoiceType;

import java.io.Serializable;
import java.util.Date;

public class InvoiceDto implements Serializable {
    private Long id;
    private String title;
    private String number;
    private InvoiceType type;
    private Date dateSupply;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public InvoiceType getType() {
        return type;
    }

    public void setType(InvoiceType type) {
        this.type = type;
    }

    public Date getDateSupply() {
        return dateSupply;
    }

    public void setDateSupply(Date dateSupply) {
        this.dateSupply = dateSupply;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
