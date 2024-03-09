package com.ss.RemoteConnection;
import com.jcraft.jsch.*;
import com.ss.Database.DbQuery;
import com.ss.Home.*;

import javax.swing.*;
import java.io.*;
import java.nio.file.Path;

import static java.nio.file.Files.exists;

public class RemoteCmds extends Connection{

    public static void createFolderInVps(String cusID){// Creating folder in VPS with cusID for the first time
        Session sessionMKDIR = connect();
        try {
            ChannelExec createFolder = (ChannelExec) sessionMKDIR.openChannel("exec");

            final String commandMKDIR = "mkdir -p syncsentry/"+cusID; //Sting command to create aa file in VPS
            createFolder.setCommand(commandMKDIR);
            createFolder.connect(); //Connect and execute the command
            createFolder.disconnect();

            System.out.println("Folder "+cusID+" created successfully in server");
        }
        catch (JSchException e) {
            throw new RuntimeException(e);
        }
        sessionMKDIR.disconnect();
    }

    public static void uploadFile(String cusID, String fileName, int fileSize, String localFilePath, String fileType) { // Upload file to the server
        Session sessionUpload = connect(); // Connect to the server, using the connect method from the super class
        try{
            ChannelSftp sftpUpload = (ChannelSftp) sessionUpload.openChannel("sftp"); // create a new sftp connection
            sftpUpload.connect();

            String remoteFilePath = "/root/syncsentry/"+HomeInterface.getCusID()+"/";
            sftpUpload.put(localFilePath, remoteFilePath); // upload the file to server

            sftpUpload.disconnect();
            new DbQuery().insertFileInDb(cusID, fileName, fileSize, fileType); // insert the file details to the database
            System.out.println("File uploaded successfully");
        }
        catch (JSchException | SftpException e) {
            throw new RuntimeException(e);
        }

        sessionUpload.disconnect(); // disconnect from VPS server
    }

    public static void downloadFile(String fileName, String fileType){ // Download file from the server
        Session sessionDownload = connect();
        try{
            ChannelSftp sftpDownload = (ChannelSftp) sessionDownload.openChannel("sftp");
            sftpDownload.connect();

            String remoteFilePath = "/root/syncsentry/"+HomeInterface.getCusID()+"/"+fileName+fileType;

            String localUser = System.getProperty("user.name"); // get the current user of the local system

            if (exists(Path.of("C:/Users/" + localUser + "/Desktop/SyncSentry Downloads/"))){ // check if the folder exists
                System.out.println("SyncSentry Downloads folder exists in C:/Users/" + localUser + "/Desktop");
            }
            else { // if the folder does not exist, this will create the folder
                System.out.println("Folder does not exist");
                File folder = new File("C:/Users/" + localUser + "/Desktop/SyncSentry Downloads/");
                folder.mkdir(); // create the folder
                System.out.println("Folder created successfully in C:/Users/" + localUser + "/Desktop");
            }

            String localFilePath = "C:/Users/" + localUser + "/Desktop/SyncSentry Downloads/";
            sftpDownload.get(remoteFilePath, localFilePath); // download the file to local system
            sftpDownload.disconnect();

            System.out.println("File has downloaded successfully");
            HomeInterface.showMsgsucess("File has downloaded successfully to C:/Users/" + localUser + "/Desktop/SyncSentry Downloads/");
        }
        catch (JSchException e) {
            throw new RuntimeException(e);
        } catch (SftpException e) {
            throw new RuntimeException(e);
        }
        sessionDownload.disconnect(); // disconnect from VPS server
    }

    public static void renameFile(String oldName, String newName, String fileId, String fileType){// Rename a file
        Session sessionRename = connect();
        String oldPath = "/root/syncsentry/"+HomeInterface.getCusID()+"/"+oldName+fileType;
        String newPath = "/root/syncsentry/"+HomeInterface.getCusID()+"/"+newName+fileType;
        try{
            ChannelSftp sftpRename = (ChannelSftp) sessionRename.openChannel("sftp");
            sftpRename.connect();

            sftpRename.rename(oldPath, newPath); //rename the file in server
            sessionRename.disconnect();
            System.out.println(oldName+" renamed to "+newName+" successfully in remote server");
            new DbQuery().renameFileInDb(fileId, newName);

        }
        catch (JSchException e) {
            throw new RuntimeException(e);
        } catch (SftpException e) {
            throw new RuntimeException(e);
        }
        sessionRename.disconnect();
    }

    public static void deleteFile(String fileName, String fileID, String fileType){
        Session sessionDelete = connect();
        try{
            ChannelExec deleteFile = (ChannelExec) sessionDelete.openChannel("exec");
            final String deleteCommand = "rm /root/syncsentry/"+HomeInterface.getCusID()+"/"+fileName+fileType;

            deleteFile.setCommand(deleteCommand);
            deleteFile.connect(); //Connect and execute the command
            deleteFile.disconnect();
            new DbQuery().deleteFileInDb(fileID);
            System.out.println(fileName+" deleted successfully");
        }
        catch (JSchException e) {
            throw new RuntimeException(e);
        }

        sessionDelete.disconnect();
    }
}
