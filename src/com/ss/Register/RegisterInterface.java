package com.ss.Register;

import com.ss.Login.*;
import javax.swing.*;
import java.awt.*;

public class RegisterInterface extends JFrame{
    public RegisterInterface() {
        initComponents();
    }

    private void initComponents() {
        Container mainContainer = getContentPane();
        mainContainer.setBackground(Color.WHITE);

        // Creating a gradient panel
        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
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


        mainContainer.add(gradientPanel, BorderLayout.CENTER);
        gradientPanel.setLayout(new GridLayout(0, 2));

        JPanel fieldsPanel = new JPanel(new BorderLayout()); // contains usernameAndPasswordPanel and buttonsPanel
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(100, 10, 75, 10));
        fieldsPanel.setBackground(new Color(255, 255, 255, 0));

        JPanel usernameAndPasswordPanel = new JPanel(new GridLayout(0, 2, 10, 30)); // contains usernameLabel, usernameField, passwordLabel, passwordField
        usernameAndPasswordPanel.setBackground(new Color(255, 255, 255, 0));
        usernameAndPasswordPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
        fieldsPanel.add(usernameAndPasswordPanel, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel( new GridLayout(0, 1, 10, 10)); // contains loginButton, orLabel, registerButton
        buttonsPanel.setBackground(new Color(255, 255, 255, 0));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 110, 0, 110));
        fieldsPanel.add(buttonsPanel, BorderLayout.SOUTH);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameAndPasswordPanel.add(nameLabel);

        JTextField nameField = new JTextField();
        usernameAndPasswordPanel.add(nameField);

        JLabel EmailLabel = new JLabel("Email:");
        EmailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameAndPasswordPanel.add(EmailLabel);

        JTextField usernameField = new JTextField();
        usernameAndPasswordPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameAndPasswordPanel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        usernameAndPasswordPanel.add(passwordField);

        JButton registerButton = new JButton("Register");
        buttonsPanel.add(registerButton);

        JLabel backtoLabel = new JLabel("Back to");
        backtoLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        backtoLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        backtoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        buttonsPanel.add(backtoLabel);

        JButton loginButton = new JButton("Login");
        loginButton.setSize(100,80);
        buttonsPanel.add(loginButton);


        JPanel imagePanel = new JPanel(new BorderLayout()); // contains appNameLabel and imageLabel
        imagePanel.setBackground(new Color(255, 255, 255, 0));

        JLabel appNameLabel = new JLabel("Sync Sentry");
        appNameLabel.setFont(new Font("Arial", Font.BOLD, 38));
        appNameLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        appNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imagePanel.add(appNameLabel, BorderLayout.NORTH);

        ImageIcon image = new ImageIcon("src/Images/logo.png");
        JLabel imageLabel = new JLabel(image);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 85, 0));
        imagePanel.add(imageLabel, BorderLayout.SOUTH);

        gradientPanel.add(fieldsPanel);
        gradientPanel.add(imagePanel);

        setTitle("Login");
        setSize(720, 480);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RegisterInterface::new);
    }
}
