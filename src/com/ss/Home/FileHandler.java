package com.ss.Home;

import com.ss.RemoteConnection.RemoteCmds;
import java.io.*;
import java.io.IOException;
import javax.swing.*;

public class FileHandler {
    public static void upload() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(null); // Show the file chooser
        File file = fileChooser.getSelectedFile(); // Get the file

        System.out.println("Selected File size "+file.length()/1024 + " KB");
        System.out.println("Selected File name "+file.getName());
        System.out.println("Selected File path "+file.getAbsolutePath());
        System.out.println("Selected File type "+fileChooser.getTypeDescription(file));
//        System.out.println("The folder size is "+file.getParentFile().length()/(1024*1024) + " MB");

        RemoteCmds.uploadFile(file.getAbsolutePath()); //call the upload function by sending selected file path
    }

    public static void downloadFile() {
        System.out.println("File has been downloaded");
    }

    public static void main(String[] args) {
        upload();
    }
}
