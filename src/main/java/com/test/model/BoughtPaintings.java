package com.test.model;

import com.test.enums.BoughtStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class BoughtPaintings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private User user;

    @OneToOne
    private Painting painting;
    private Date date;
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private BoughtStatus status;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Painting getPainting() {
        return painting;
    }

    public void setPainting(Painting painting) {
        this.painting = painting;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BoughtStatus getStatus() {
        return status;
    }

    public void setStatus(BoughtStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BoughtPaintings{" +
                "id=" + id +
                ", user=" + user +
                ", painting=" + painting +
                ", date=" + date +
                ", price=" + price +
                ", status=" + status +
                '}';
    }
}
