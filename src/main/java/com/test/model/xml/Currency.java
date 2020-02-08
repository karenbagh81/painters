package com.test.model.xml;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "CcyNtry")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Currency {
    @Id
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "increment")
    private int id;

    @XmlElement(name = "CtryNm")
    private String country;

    @XmlElement(name = "CcyNm")
    private String currencyName;

    @XmlElement(name = "Ccy")
    private String currency;

    @XmlElement(name = "CcyNbr")
    private int curencyNumber;

    @XmlElement(name = "CcyMnrUnts")
    private int minorUnit;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getCurencyNumber() {
        return curencyNumber;
    }

    public void setCurencyNumber(int curencyNumber) {
        this.curencyNumber = curencyNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMinorUnit() {
        return minorUnit;
    }

    public void setMinorUnit(int minorUnit) {
        this.minorUnit = minorUnit;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", currencyName='" + currencyName + '\'' +
                ", currency='" + currency + '\'' +
                ", curencyNumber=" + curencyNumber +
                ", minorUnit=" + minorUnit +
                '}';
    }
}
