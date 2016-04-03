package com.tsystems.jschool.report.entities;

import java.util.List;

public class Contract {

    private String phoneNumber;

    private String name;

    private String surname;

    private String email;

    private String tariff;

    private List<String> contractOptions;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTariff() {
        return tariff;
    }

    public void setTariff(String tariff) {
        this.tariff = tariff;
    }

    public List<String> getContractOptions() {
        return contractOptions;
    }

    public void setContractOptions(List<String> contractOptions) {
        this.contractOptions = contractOptions;
    }
}
