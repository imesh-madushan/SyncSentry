package com.ss.Database;
import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

class DbConnection{
    private Connection dbCon;
    private Statement cmdSql;
    private final String dbUrl = "jdbc:mysql://oneoclock.lifezeeds.me:3306/syncsentry";
    private final String dbUser = "imesh";
    private final String dbPasswd = "imesh";

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
