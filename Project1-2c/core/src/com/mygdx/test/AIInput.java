package com.mygdx.test;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AIInput {

    private static boolean buttonClicked;
    private static String vis;
    boolean visible = true;

    public void createGUI() {

        JTextArea nameArea = new JTextArea(10, 30);
        //JLabel label = new JLabel("wololoooo");
        //label.setSize(400,100);
        String[] combo1={"invisible","visible"};
        JComboBox visibleBox = new JComboBox(combo1);
        JButton button = new JButton("Swing!");
        JPanel controlPanel = new JPanel();
        controlPanel.add(visibleBox);
        controlPanel.add(button);
        //controlPanel.add(label);
        JFrame controlFrame = new JFrame();
        controlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        controlFrame.add(controlPanel);
        controlFrame.setSize(200, 200);
        controlFrame.setVisible(true);

        class InputListener implements ActionListener{
            public void actionPerformed(ActionEvent event){
                vis = visibleBox.getSelectedItem().toString();
                if(vis == ("invisible")){
                   visible = false;
                }
                else{
                    visible = true;
                }
                buttonClicked = true;
            }
        }

        ActionListener listener = new InputListener();
        button.addActionListener(listener);
    }

    public boolean getVisible(){
        return visible;
    }
    public void setVisible(){
        if(!visible){
            visible = true;
        }
    }

    public boolean getButtonClicked(){
        return buttonClicked;
    }

    public void setButtonClicked(boolean clicked){
        buttonClicked = clicked;
    }
}
