package com.ss.Home;

import com.ss.Database.DbQuery;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UpgradePopUp extends JFrame {
    private String cusID;
    public UpgradePopUp(String cusID) {
        this.cusID = cusID;
        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(41, 164, 241, 100));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));


        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        headerPanel.setBackground(new Color(0,0,0, 200));
        JLabel upgradeLabel = new JLabel("Upgrade to Pro to Remove Upload Limits, 10$ per month");
        upgradeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        upgradeLabel.setForeground(Color.WHITE);
        upgradeLabel.setBorder(BorderFactory.createEmptyBorder(18, 0, 0, 0));
        upgradeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(upgradeLabel);
        mainPanel.add(headerPanel);

        JLabel paymentLabel = new JLabel("Enter Payment Details");
        paymentLabel.setBorder(BorderFactory.createEmptyBorder(35, 0, 0, 0));
        paymentLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        paymentLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        mainPanel.add(paymentLabel);

        JPanel paymentPanel = new JPanel(new GridLayout(0, 2 , -140, 10));
        paymentPanel.setBorder(BorderFactory.createEmptyBorder(15, 60, 30, 60));
        paymentPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        paymentPanel.setBackground(new Color(255, 255, 255, 0));

        JLabel cardNumberLabel = new JLabel("Card Number");
        cardNumberLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        cardNumberLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        paymentPanel.add(cardNumberLabel);
        JTextField cardNumberField = new JTextField();
        cardNumberField.setAlignmentX(JTextField.CENTER_ALIGNMENT);
        paymentPanel.add(cardNumberField);

        JLabel expiryDateLabel = new JLabel("Expiry Date");
        expiryDateLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        expiryDateLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        paymentPanel.add(expiryDateLabel);
        JTextField expiryDateField = new JTextField();
        expiryDateField.setAlignmentX(JTextField.CENTER_ALIGNMENT);
        paymentPanel.add(expiryDateField);

        JLabel cvvLabel = new JLabel("CVV");
        cvvLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        cvvLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        paymentPanel.add(cvvLabel);
        JTextField cvvField = new JTextField();
        cvvField.setAlignmentX(JTextField.CENTER_ALIGNMENT);
        paymentPanel.add(cvvField);

        mainPanel.add(paymentPanel);

        JButton upgradeButton = new JButton("Upgrade");
        upgradeButton.setFont(new Font("Arial", Font.PLAIN, 18));
        upgradeButton.setBackground(new Color(255, 255, 255));
        upgradeButton.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        upgradeButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        mainPanel.add(upgradeButton);

        upgradeButton.addActionListener(e -> {
            if (cardNumberField.getText().isEmpty() || expiryDateField.getText().isEmpty() || cvvField.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Please fill all the fields" , "Error", JOptionPane.ERROR_MESSAGE);
            }
            //if card number data is not all numbers
            else if (cardNumberField.getText().matches(".*[a-zA-Z]+.*") || expiryDateField.getText().matches(".*[a-zA-Z]+.*") || cvvField.getText().matches(".*[a-zA-Z]+.*")){
                JOptionPane.showMessageDialog(null, "Card details must be numbers", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                String upgradingStatus = new DbQuery().updateToPro(cusID, 1);
                if( upgradingStatus == "success"){
                    JOptionPane.showMessageDialog(null, "Thank you for upgrading to Pro!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    HomeInterface.reFreshPlanPanel();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Error occurred while upgrading to Pro", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(mainPanel);
        setTitle("Upgrade to Pro");
        setSize(480, 360);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

//        addWindowListener(new WindowAdapter() { // function to reOpen HomeInterface if popup is closed
//            @Override
//            public void windowClosing(WindowEvent e) {
//                HomeInterface.reOpenHome();
//            }
//        });
    }
}
