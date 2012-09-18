package com.cabletech.baseinfo.beans;

public class PhoneBook{
    private String simNumber = "";
    private String name = "";
    private String phone = "";
    public PhoneBook(){
    }


    public void setSimNumber( String simNumber ){
        this.simNumber = simNumber;
    }


    public void setName( String name ){
        this.name = name;
    }


    public void setPhone( String phone ){
        this.phone = phone;
    }


    public String getSimNumber(){
        return simNumber;
    }


    public String getName(){
        return name;
    }


    public String getPhone(){
        return phone;
    }
}
