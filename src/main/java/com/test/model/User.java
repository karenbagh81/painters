package com.test.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class User extends AbstractModel {

    @ManyToMany
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private List<Authority> authority;

    @OneToOne(mappedBy = "user")
    private Painter painter;

    @OneToOne(mappedBy = "user")
    private Wallet wallet;


    public List<Authority> getAuthority() {
        return authority;
    }

    public void setAuthority(List<Authority> authority) {
        this.authority = authority;
    }

    public Painter getPainter() {
        return painter;
    }

    public void setPainter(Painter painter) {
        this.painter = painter;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    @Override
    public String toString() {
        return "User{" +
                "authority=" + authority +
                ", painter=" + painter +
                ", wallet=" + wallet +
                '}';
    }
}
