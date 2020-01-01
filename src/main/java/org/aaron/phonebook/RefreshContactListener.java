/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aaron.phonebook;

import java.awt.event.ActionListener;
import javax.swing.JTable;

/**
 *
 * @author godwin
 */
interface RefreshContactListener extends ActionListener{
    void refreshContactList();
}
