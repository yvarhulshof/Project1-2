package com.mygdx.test;

import java.io.IOException;
import java.io.PrintWriter;

public class Course {

    private double frictionConstant;
    private double gravitationalForce;
    private double maxSpeed;
    private String heightFunction;

    public Course(double frictionConstant, double gravitationalForce, double maxSpeed, String heightFunction) {
        this.frictionConstant = frictionConstant;
        this.gravitationalForce = gravitationalForce;
        this.maxSpeed = maxSpeed;
        this.heightFunction = heightFunction;
    }

    public void writeCourseToFile() {
        try {
            PrintWriter writer = new PrintWriter("MapInput.txt", "UTF-8");
            writer.println();
            writer.println(frictionConstant);
            writer.println(gravitationalForce);
            writer.println(maxSpeed);
            writer.println(heightFunction);
            writer.close();
        }
        catch(IOException exception){
            System.out.println("file not found");
        }
    }
}