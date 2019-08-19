/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DoctorOne
 */
public class Connection {
    private String root = "root";
    private String pass = "";
    private String host = "localhost";
    private String db_name = "chat";
    
    private int port = 3306;
    
    private java.sql.Connection con = null;
    private Statement stmt = null;
    
    private String url = "jdbc:mysql://" + host + ":" + port + "/" + db_name + "?useUnicode=true&characterEncoding=utf8"  ;
                         
    public Connection () {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("WHERE THE HELL IS THE DRIVER!?");
        }
		
        try {
            con = DriverManager.getConnection(url,root,pass);
            System.out.println("Connection Established.");
        } catch (SQLException ex) {
            System.out.println("Connection Failed.");
            System.exit(0);
        }
    }
    
    public String getMessagesById(int MessageId){
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM messages WHERE MessageId = "+ MessageId;
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            String Message;
            if(rs.first()){
                Message = rs.getString("Message");
                return Message;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ResultSet getMessagesByName(String User1,String User2){
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM messages WHERE User1 = ? AND User2 = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, User1);
            ps.setString(2, User2);
            rs = ps.executeQuery();
            
            if(!rs.first()){
                sql = "SELECT * FROM messages WHERE User1 = ? AND User2 = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, User2);
                ps.setString(2, User1);
                rs = ps.executeQuery();
                
                if(!rs.first()){
                    sql = "SELECT * FROM messages WHERE User1 = ? AND User2 = ?";
                    OpenChat(User1, User2);
                    ps = con.prepareStatement(sql);
                    ps.setString(1, User1);
                    ps.setString(2, User2);
                    rs = ps.executeQuery();
                }
            }
            
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public void sendMessageById(int MessageId,String Message){
        try {
            String sql = "UPDATE messages SET Message = ? WHERE MessageId = "+MessageId;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, Message);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void OpenChat(String User1,String User2){
        String html = "<html>\r\n  <head>\r\n\r\n  </head>\r\n  <body>  </body></html>\r\n";
        try {
            String sql = "INSERT INTO messages VALUES(NULL,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, User1);
            ps.setString(2, User2);
            ps.setString(3, html);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
