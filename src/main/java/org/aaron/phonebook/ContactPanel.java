/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aaron.phonebook;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author godwin
 */
public class ContactPanel extends JPanel implements ContactSelectedListener{
    
    private JTextField txtName,txtPhoneNo;
    private JButton btnSave,btnClear;
    private Contact contact=null;
    private RefreshContactListener refreshHandler;
    
    public ContactPanel(){
        
	super.setBounds(100, 100, 450, 300);
        super.setLayout(null);
		
	JLabel lblName = new JLabel("Name");
	lblName.setBounds(40, 14, 40, 15);
	super.add(lblName);
		
	txtName = new JTextField();
	txtName.setBounds(85, 12, 261, 17);
	txtName.setHorizontalAlignment(SwingConstants.LEFT);
	super.add(txtName);
	txtName.setColumns(10);
		
	JLabel lblPhoneNo = new JLabel("Phone No");
	lblPhoneNo.setBounds(12, 38, 68, 15);
	super.add(lblPhoneNo);
		
	txtPhoneNo = new JTextField();
	txtPhoneNo.setBounds(85, 36, 261, 19);
	txtPhoneNo.setHorizontalAlignment(SwingConstants.LEFT);
	super.add(txtPhoneNo);
	txtPhoneNo.setColumns(10);
		
	btnSave = new JButton("Save");
	btnSave.setBounds(85, 67, 117, 25);
	super.add(btnSave);
        
        btnClear = new JButton("Clear");
	btnClear.setBounds(216, 67, 107, 25);
        super.add(btnClear);
        //setup events
        //onClick of save button
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                save(getContact());
                clear();
                //refresh table
                refreshHandler.refreshContactList();
            }
        });
        btnClear.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               clear();
           }
        });
    }
    private void setContactForm(String name, String phone){
        this.txtName.setText(name);
        this.txtPhoneNo.setText(phone);
    }
    private void clear(){
        this.txtName.setText("");
        this.txtPhoneNo.setText("");
        contact=null;
    }
    private Contact getContact(){
        if(contact == null){
            contact=new Contact();
        }
        contact.setName(this.txtName.getText());
        contact.setPhoneNo(this.txtPhoneNo.getText());
        return contact;
    }
    private void save(Contact contact){
        //code to save in db here
        try{
           new ContactDatabase().save(contact);
        }catch(Exception e){
            //
        }
        System.out.println(contact.getId());
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        contact = (Contact)e.getSource();
        this.setContactForm(contact.getName(), contact.getPhoneNo());
    }
    public void setRefreshHandler(RefreshContactListener l){
        this.refreshHandler=l;
    }
}