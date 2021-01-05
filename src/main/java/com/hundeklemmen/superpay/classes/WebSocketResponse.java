package com.hundeklemmen.superpay.classes;

import jline.internal.Nullable;

public class WebSocketResponse {

    private String type;

    @Nullable
    private String error;

    @Nullable
    private String message;

    @Nullable
    private double balance;

    @Nullable
    private TransactionRequest anmodning;

    @Nullable
    private boolean verified;

    public String getType(){
        return this.type;
    }
    public String getError() {
        return this.error;
    }
    public double getBalance(){
        return this.balance;
    }
    public TransactionRequest getAnmodning(){
        return this.anmodning;
    }
    public boolean getVerified(){
        return this.verified;
    }
    public String getMessage(){
        return this.message;
    }
}
