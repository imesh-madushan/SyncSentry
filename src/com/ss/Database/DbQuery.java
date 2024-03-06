package com.ss.Database;

import javax.swing.*;
import java.awt.*;
import com.mysql.jdbc.*;
import com.ss.Home.HomeInterface;
import com.ss.RemoteConnection.RemoteCmds;

import java.sql.*;

public class DbQuery extends DbConnection{
    private static Connection con;
    public DbQuery(){
    }

    public static void validateLoginInDb(String email, String password){
        con = DbConnection.createDbCon();
        String sql = "SELECT * FROM customer WHERE C_Email = ? and C_Password = ?";
        PreparedStatement pState = null;
        ResultSet result;

        try {
            pState = con.prepareStatement(sql);
            pState.setString(1, email);
            pState.setString(2, password);

            result = pState.executeQuery();

            if (result.next()) {
                // Login successful
                System.out.println("Login successful!");
                String cusID = result.getString("C_ID");
                new HomeInterface(email, cusID);
            } else {
                // Invalid credentials
                JOptionPane.showMessageDialog(new Component() {// creating joption pane to show error login msg
                    @Override
                    public Container getParent() {
                        return super.getParent();
                    }
                }, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println("Invalid email or password!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            con.close();
            System.out.println("db connection closed..!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
