package com.test.model;


import javax.persistence.*;
import java.util.List;

@Entity
public class Painter extends AbstractModel {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "painter_fk")
    private List<Painting> paintings;

    @OneToOne
    @JoinColumn(name = "user_fk")
    private User user;

    public List<Painting> getPaintings() {
        return paintings;
    }

    public void setPaintings(List<Painting> paintings) {
        this.paintings = paintings;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Painter{" +
                "paintings=" + paintings +
                ", user=" + user +
                '}';
    }
}
