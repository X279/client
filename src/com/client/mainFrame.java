package com.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainFrame extends Thread{
    private JFrame myClient = new JFrame("邮件管理");
    private JButton sendButton = new JButton("发送邮件");
    private JButton varifyButton = new JButton("验证邮箱");
    Container contentPane = myClient.getContentPane();

    private void setupFrame(){
        myClient.setSize(200,200);
        myClient.setLocationRelativeTo(null);
        myClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myClient.setResizable(false);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMailSevice c = new sendMailSevice();
                c.start();
            }
        });
        varifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                varifyMailAddressService v = new varifyMailAddressService();
                v.start();
            }
        });
        contentPane.setLayout(new FlowLayout());
        contentPane.add(sendButton);
        contentPane.add(varifyButton);
        myClient.setVisible(true);
    }

    public void run(){
        setupFrame();
    }
}
