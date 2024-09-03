# SyncSentry <img align="center" src="https://raw.githubusercontent.com/imesh927/SyncSentry/master/src/Images/logo.png" width="40" height="40">

SyncSentry is a **cloud-backup** desktop application developed using **Java**. It allows users to securely upload, download, rename, and delete files. The application supports a free plan for uploading files up to 10MB, with a pro version available for larger file uploads.

#### Demo Version of Program

- The compiled `.jar` file is available [here](demo/SyncSentry.jar). Download it and run the application using the following command:
  ```sh
  java -jar SyncSentry.jar
  ```

#### Features

- **Secure File Management**: Upload, download, rename, and delete files with ease.
- **User Authentication**: Secure login required to access the application.
- **File Size Limitations**: Free plan limited to files smaller than 10MB. Upgrade to pro for larger files.
- **Virtual Machine Requirement**: Requires a virtual machine connected via SSH for operation.

#### Java Libraries Used

- `java.swing` - Used for developing the front-end of the application.
- `java.jdbc` - Used to connect to the MySQL database.
- `java.jsch` - Used to execute remote shell commands on the cloud server.

#### Setup Instructions

1. **Clone the repository**:

   ```sh
   git clone https://github.com/imesh927/SyncSentry.git
   ```

2. **Configure SSH Connection**:

   - Update the **username**, **password**, and **IP address** of your VPS in:
     ```sh
     ./SyncSentry/src/com/ss/RemoteConnection/Connection.java
     ```

3. **Configure Database Connection**:

   - Update your **database urlt**, **username**, and **password** in:
     ```sh
     ./SyncSentry/src/com/ss/Database/DbConnection.java
     ```

4. **Create the Database**:

   - Use following mysql commands to setup the database:

     ```sql
     CREATE DATABASE syncsentry;

     CREATE TABLE Customer(
     C_ID VARCHAR(10) NOT NULL,
     C_Name VARCHAR(20) NOT NULL,
     C_Email VARCHAR(30) NOT NULL,
     C_Password VARCHAR(20) NOT NULL,
     Pro INT NOT NULL DEFAULT 0,
     PRIMARY KEY (C_ID)
     );

     CREATE TABLE File (
     F_ID VARCHAR(10) NOT NULL,
     F_Name VARCHAR(500) NOT NULL,
     F_Size INT NOT NULL,
     C_ID VARCHAR(10),
     PRIMARY KEY (F_ID),
     FOREIGN KEY (C_ID) REFERENCES Customer(C_ID)
     );

     ```

5. **Build and Run**:
   - Use your preferred IDE to compile and run the application.
   - The main java file as bellow
     ```sh
     .\SyncSentry\src\com\ss\Login\LoginInterface.java
     ```

#### Requirements

- A virtual machine with SSH connectivity.
- Java Development Kit (JDK) installed.
- MySQL database.

#### Purpose of this Project

This project was developed as part of a university module to fulfill academic requirements and enhance my knowledge and skills in software development.

### License

This project is licensed under the MIT License. See the `LICENSE` file for details.
