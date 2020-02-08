package com.test.model;

import com.test.model.xml.Currency;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
public class SystemWallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "currency_fk")
    private Currency currency;

    @Column(precision = 11, scale = 2)
    private BigDecimal balance;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "SystemWallet{" +
                "id=" + id +
                ", currency=" + currency +
                ", balance=" + balance +
                '}';
    }
}
