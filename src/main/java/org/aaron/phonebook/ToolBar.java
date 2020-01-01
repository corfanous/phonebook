/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aaron.phonebook;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author godwin
 */
public class ToolBar extends JPanel{
    
    JButton addContact, deleteContact;
    
    public ToolBar(){
        
       addContact=new JButton("Add");
       deleteContact=new JButton("Delete");
       
        setLayout(new FlowLayout(FlowLayout.LEFT));
        
        //add(addContact);
        add(deleteContact);
    }
}
