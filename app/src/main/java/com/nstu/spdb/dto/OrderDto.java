package com.nstu.spdb.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDto implements Serializable {
    private Long orderId;
    private ClientDto client;
    private Date createDate;
    private Date closeDate;
    private List<CargoDto> cargos;
    private String statusTitle;

    public String getStatusTitle() {
        return statusTitle;
    }

    public void setStatusTitle(String statusTitle) {
        this.statusTitle = statusTitle;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setCargos(List<CargoDto> cargos) {
        this.cargos = cargos;
    }

    public ClientDto getClient() {
        return client;
    }

    public void setClient(ClientDto client) {
        this.client = client;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public List<CargoDto> getCargos() {
        if (cargos == null) {
            cargos = new ArrayList<>();
        }

        return cargos;
    }
}