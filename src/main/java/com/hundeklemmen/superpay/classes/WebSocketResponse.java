package com.hundeklemmen.superpay.classes;

import jline.internal.Nullable;

public class WebSocketResponse {

    private String type;

    @Nullable
    private String error;

    @Nullable
    private double balance;

    @Nullable
    private TransactionRequest anmodning;

    public String getType(){
        return this.type;
    }
    public String getError() {
        return this.error;
    }
    public double getBalance(){
        return this.balance;
    }
}
