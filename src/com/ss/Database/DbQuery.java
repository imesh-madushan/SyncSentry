package com.ss.Database;

import javax.swing.*;
import java.awt.*;
import com.mysql.jdbc.*;
import com.ss.Home.HomeInterface;
import com.ss.RemoteConnection.RemoteCmds;

import java.sql.*;

public class DbQuery extends DbConnection{
    private Connection con;
    private PreparedStatement preStatement = null;
    private ResultSet result;

    public DbQuery(){
    }

    public void readFilesDataInDb(String cusID){
        con = createDbCon();
        String sql = "SELECT * FROM file WHERE C_ID = ?";
        try {
            preStatement = con.prepareStatement(sql);
            preStatement.setString(1, cusID);
            result = preStatement.executeQuery();
            while (result.next()){
                HomeInterface.addDataToWrapper(result.getString("F_Name"), result.getString("F_Size"), result.getString("F_ID") );
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String validateLoginInDb(String email, String password){
        con = createDbCon();

        String sql = "SELECT * FROM customer WHERE C_Email = ? and C_Password = ?";

        try {
            preStatement = con.prepareStatement(sql);
            preStatement.setString(1, email); // Set parameters to sql query
            preStatement.setString(2, password);

            result = preStatement.executeQuery();

            if (result.next()) {
                // Login successful
                System.out.println("Login successful!");
                String cusID = result.getString("C_ID");
                int Pro = result.getInt("Pro");
                new HomeInterface(email, cusID, Pro);
                return "success";
            }
            else {
                return "failed";
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
        return null;
    }

    public String registerUserInDb(String name, String email, String password) {
        con = createDbCon();
        String checkEmail = "SELECT * FROM customer WHERE C_Email = ?"; // Check if the email already exists
        try {
            preStatement = con.prepareStatement(checkEmail);
            preStatement.setString(1, email);
            result = preStatement.executeQuery();
            if (result.next()) {
                JOptionPane.showMessageDialog(null, "Email already exists");
                return "failed";
            }
            else {
                String register = "INSERT INTO customer (C_ID, C_Name, C_Email, C_Password, Pro) VALUES (?, ?, ?, ?, ?)"; // If email is a fresh one, Register the user
                try {
                    String cusID = genarateCusID(); //Get a new customer ID

                    if (cusID == null){ // If the customer ID is null, regenerate a new one
                        cusID = genarateCusID();
                    }

                    preStatement = con.prepareStatement(register);
                    preStatement.setString(1, cusID);
                    preStatement.setString(2, name);
                    preStatement.setString(3, email);
                    preStatement.setString(4, password);
                    preStatement.setInt(5, 0);
                    preStatement.executeUpdate();
                    System.out.println("User registered successfully");
                    RemoteCmds.createFolderInVps(cusID);// Creating folder in VPS with cusID for the first time
                    new HomeInterface(email, cusID, 0);
                    return "success";
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String updateToPro(String cusID, int pro){
        con = createDbCon();
        String updatePro = "UPDATE customer SET Pro = ? WHERE C_ID = ?";
        try {
            preStatement = con.prepareStatement(updatePro);
            preStatement.setInt(1, pro);
            preStatement.setString(2, cusID);
            preStatement.executeUpdate();
            System.out.println("Pro updated successfully");
            return "success";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    private String genarateCusID(){
        StringBuilder cusID = new StringBuilder("Cus");
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < 7; i++) {
            int index = (int) (chars.length() * Math.random());
            cusID.append(chars.charAt(index));
        }

        String checkID = "SELECT * FROM customer WHERE C_ID = ?";
        con = createDbCon();
        try {
            preStatement = con.prepareStatement(checkID);
            preStatement.setString(1, cusID.toString());
            result = preStatement.executeQuery();
            if (result.next()) {
                genarateCusID();
            }
            else{
                return cusID.toString();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
