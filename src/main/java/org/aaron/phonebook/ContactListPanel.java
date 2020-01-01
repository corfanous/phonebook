/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aaron.phonebook;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author godwin
 */
public class ContactListPanel extends JPanel implements RefreshContactListener{
    
    private JTable contactTable;
    private JScrollPane scrollPane;
    private JTextField txtFilter;
    private TableRowSorter sorter;
    TableModel model;
    private ContactSelectedListener contactSelectedListener;
    
    public ContactListPanel(){
        
	GridBagLayout gbl_contentPane = new GridBagLayout();
	gbl_contentPane.columnWidths = new int[]{0, 0};
	gbl_contentPane.rowHeights = new int[]{0, 0, 0};
	gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
	gbl_contentPane.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
	super.setLayout(gbl_contentPane);
	//
	txtFilter = new JTextField();
	GridBagConstraints gbc_textField = new GridBagConstraints();
	gbc_textField.insets = new Insets(0, 0, 5, 0);
	gbc_textField.fill = GridBagConstraints.HORIZONTAL;
	gbc_textField.gridx = 0;
	gbc_textField.gridy = 0;
	super.add(txtFilter, gbc_textField);
	txtFilter.setColumns(10);
        //refreshContactList();
        txtFilter.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filter();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filter();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filter();
            }
        });
        setupTable();
    }
    private void filter(){
        RowFilter<ContactModel, Object> filter;
        try{
            filter=RowFilter.regexFilter("(?i)"+txtFilter.getText(), 0, 1,2);
        }catch(PatternSyntaxException e){
            //
            return;
        }
        sorter.setRowFilter(filter);
    }
    private void execContactSelectedEvent(Contact contact){
        contactSelectedListener.actionPerformed(new ActionEvent(contact, 0, "contact"));
    }
    private ContactModel buildTableData(){
        List<Contact> contacts=new ContactDatabase().findAll();
        return new ContactModel(contacts);
    }
    public void addContactSelectedListener(ContactSelectedListener l){
        this.contactSelectedListener=l;
    }
    private void setupTable(){
        //
        model=buildTableData();
        contactTable=new JTable(model);
        contactTable.removeColumn(contactTable.getColumn("#"));
        contactTable.setAutoCreateRowSorter(true);
        sorter=new TableRowSorter(model);
        contactTable.setRowSorter(sorter);
        //
        List <RowSorter.SortKey> sortKeys=new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
        //
        scrollPane = new JScrollPane(contactTable);
        contactTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row_no=contactTable.getSelectedRow();
                int id = (int)contactTable.getModel().getValueAt(row_no, 0);
                String name=(String)contactTable.getModel().getValueAt(row_no, 1);
                String phone_no=(String)contactTable.getModel().getValueAt(row_no, 2);
                execContactSelectedEvent(new Contact(id, name, phone_no));
            }
            @Override
            public void mousePressed(MouseEvent e) {
                //
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                //
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                //
            }
            @Override
            public void mouseExited(MouseEvent e) {
                //
            }
        });
        //
	GridBagConstraints gbc_scrollPane = new GridBagConstraints();
	gbc_scrollPane.fill = GridBagConstraints.BOTH;
	gbc_scrollPane.gridx = 0;
	gbc_scrollPane.gridy = 1;
	super.add(scrollPane, gbc_scrollPane);
        
    }
    @Override
    public final void refreshContactList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
              DefaultTableModel data=(DefaultTableModel)contactTable.getModel();
               data.setRowCount(0);
               setupTable();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //
    }
    class ContactModel extends AbstractTableModel{
        private List<Contact> contacts;
        private final String[] columns={"#", "Name", "Phone #"};
        
        public ContactModel(List<Contact> contacts){
            this.contacts=contacts;
        }
        @Override
        public int getRowCount() {
            return contacts.size();
        }
        @Override
        public String getColumnName(int col){
            return columns[col];
        }
        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Object val;
            Contact contact=contacts.get(rowIndex);
            switch(columnIndex){
                case 0:
                    val=contact.getId();
                    break;
                case 1:
                    val=contact.getName();
                    break;
                default:
                    val=contact.getPhoneNo();
            }
            return val;
        }
    }
}