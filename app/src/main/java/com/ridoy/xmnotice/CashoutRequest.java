package com.ridoy.xmnotice;

public class CashoutRequest {
    String bkash,cashoutpoint;

    public CashoutRequest() {
    }

    public CashoutRequest(String bkash, String cashoutpoint) {
        this.bkash = bkash;
        this.cashoutpoint = cashoutpoint;
    }

    public String getBkash() {
        return bkash;
    }

    public void setBkash(String bkash) {
        this.bkash = bkash;
    }

    public String getCashoutpoint() {
        return cashoutpoint;
    }

    public void setCashoutpoint(String cashoutpoint) {
        this.cashoutpoint = cashoutpoint;
    }
}
