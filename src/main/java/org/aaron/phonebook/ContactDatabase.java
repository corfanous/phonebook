/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aaron.phonebook;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.jdbc.EmbeddedDriver;

/**
 *
 * @author godwin
 */
public class ContactDatabase {
    
    Connection conn=null;
    String connectionUrl="jdbc:derby:contact_db;create=true";
    String connectionUser="arron";
    String connectionPassword="arron_1";
    
    public ContactDatabase(){
        //setup connection and create contact table
        init();
    }
    
    private void init(){
        try{
            Driver derby=new EmbeddedDriver();
            DriverManager.registerDriver(derby);
            conn=DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
            conn.setAutoCommit(true);
            //
            createTables();
        }catch(SQLException ex){
            Logger.getLogger(ContactDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void createTables(){
        
        String CREATE_CONTACT_TABLE =
                "create table contact( "+
                  "id integer not null generated always as identity (start with 1, increment by 1),"+
                  "fullname varchar(200) not null,"+
                  "phone_no varchar(20),"+
                  "constraint primary_key primary key (id) "+
                ")";
        try {
            Statement stmt=conn.createStatement();
            stmt.execute(CREATE_CONTACT_TABLE);
        } catch (SQLException ex) {
            Logger.getLogger(ContactDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void save(Contact contact){
        String query="insert into contact(fullname,phone_no) values(?,?)";
        try{
            if(contact.getId()>0){
                //update operation
                query="update contact set fullname=?, phone_no=? where id="+contact.getId();
            }
            PreparedStatement stmt=conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, contact.getName());
            stmt.setString(2, contact.getPhoneNo());
            stmt.executeUpdate();
            //contact.setId(1);
            ResultSet rs=stmt.getGeneratedKeys();
            while(rs.next()){
                contact.setId(rs.getInt(1));
            }
        }catch(SQLException ex){
            //if( DerbyHelper.tableAlreadyExists( e ) ) {
              //return; // That's OK
            //}
            Logger.getLogger(ContactDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public List<Contact> findAll(){
        List<Contact> contacts=new ArrayList<>();
        try {
            Statement stmt=conn.createStatement();
            String query="select id, fullname, phone_no from contact";
            ResultSet rs=stmt.executeQuery(query);
            
            while(rs.next()){
                contacts.add(new Contact(rs.getInt("id"), rs.getString("fullname"), rs.getString("phone_no")));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ContactDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return contacts;
    }
    public void delete(int id){
        
    }
}
