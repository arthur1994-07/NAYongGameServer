package com.common.nayong.enumerate;

public enum PaymentType {
    creditCard(1, "credit_card"),
    paypal(2, "paypal");

    public final int code;
    public final String name;

    PaymentType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public String toString() { return name; }
}
