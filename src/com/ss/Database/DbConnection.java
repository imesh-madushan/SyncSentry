package com.ss.Database;
import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

class DbConnection{
    private Connection dbCon;
    private Statement cmdSql;
    private final String dbUrl = "jdbc:mysql://localhost:3306/syncsentry"; //change this with your actual database host url and port
    private final String dbUser = "root"; //change the username with your credintials
    private final String dbPasswd = ""; //change the password as well

    protected DbConnection(){
    }

    protected Connection createDbCon(){
        try {
            DriverManager.registerDriver(new Driver());
            dbCon = DriverManager.getConnection(dbUrl, dbUser, dbPasswd);
            cmdSql = dbCon.createStatement();
            System.out.println("Database connected...");
            return dbCon;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
