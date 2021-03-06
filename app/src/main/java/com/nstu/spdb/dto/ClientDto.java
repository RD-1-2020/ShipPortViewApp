package com.nstu.spdb.dto;

import java.io.Serializable;

public class ClientDto implements Serializable {
    private Long id;
    private String fullName;

    public ClientDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
