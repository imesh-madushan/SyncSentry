package com.ss.Database;
import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

class DbConnection{
    private static Connection dbCon;
    private  static Statement cmdSql;
    private static final String dbUrl = "jdbc:mysql://oneoclock.lifezeeds.me:3306/syncsentry";
    private static final String dbUser = "imesh";
    private static  final String dbPasswd = "imesh";

    protected DbConnection(){
    }

    protected static Connection createDbCon(){
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
