package com.makinus.unitedsupplies.common.data.reftype;

public enum PaymentStatus {
    PAYMENT_SUCCESS("S", "TXN_SUCCESS", "Payment Success"),
    PAYMENT_PENDING("P", "PENDING", "Payment Pending"),
    PAYMENT_FAILED("F", "TXN_FAILURE", "Payment Failed"),
    NOT_PAID("N", "NOT_PAID", "Not Paid");

    private String status;
    private String value;
    private String display;


    PaymentStatus(String status, String value, String display) {
        this.status = status;
        this.value = value;
        this.display = display;
    }

    public String getStatus() {
        return status;
    }

    public String getDisplay() {
        return display;
    }

    public String getValue() {
        return value;
    }

    public static PaymentStatus statusMatch(String status) {
        for (PaymentStatus payment : PaymentStatus.values()) {
            if (payment.getStatus().equalsIgnoreCase(status)) {
                return payment;
            }
        }
        return PAYMENT_FAILED;
    }

    public static PaymentStatus valueMatch(String value) {
        for (PaymentStatus payment : PaymentStatus.values()) {
            if (payment.getValue().equalsIgnoreCase(value)) {
                return payment;
            }
        }
        return PAYMENT_FAILED;
    }

    public static PaymentStatus displayMatch(String display) {
        for (PaymentStatus payment : PaymentStatus.values()) {
            if (payment.getDisplay().equalsIgnoreCase(display)) {
                return payment;
            }
        }
        return PAYMENT_FAILED;
    }
    @Override
    public String toString() {
        return "PaymentStatus{" +
                "status='" + status + '\'' +
                ", display='" + display + '\'' +
                '}';
    }
}
