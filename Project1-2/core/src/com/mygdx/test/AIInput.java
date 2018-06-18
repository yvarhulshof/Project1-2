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
    public boolean course1 = true;
    public boolean course2 = false;
    public boolean course3 = false;


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

        String[] combo2={"1","2","3"};
        JComboBox courseBox = new JComboBox(combo2);
        JButton courseButton = new JButton("select");
        controlPanel.add(courseBox);
        controlPanel.add(courseButton);

        //controlPanel.add(label);
        JFrame controlFrame = new JFrame();
        controlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        controlFrame.add(controlPanel);
        controlFrame.setSize(200, 200);
        controlFrame.setVisible(true);

        class InputListener implements ActionListener{
            public void actionPerformed(ActionEvent event) {
                vis = visibleBox.getSelectedItem().toString();
                if (event.getSource() == button) {
                    if (vis == ("invisible")) {
                        visible = false;
                    } else {
                        visible = true;
                    }
                    buttonClicked = true;
                }
                else if(event.getSource()== courseButton){
                    String course=courseBox.getSelectedItem().toString();
                    if(course == "1"){
                        System.out.println("1");
                        course1=true;
                        course2=false;
                        course3=false;

                    }
                    else if(course == "2"){
                        System.out.println("2");
                        course1=false;
                        course2=true;
                        course3=false;


                    }
                    else if(course == "3"){
                        System.out.println("3");
                        course1=false;
                        course2=false;
                        course3=true;

                    }

                }

            }
        }

        ActionListener listener = new InputListener();
        button.addActionListener(listener);

        ActionListener listener2 = new InputListener();
        courseButton.addActionListener(listener2);
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

    public boolean getCourse1(){
        return course1;
    }
    public boolean getCourse2(){
        return course2;
    }
    public boolean getCourse3(){
        return course3;
    }

}
