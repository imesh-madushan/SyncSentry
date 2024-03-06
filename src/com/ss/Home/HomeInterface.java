package com.ss.Home;

import com.ss.Database.DbQuery;
import com.ss.Login.*;
import com.ss.Home.*;
import javax.swing.*;
import java.awt.*;

public class HomeInterface extends JFrame {
    private String email;
    private static String cusID = "C001";
    public HomeInterface(String email, String cusID){
        this.email = email;
        this.cusID = cusID;
        initComponents();
    }

    public static String getCusID(){
        return cusID;
    }
    private void initComponents(){
        Container container = getContentPane();

        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) { // Creating gradiant colour background
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();

                int width = getWidth();
                int height = getHeight();

                Color startColor = new Color(189, 160, 229);
                Color endColor = new Color(72, 136, 203);

                GradientPaint gradient = new GradientPaint(0, 0, startColor, width, height, endColor);

                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, width, height);

                g2d.dispose();
            }
        };
        gradientPanel.setLayout(new BorderLayout());

        JPanel sideLeft = new JPanel(new BorderLayout());
        sideLeft.setBackground(new Color(255, 255, 255, 0));

        JPanel sideRight = new JPanel(new BorderLayout());
        sideRight.setBackground(new Color(255, 255, 255, 0));

        // Creating elements for the left side--------------------------------------------------------------------------
        ImageIcon image = new ImageIcon("src/Images/logo.png");
        image.setImage(image.getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH));
        JLabel imageLabel = new JLabel(image);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 15, 20));

        JPanel usrnamePanel = new JPanel(new BorderLayout());
        usrnamePanel.setBackground(new Color(255, 255, 255, 0));

        JLabel userName = new JLabel(email);
        userName.setFont(new Font("Arial", Font.PLAIN, 18));
        userName.setHorizontalAlignment(SwingConstants.CENTER);
        userName.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        JLabel planLabel = new JLabel("Free Plan");
        planLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        planLabel.setHorizontalAlignment(SwingConstants.CENTER);
        planLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        JButton LogoutButton = new JButton("Logout");
        LogoutButton.setFont(new Font("Arial", Font.PLAIN, 18));
        LogoutButton.setBackground(new Color(255, 255, 255));
        LogoutButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));



        // Creating elements for the right side--------------------------------------------------------------------------
        JLabel appName = new JLabel("Sync Sentry");
        appName.setFont(new Font("Arial", Font.PLAIN, 37));
        appName.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        appName.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(0, 0, 0, 200));

        JLabel labelHead = new JLabel("Your Files");
        labelHead.setForeground(Color.WHITE);
        labelHead.setFont(new Font("Arial", Font.PLAIN, 20));
        labelHead.setHorizontalAlignment(SwingConstants.CENTER);
        labelHead.setBorder(BorderFactory.createEmptyBorder(18, 0, 18, 0));

        JPanel wrapperPanel = new JPanel(new GridLayout(0, 1));
        wrapperPanel.setBackground(new Color(255, 255, 255, 0));
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBackground(new Color(255, 255, 255));

        JPanel uploadPanel = new JPanel(new FlowLayout());
        uploadPanel.setBackground(new Color(255, 255, 255, 0));
        uploadPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel uploadLabel = new JLabel("Upload a file");
        uploadLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        uploadLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        uploadLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton uploadButton = new JButton("Select");
        uploadButton.setFont(new Font("Arial", Font.PLAIN, 18));
        uploadButton.setBackground(new Color(255, 255, 255));
        uploadButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));


        // Adding the elements to the panels
        sideLeft.add(imageLabel, BorderLayout.NORTH);
        usrnamePanel.add(userName, BorderLayout.NORTH);
        usrnamePanel.add(planLabel);
        sideLeft.add(usrnamePanel, BorderLayout.CENTER);
        sideLeft.add(LogoutButton, BorderLayout.SOUTH);

        sideRight.add(appName, BorderLayout.NORTH);
        contentPanel.add(labelHead, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        uploadPanel.add(uploadLabel);
        uploadPanel.add(uploadButton);
        sideRight.add(contentPanel, BorderLayout.CENTER);
        sideRight.add(uploadPanel, BorderLayout.SOUTH);

        gradientPanel.add(sideLeft, BorderLayout.WEST);
        gradientPanel.add(sideRight, BorderLayout.CENTER);
        container.add(gradientPanel);

        //Adding enevt listeners to the buttons
        LogoutButton.addActionListener(e -> {
            dispose();
            new LoginInterface();
        });

        uploadButton.addActionListener(e -> {
            FileHandler.upload();
        });


        setTitle("Home");
        setSize(1080, 720);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);


        //Creating the dynamic file panels
        for (int i = 0; i < 10; i++) {
            JPanel filePanel = new JPanel(new GridLayout(0, 4));
            filePanel.setBackground(new Color(255, 255, 255, 0));
            filePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel fileName = new JLabel("File " + i);
            fileName.setFont(new Font("Arial", Font.PLAIN, 18));
            fileName.setHorizontalAlignment(SwingConstants.CENTER);
            filePanel.add(fileName);

            JButton downloadButton = new JButton("Rename");
            downloadButton.setForeground(new Color(8, 173, 35));
            downloadButton.setFont(new Font("Arial", Font.PLAIN, 18));
            downloadButton.setBackground(new Color(255, 255, 255));
            downloadButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            filePanel.add(downloadButton);

            JButton renameButton = new JButton("Download");
            renameButton.setForeground(Color.blue);
            renameButton.setFont(new Font("Arial", Font.PLAIN, 18));
            renameButton.setBackground(new Color(255, 255, 255));
            renameButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            filePanel.add(renameButton);

            JButton deleteButton = new JButton("Delete");
            deleteButton.setForeground(Color.RED);
            deleteButton.setFont(new Font("Arial", Font.PLAIN, 18));
            deleteButton.setBackground(new Color(255, 255, 255));
            deleteButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            filePanel.add(deleteButton);

            wrapperPanel.add(filePanel);
        }
        scrollPane.setViewportView(wrapperPanel); // Set the wrapperPanel to the scrollPane as the view

    }
}
