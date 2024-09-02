# SysncSentry <img align="center" src="https://raw.githubusercontent.com/imesh927/SysncSentry/master/src/Images/logo.png" width="40" height="40">

SysncSentry is a **cloud-backup** desktop application developed using **Java**. It allows users to securely upload, download, rename, and delete files. The application supports a free plan for uploading files up to 10MB, with a pro version available for larger file uploads.

#### Features

- **Secure File Management**: Upload, download, rename, and delete files with ease.
- **User Authentication**: Secure login required to access the application.
- **File Size Limitations**: Free plan limited to files smaller than 10MB. Upgrade to pro for larger files.
- **Virtual Machine Requirement**: Requires a virtual machine connected via SSH for operation.

###3 Java Libraries Used

- `java.swing` - Used for developing the front-end of the application.
- `java.jdbc` - Used to connect to the MySQL database.
- `java.jsch` - Used to execute remote shell commands on the cloud server.

#### Setup Instructions

1. **Clone the repository**:
    ```sh
    git clone https://github.com/imesh927/SysncSentry.git
    ```

2. **Configure SSH Connection**:
    - Update the **username**, **password**, and **IP address** of your VPS in:
      ```sh
      /SysncSentry/src/com/ss/RemoteConnection/Connection.java
      ```

3. **Configure Database Connection**:
    - Update your **database host**, **username**, and **password** in:
      ```sh
      /SysncSentry/src/com/ss/Database/DbConnection.java
      ```

4. **Build and Run**:
    - Use your preferred IDE to compile and run the application.

#### Requirements

- A virtual machine with SSH connectivity.
- Java Development Kit (JDK) installed.
- MySQL database.

#### Contributing

This project was created for a university module to meet academic requirements and develop my knowledge and skills. Contributions are welcome

#### License

This project is licensed under the MIT License. See the `LICENSE` file for details.
This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.
