package com.common.nayong.enumerate;

public enum PaymentType {
    creditCard(1, "credit_card"),
    paypal(2, "paypal"),
    applePay(3, "apple_pay"),
    googlePay(4, "google_pay");

    public final int key;
    public final String name;

    PaymentType(int key, String name) {
        this.key = key;
        this.name = name;
    }

    public String toString() { return name; }
}
