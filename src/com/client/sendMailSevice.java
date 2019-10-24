package com.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class sendMailSevice extends Thread{

    //布局组件
    private JFrame frame = new JFrame("发送邮件");
    private GridBagLayout gridBagLayout = new GridBagLayout();
    GridBagConstraints constraints = null;
    private JLabel recipientsLable = new JLabel("收件地址：");
    private JTextField address = new JTextField(45);
    private JLabel topicLable = new JLabel("邮件主题：");
    private JTextField topic = new JTextField(45);
    private JTextArea message = new JTextArea("邮件正文：",10,10);
    private JScrollPane scrollPane = new JScrollPane(message,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    private JButton send = new JButton("发送");
    private http request;

    //初始化界面
    private void setFrame(){
        frame.setSize(600,450);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(gridBagLayout);
        placeComponents();
        frame.setVisible(true);
    }

    public void run(){
        setFrame();
    }

    //布局组件
    private void placeComponents(){
        constraints = new GridBagConstraints();

        //收件人地址，多个收件人用（英文）分号;隔开
        constraints.gridwidth = 1;
        constraints.weightx = 1;
        constraints.weighty = 1;
        gridBagLayout.addLayoutComponent(recipientsLable, constraints);
        frame.add(recipientsLable);

        constraints.gridwidth = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        gridBagLayout.addLayoutComponent(address, constraints);
        frame.add(address);

        //邮件主题
        constraints.gridwidth = 1;
        constraints.weightx = 1;
        constraints.weighty = 1;
        gridBagLayout.addLayoutComponent(topicLable, constraints);
        frame.add(topicLable);

        constraints.gridwidth = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        gridBagLayout.addLayoutComponent(topic, constraints);
        frame.add(topic);

        //邮件正文
        constraints.gridwidth = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        message.setEditable(true);
        message.setLineWrap(true);
        message.setWrapStyleWord(true);
        gridBagLayout.addLayoutComponent(scrollPane, constraints);
        frame.add(scrollPane);

        //发送按钮
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("发送中");
                String add = address.getText();
                String topicText = topic.getText();
                String info = message.getText().substring(5);//得到邮件正文，去除“邮件正文”的提示
                request = new http(add,topicText,info,true);
                try {
                    String res = request.setRequest();
                    if(res.equals("Y")){
                        JFrame respFrame = new JFrame("成功");
                        respFrame.setSize(100,100);
                        respFrame.add(new JLabel("发送成功!"));
                        respFrame.setResizable(false);
                        respFrame.setLocationRelativeTo(null);
                        respFrame.setVisible(true);
                    }
                    else if(res.equals("N")){
                        JFrame respFrame = new JFrame("失败");
                        respFrame.setSize(100,100);
                        respFrame.add(new JLabel("发送失败!"));
                        respFrame.setResizable(false);
                        respFrame.setLocationRelativeTo(null);
                        respFrame.setVisible(true);
                    }
                    address.setText("");
                    topic.setText("");
                    message.setText("邮件正文：");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        frame.add(send);
    }
}
