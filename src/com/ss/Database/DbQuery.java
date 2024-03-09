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
                String fname = result.getString("F_Name");
                String fsize = result.getString("F_Size");
                String fid = result.getString("F_ID");
                String ftype = result.getString("F_Type");
                HomeInterface.addDataToWrapper(fname, fsize, fid, ftype);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertFileInDb(String cusID, String fileName, int fileSize, String fileType){
        con = createDbCon();
        String sql = "INSERT INTO file (C_ID, F_Name, F_Size, F_ID, F_Type) VALUES (?, ?, ?, ?, ?)";
        try {
            String fileID = genarateFileID();

            if (fileID == null){// regenerating file ID if got a null while genarating
                fileID = genarateFileID();
            }

            preStatement = con.prepareStatement(sql);
            preStatement.setString(1, cusID);
            preStatement.setString(2, fileName);
            preStatement.setInt(3, fileSize);
            preStatement.setString(4, fileID);
            preStatement.setString(5, fileType);
            preStatement.executeUpdate();

//            new HomeInterface();
            System.out.println("File inserted successfully");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void renameFileInDb(String fileId, String newName){
        con = createDbCon();
        String sql = "UPDATE file SET F_Name = ? WHERE F_ID = ?";
        try {
            preStatement = con.prepareStatement(sql);
            preStatement.setString(1, newName);
            preStatement.setString(2, fileId);
            preStatement.executeUpdate();
            System.out.println("File renamed successfully");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFileInDb(String fileID){
        con = createDbCon();
        String sql = "DELETE FROM file WHERE F_ID = ?";
        try {
            preStatement = con.prepareStatement(sql);
            preStatement.setString(1, fileID);
            preStatement.executeUpdate();
            System.out.println("File deleted successfully");
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

    private String genarateCusID(){ // Generate a new customer ID
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

    private String genarateFileID(){ // Generate a new file ID
        StringBuilder fileID = new StringBuilder("File");
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < 11; i++) {
            int index = (int) (chars.length() * Math.random());
            fileID.append(chars.charAt(index));
        }

        String checkID = "SELECT * FROM file WHERE F_ID = ?";
        con = createDbCon();
        try {
            preStatement = con.prepareStatement(checkID);
            preStatement.setString(1, fileID.toString());
            result = preStatement.executeQuery();
            if (result.next()) {
                genarateCusID();
            }
            else{
                return fileID.toString();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
