package com.test.enums;

public enum BoughtStatus {

    PREPAYMENT("prepayment"),
    FINISHED("finished");

    private String Value;

    BoughtStatus(String value) {
        this.Value = value;
    }
}
