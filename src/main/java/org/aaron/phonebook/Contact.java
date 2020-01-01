/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aaron.phonebook;

/**
 *
 * @author godwin
 */
public class Contact {
    
    private int id;
    private String name;
    private String phoneNo;
    
    public Contact(){
        id=0;
        name="";
        phoneNo="";
    }
    public Contact(String name, String phoneNo){
        this.name=name;
        this.phoneNo=phoneNo;
    }
    public Contact(int id, String name, String phoneNo){
        this.id=id;
        this.name=name;
        this.phoneNo=phoneNo;
    }
    
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getPhoneNo(){
        return phoneNo;
    }
    public void setPhoneNo(String phoneNo){
        this.phoneNo=phoneNo;
    }
}
