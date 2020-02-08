package com.test.model;

import com.test.model.xml.Currency;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(precision = 11, scale = 2)
    private BigDecimal balance;

    @OneToOne
    @JoinColumn(name = "currency_fk")
    private Currency currency;

    @OneToOne
    @JoinColumn(name = "user_fk", unique = true)
    private User user;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "id=" + id +
                ", balance=" + balance +
                ", currency=" + currency +
                ", user=" + user +
                '}';
    }
}
