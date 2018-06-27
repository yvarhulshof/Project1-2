package com.mygdx.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsWindow {
    private int players;
    private int terrain;
    private double maxdistance;
    private boolean playerSelected = false;
    private boolean terrainSelected = false;
    private boolean distanceSelected = false;
    public static boolean done = false;

    private static String vis;


    public void createGUI() {


        JLabel numberLabel = new JLabel("number of players");
        String[] comboplayers = {"1","2","3","4","5","6"};
        JComboBox playersBox = new JComboBox(comboplayers);
        JButton numPlayerDoneButton = new JButton("choose");


        JLabel terrainLabel = new JLabel("terrain");
        String[] comboTerrain = {"1","2","3",};
        JComboBox terrainBox = new JComboBox(comboTerrain);
        JButton DoneButton = new JButton("choose");

        JLabel distanceLabel = new JLabel("max distance between balls, recommended = 350 ");
        JTextField distanceField = new JTextField(10);



        JPanel finalPanel = new JPanel(new GridLayout(7,0));
        finalPanel.add(numberLabel);
        finalPanel.add(playersBox);
        finalPanel.add(terrainLabel);
        finalPanel.add(terrainBox);
        finalPanel.add(distanceLabel);
        finalPanel.add(distanceField);
        finalPanel.add(DoneButton);

        JFrame finalFrame = new JFrame();
        finalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        finalFrame.add(finalPanel);
        finalFrame.setSize(300, 300);
        finalFrame.setVisible(true);


        class SettingsInputListener implements ActionListener{
            public void actionPerformed(ActionEvent event) {

                if (event.getSource() == DoneButton) {
                    vis = playersBox.getSelectedItem().toString();
                    if (vis == ("1"))
                        players = 1;
                    else if (vis == ("2"))
                        players = 2;
                    else if (vis == ("3"))
                        players = 3;
                    else if (vis == ("4"))
                        players = 4;
                    else if (vis == ("5"))
                        players = 5;
                    else if (vis == ("6"))
                        players = 6;

                    playerSelected = true;
                    System.out.println("player selected");



                    String course = terrainBox.getSelectedItem().toString();
                    if(course == "1")
                        terrain = 1;

                    else if(course == "2")
                        terrain = 2;

                    else if(course == "3")
                       terrain = 3;

                    terrainSelected = true;
                    System.out.println("terrain selected");

                    maxdistance = (Double.parseDouble(distanceField.getText()));
                    distanceSelected = true;


                    done = true;
                    System.out.println("done is " + done);

                }

            }
        }


        ActionListener SIL = new SettingsInputListener();
        numPlayerDoneButton.addActionListener(SIL);

        ActionListener SIL2 = new SettingsInputListener();
        DoneButton.addActionListener(SIL2);



//        JTextField nameField = new JTextField(10);
//        JLabel passLabel = new JLabel("Initial Speed(m/s): ");
//        JTextField passField = new JTextField(10);
//
//        JButton shootButton = new JButton("Swing!");
//        JPanel controlPanel = new JPanel(new GridLayout(5,0));
//        controlPanel.add(numberLabel);
//        controlPanel.add(nameField);
//        controlPanel.add(passLabel);
//        controlPanel.add(passField);
//        controlPanel.add(shootButton);
//        controlPanel.add(label);
//        JFrame controlFrame = new JFrame();
//        controlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        controlFrame.add(controlPanel);
//        controlFrame.setSize(200, 200);
//        controlFrame.setVisible(true);

//        class ShootInputListener implements ActionListener {
//            public void actionPerformed(ActionEvent event){
//
//                dir = (Double.parseDouble(nameField.getText()));
//                System.out.println(dir);
//                spd = (Double.parseDouble(passField.getText()));
//                System.out.println(spd);
//                buttonShootClicked = true;
//            }
//        }
//
//        ActionListener listener = new ShootInputListener();
//        shootButton.addActionListener(listener);
//
//        ///////////////////////////////////////
//
//
//        JTextArea AInameArea = new JTextArea(10, 30);
//        //JLabel label = new JLabel("wololoooo");
//        //label.setSize(400,100);
//        String[] combo1={"invisible","visible"};
//        JComboBox visibleBox = new JComboBox(combo1);
//        JButton button = new JButton("Swing!");
//        JPanel AIcontrolPanel = new JPanel(new GridLayout(4,0));
//        AIcontrolPanel.add(visibleBox);
//        AIcontrolPanel.add(button);
//
//        String[] combo2={"1","2","3"};
//        JComboBox courseBox = new JComboBox(combo2);
//        JButton courseButton = new JButton("select");
//        AIcontrolPanel.add(courseBox);
//        AIcontrolPanel.add(courseButton);
//
//        //controlPanel.add(label);
////        JFrame AIcontrolFrame = new JFrame();
////        AIcontrolFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////        AIcontrolFrame.add(AIcontrolPanel);
////        AIcontrolFrame.setSize(200, 200);
////        AIcontrolFrame.setVisible(true);
//
//
//        JPanel finalPanel = new JPanel(new GridLayout(2,0));
//        finalPanel.add(controlPanel);
//        finalPanel.add(AIcontrolPanel);
//
//        JFrame finalFrame = new JFrame();
//        finalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        finalFrame.add(finalPanel);
//        finalFrame.setSize(300, 300);
//        finalFrame.setVisible(true);
//
//        class AIInputListener implements ActionListener{
//            public void actionPerformed(ActionEvent event) {
//                vis = visibleBox.getSelectedItem().toString();
//                if (event.getSource() == button) {
//                    if (vis == ("invisible")) {
//                        visible = false;
//                    } else {
//                        visible = true;
//                    }
//                    AIbuttonClicked = true;
//                }
//                else if(event.getSource()== courseButton){
//                    String course=courseBox.getSelectedItem().toString();
//                    if(course == "1"){
//                        System.out.println("1");
//                        course1=true;
//                        course2=false;
//                        course3=false;
//
//                    }
//                    else if(course == "2"){
//                        System.out.println("2");
//                        course1=false;
//                        course2=true;
//                        course3=false;
//
//
//                    }
//                    else if(course == "3"){
//                        System.out.println("3");
//                        course1=false;
//                        course2=false;
//                        course3=true;
//
//                    }
//
//                }
//
//            }
//        }

//        ActionListener AIlistener = new AIInputListener();
//        button.addActionListener(AIlistener);
//
//        ActionListener AIlistener2 = new AIInputListener();
//        courseButton.addActionListener(AIlistener2);

    }



    public boolean done(){
//        if (playerSelected && terrainSelected && distanceSelected)
//            return true;
//        else
//            return false;
        return done;
    }
}
