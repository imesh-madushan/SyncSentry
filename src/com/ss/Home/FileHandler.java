package com.ss.Home;

import com.ss.RemoteConnection.RemoteCmds;
import java.io.*;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;

public class FileHandler {
    public static void upload(String cusID, int isPro) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(null); // Show the file chooser
        File file = fileChooser.getSelectedFile(); // Get the file

        if (file == null) {
            System.out.println("No file selected");
            return;
        }
        else {
            System.out.println("File selected");
            System.out.println("Selected File size "+file.length()/1024 + " KB");

            System.out.println("Selected File path "+file.getAbsolutePath());
            System.out.println("Selected File type "+fileChooser.getTypeDescription(file));

            int fileSize = (int) file.length();
            int fileSizeMB = fileSize/(1024*1024);
            int freeUploadLimit = 10;

            String fileNameWithExtension = file.getName(); // get the file name with extension  ex: abc.mp4
            String fileType = "";

            int dotIndex = fileNameWithExtension.lastIndexOf('.'); // get the index of the dot in the file name

            if (dotIndex >= 0) { //if there is a dot in the file name
                fileType = fileNameWithExtension.substring(dotIndex); // get the file extension by separating with that dot
            }

            System.out.println("File Extension: " + fileType);
            String fileNameWithoutExtension = fileNameWithExtension.substring(0, fileNameWithExtension.lastIndexOf('.'));// get the file name without extension ex: abc

            String filePath = file.getAbsolutePath();


            if(isPro == 1){
                RemoteCmds.uploadFile(cusID, fileNameWithoutExtension, fileSizeMB, filePath, fileType); //call the upload function by sending selected file path
            }
            else{
                if (fileSizeMB > freeUploadLimit){
                    JOptionPane.showMessageDialog(null, "Free plan is limited to 10MB perfile\n Upgrade Pro to remove upload Limits", "limited", JOptionPane.INFORMATION_MESSAGE);
                    fileChooser.setVisible(false);  //close the file chooser
                    new UpgradePopUp(cusID); //open the upgrade pop up
                }
                else {
                    RemoteCmds.uploadFile(cusID, fileNameWithoutExtension, fileSizeMB, filePath, fileType); //call the upload function by sending selected file path
                }
            }
        }
    }

    public static void downloadFile() {
        System.out.println("File has been downloaded");
    }
}
