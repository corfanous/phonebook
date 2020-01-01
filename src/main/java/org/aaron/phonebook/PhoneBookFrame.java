/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aaron.phonebook;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JSplitPane;

/**
 *
 * @author godwin
 */
public class PhoneBookFrame extends JFrame{
    
    private JSplitPane splitPane;
    
    public PhoneBookFrame(){
        
        super("Phone Book");
        
        super.setLayout(new BorderLayout());
        
        super.add(new ToolBar(), BorderLayout.NORTH);
        ContactPanel contactPanel=new ContactPanel();
        ContactListPanel contactListPanel=new ContactListPanel();
        contactListPanel.addContactSelectedListener(contactPanel);
        //
        contactPanel.setRefreshHandler(contactListPanel);
        splitPane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,contactListPanel,contactPanel);
        super.add(splitPane,BorderLayout.CENTER);
    }
}