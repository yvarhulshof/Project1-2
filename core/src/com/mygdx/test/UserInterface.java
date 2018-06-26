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
import java.awt.*;

public class UserInterface {

    private static double dir;
    private static double spd;
    private static boolean buttonShootClicked;


    /////////////////////////

    private static boolean AIbuttonClicked;
    private static String vis;
    boolean visible = true;
    public boolean course1 = true;
    public boolean course2 = false;
    public boolean course3 = false;


    public void createGUI() {

        JTextArea nameArea = new JTextArea(10, 30);
        //JLabel label = new JLabel("wololoooo");
        //label.setSize(400,100);
        JLabel nameLabel = new JLabel("Initial Direction (degrees/360): ");
        JTextField nameField = new JTextField(10);
        JLabel passLabel = new JLabel("Initial Speed(m/s): ");
        JTextField passField = new JTextField(10);

        JButton shootButton = new JButton("Swing!");
        JPanel controlPanel = new JPanel(new GridLayout(5,0));
        controlPanel.add(nameLabel);
        controlPanel.add(nameField);
        controlPanel.add(passLabel);
        controlPanel.add(passField);
        controlPanel.add(shootButton);
        //controlPanel.add(label);
//        JFrame controlFrame = new JFrame();
//        controlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        controlFrame.add(controlPanel);
//        controlFrame.setSize(200, 200);
//        controlFrame.setVisible(true);

        class ShootInputListener implements ActionListener{
            public void actionPerformed(ActionEvent event){

                dir = (Double.parseDouble(nameField.getText()));
                System.out.println(dir);
                spd = (Double.parseDouble(passField.getText()));
                System.out.println(spd);
                buttonShootClicked = true;
            }
        }

        ActionListener listener = new ShootInputListener();
        shootButton.addActionListener(listener);

        ///////////////////////////////////////


        JTextArea AInameArea = new JTextArea(10, 30);
        //JLabel label = new JLabel("wololoooo");
        //label.setSize(400,100);
        String[] combo1={"invisible","visible"};
        JComboBox visibleBox = new JComboBox(combo1);
        JButton button = new JButton("Swing!");
        JPanel AIcontrolPanel = new JPanel(new GridLayout(4,0));
        AIcontrolPanel.add(visibleBox);
        AIcontrolPanel.add(button);

        String[] combo2={"1","2","3"};
        JComboBox courseBox = new JComboBox(combo2);
        JButton courseButton = new JButton("select");
        AIcontrolPanel.add(courseBox);
        AIcontrolPanel.add(courseButton);

        //controlPanel.add(label);
//        JFrame AIcontrolFrame = new JFrame();
//        AIcontrolFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        AIcontrolFrame.add(AIcontrolPanel);
//        AIcontrolFrame.setSize(200, 200);
//        AIcontrolFrame.setVisible(true);


        JPanel finalPanel = new JPanel(new GridLayout(2,0));
        finalPanel.add(controlPanel);
        finalPanel.add(AIcontrolPanel);

        JFrame finalFrame = new JFrame();
        finalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        finalFrame.add(finalPanel);
        finalFrame.setSize(300, 300);
        finalFrame.setVisible(true);

        class AIInputListener implements ActionListener{
            public void actionPerformed(ActionEvent event) {
                vis = visibleBox.getSelectedItem().toString();
                if (event.getSource() == button) {
                    if (vis == ("invisible")) {
                        visible = false;
                    } else {
                        visible = true;
                    }
                    AIbuttonClicked = true;
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

        ActionListener AIlistener = new AIInputListener();
        button.addActionListener(AIlistener);

        ActionListener AIlistener2 = new AIInputListener();
        courseButton.addActionListener(AIlistener2);

    }

    public double getDir(){
        return dir;
    }

    public double getSpd(){
        return spd;
    }

    public boolean getShootButtonClicked(){
        return buttonShootClicked;
    }

    public void setShootButtonClicked(boolean clicked){
        buttonShootClicked = clicked;
    }

    /////////////////////////

    public boolean getVisible(){
        return visible;
    }

    public void setVisible(){
        if(!visible){
            visible = true;
        }
    }

    public boolean getAIButtonClicked(){
        return AIbuttonClicked;
    }

    public void setAIButtonClicked(boolean clicked){
        AIbuttonClicked = clicked;
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
