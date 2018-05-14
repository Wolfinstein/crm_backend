package com.crm.application.utilModels;

public class ClientsPerMonth {

    private String name;

    private Integer value;

    public ClientsPerMonth(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public ClientsPerMonth() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
