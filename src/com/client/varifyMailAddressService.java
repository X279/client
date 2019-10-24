package com.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class varifyMailAddressService extends Thread{
    private JFrame myVarify = new JFrame("验证邮箱");
    private JLabel addressLlable = new JLabel("邮箱地址:");
    private JTextField address = new JTextField(35);
    private JButton varify = new JButton("验证");
    private GridBagLayout gridBagLayout = new GridBagLayout();
    GridBagConstraints constraints = null;
    private http request;

    private void setupFrame(){
        myVarify.setSize(500, 300);
        myVarify.setResizable(false);
        myVarify.setLocationRelativeTo(null);
        myVarify.setLayout(gridBagLayout);
        place();
        myVarify.setVisible(true);
    }

    private void place(){
        constraints = new GridBagConstraints();

        constraints.gridwidth = 2;
        constraints.weightx = 1;
        constraints.weighty = 1;
        gridBagLayout.addLayoutComponent(addressLlable, constraints);
        myVarify.add(addressLlable);

        constraints.gridwidth = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        gridBagLayout.addLayoutComponent(address, constraints);
        myVarify.add(address);
        varify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("验证邮箱是否为有效邮箱...");
                String add = address.getText();
                request = new http(add, null, null, false);
                try {
                   String res = request.setRequest();
                    if(res.equals("Y")){
                        JFrame respFrame = new JFrame("有效");
                        respFrame.setSize(200,100);
                        respFrame.add(new JLabel("该地址为有效邮箱地址!"));
                        respFrame.setResizable(false);
                        respFrame.setLocationRelativeTo(null);
                        respFrame.setVisible(true);
                    }
                    else if(res.equals("N")){
                        JFrame respFrame = new JFrame("无效");
                        respFrame.setSize(200,100);
                        respFrame.add(new JLabel("该邮箱地址无效!"));
                        respFrame.setResizable(false);
                        respFrame.setLocationRelativeTo(null);
                        respFrame.setVisible(true);
                    }
                    address.setText("");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        myVarify.add(varify);
    }

    public void run(){
        setupFrame();
    }
}
