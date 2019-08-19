/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import javax.swing.JFrame;

/**
 *
 * @author DoctorOne
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public void go(){
        JFrame frame = new JFrame();
        ChatPanel panel = new ChatPanel("TheDoctorOne","MahmutHuseyinKocas"); //Pass the users via constructor as Strings
        frame.add(panel);
        frame.setVisible(true);
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(3);
        
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        new Main().go();
    }
    
}
