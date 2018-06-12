package com.mygdx.test;

import javax.swing.*;
import java.awt.*;

public class WinFrame {

    public void winGUI() {


        JLabel label = new JLabel("Congratulation!");
        label.setFont(new Font("Colibri", Font.BOLD, 30));
        label.setForeground(Color.RED);
        JLabel nameLabel = new JLabel(" next level in construction...");
        nameLabel.setFont(new Font("Colibri", Font.BOLD, 30));
        nameLabel.setForeground(Color.ORANGE);
        JLabel img = new JLabel(new ImageIcon("construction.png"));
        JPanel controlPanel = new JPanel();
        controlPanel.add(label);
        controlPanel.add(nameLabel);
        controlPanel.add(img);


        JFrame controlFrame = new JFrame();
        controlFrame.setBackground(Color.darkGray);
        controlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        controlFrame.add(controlPanel);
        controlFrame.setSize(500, 600);
        controlFrame.setAlwaysOnTop(true);
        controlFrame.setLocationRelativeTo(null);
        controlFrame.setVisible(true);


    }
    public static void  main(String[] Args){
        WinFrame j = new WinFrame();
        j.winGUI();
    }

}




