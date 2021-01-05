package com.hundeklemmen.superpay.classes;

public class TransactionRequest {
    private String _id;
    private String spiller;
    private double amount;
    private String pakke;
    private String server;
    private String key;

    public String getId(){
        return this._id;
    }
    public double getAmount() {
        return this.amount;
    }
    public String getPakke(){
        return this.pakke;
    }
    public String getServer(){
        return this.server;
    }
    public String getKey(){
        return this.key;
    }
}
