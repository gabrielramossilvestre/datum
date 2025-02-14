package br.com.datum.prova.dto;

import br.com.datum.prova.entities.Customer;

public class CustomerDto {
    private String name;
    private String address;
    private String email;

    public CustomerDto(){}

    public CustomerDto(String name, String address, String email) {
        this.name = name;
        this.address = address;
        this.email = email;
    }

    public CustomerDto(Customer customer) {
        this.name = customer.getName();
        this.address = customer.getAddress();
        this.email = customer.getEmail();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
