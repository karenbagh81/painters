package com.test.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "CcyTbl")
@XmlAccessorType(XmlAccessType.FIELD)
public class CurrencyArray {

    @XmlElement(name = "CcyNtry")
    private List<Currency> currencies;

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }


    public String toString() {
        return "CurrencyArray{" +
                "currencies=" + currencies +
                '}';
    }
}
