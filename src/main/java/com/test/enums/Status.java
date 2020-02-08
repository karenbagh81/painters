package com.test.enums;

public enum Status {

    VERIFIED(0), UNVERIFIED(1);

    private int Value;

    Status(int value) {
        Value = value;
    }
}
