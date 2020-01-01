/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aaron.phonebook;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author godwin
 */
public class PhoneBookApp {
    public static void main(String[] args){
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    PhoneBookFrame frame=new PhoneBookFrame();
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setSize(1000, 600);
                    //frame.pack();
                    frame.setVisible(true);
                }
            });
        } catch (InterruptedException ex) {
            Logger.getLogger(PhoneBookApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(PhoneBookApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
