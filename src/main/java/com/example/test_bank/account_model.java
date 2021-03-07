package com.example.test_bank;

public class account_model {
    private int id;
    private String iban;
    private String name;
    private String currency;
    private String amount;

    public account_model(int id, String iban, String name, String currency, String amount) {
        this.id = id;
        this.iban = iban;
        this.name = name;
        this.currency = currency;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "account_model{" +
                "id=" + id +
                ", iban='" + iban + '\'' +
                ", name='" + name + '\'' +
                ", currency='" + currency + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }

    public account_model() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
