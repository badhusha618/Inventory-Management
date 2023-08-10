package com.makinus.unitedsupplies.common.data.reftype;

public enum PaymentType {
    ADVANCE_PAYMENT("A", "Advance Payment"),
    FULL_PAYMENT("F", "Full Payment");

    private String status;
    private String display;

    PaymentType(String status, String display) {
        this.status = status;
        this.display = display;
    }

    public String getStatus() {
        return status;
    }

    public String getDisplay() {
        return display;
    }

    public static PaymentType statusMatch(String status) {
        for (PaymentType payment : PaymentType.values()) {
            if (payment.getStatus().equalsIgnoreCase(status)) {
                return payment;
            }
        }
        return ADVANCE_PAYMENT;
    }

    @Override
    public String toString() {
        return "PaymentType values - "
                + ADVANCE_PAYMENT.getDisplay()
                + " "
                + FULL_PAYMENT.getDisplay();
    }
}
