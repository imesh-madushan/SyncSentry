package com.ss.RemoteConnection;
import com.jcraft.jsch.*;

class Connection {
    private static final String username = "root";
    private static final String host = "";

    private static final String password = "";

    protected static Session connect() {
        try {
            JSch jsch = new JSch();

            // Connect to the VPS
            Session session = jsch.getSession(username, host, 22);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setConfig("PreferredAuthentications", "password");
            session.connect();

            System.out.println("Connected to the remote server");

            return session;
        }
        catch (JSchException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        connect();
    }
}
