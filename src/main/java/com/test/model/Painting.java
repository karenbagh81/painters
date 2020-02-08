package com.test.model;


import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Painting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String path;
    private boolean sold;

    @Column(unique = true)
    private String confirmCode;

    @Column(precision = 11, scale = 2)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "painter_fk")
    private Painter painter;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public Painter getPainter() {
        return painter;
    }

    public void setPainter(Painter painter) {
        this.painter = painter;
    }

    public String getConfirmCode() {
        return confirmCode;
    }

    public void setConfirmCode(String confirmCode) {
        this.confirmCode = confirmCode;
    }

    @Override
    public String toString() {
        return "Painting{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", sold=" + sold +
                ", confirmCode='" + confirmCode + '\'' +
                ", price=" + price +
                ", painter=" + painter + '}';
    }
}
