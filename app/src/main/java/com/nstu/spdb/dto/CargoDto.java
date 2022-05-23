package com.nstu.spdb.dto;


import java.io.Serializable;

public class CargoDto implements Serializable {
    private Long id;
    private Long weight;
    private String title;
    private InvoiceDto invoice;
    //TODO STATUS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public InvoiceDto getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceDto invoice) {
        this.invoice = invoice;
    }
}
