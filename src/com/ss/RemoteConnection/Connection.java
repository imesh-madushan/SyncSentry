package com.ss.RemoteConnection;
import com.jcraft.jsch.*;

class Connection {
    private static final String username = "root";
    private static final String keyPath = "src/com/ss/RemoteConnection/v1digi.pem";
    private static final String host = "188.166.237.86";

    protected static Session connect() {
        try {
            JSch jsch = new JSch();
            jsch.addIdentity(keyPath);

            // Connect to the VPS
            Session session = jsch.getSession(username, host, 22);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setConfig("PreferredAuthentications", "publickey");
            session.connect();

            System.out.println("Connected to the remote server");
            return session;
        }
        catch (JSchException e) {
            throw new RuntimeException(e);
        }
    }
}
