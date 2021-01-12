package com.hundeklemmen.superpay.classes;

public class Server {

    private String _id;
    private String name;
    private String description1;
    private String description2;
    private String owner;
    private Integer online;
    private Integer max;
    private String customImage;
    private boolean partner = false;

    public String getName(){
        return this.name;
    }
    public String getDescription1(){
        return this.description1;
    }
    public String getDescription2(){
        return this.description2;
    }
    public String getOwner(){
        return this.owner;
    }
    public Integer getOnline(){
        return this.online;
    }
    public Integer getMax(){
        return this.max;
    }
    public String getCustomImage(){
        return this.customImage;
    }
    public boolean isPartner(){
        return this.partner;
    }
}
