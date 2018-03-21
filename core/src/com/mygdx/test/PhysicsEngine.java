package com.mygdx.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;

public class PhysicsEngine {

    Circle golfBall;
    boolean ballStopped = true;

    static boolean initialCall = true;
    static double startTime = 0;
    static double previousGolfballX = 0;
    static double previousGolfballY = 0;

    private double frictionConstant = 0.6;
    private double gravitationalForce = 9.81;
    private double maxSpeed;


    public PhysicsEngine(Circle golfBall){
        this.golfBall = golfBall;
    }

    public void moveBall(double direction, double initialSpeed){

        ballStopped = false;

        if(initialCall) startTime = System.nanoTime() / 1000000000.0; //defining the value for which t = 0
        initialCall = false;

        double directionCoefficientX = (Math.cos(Math.toRadians(direction)));
        double directionCoefficientY = (Math.sin(Math.toRadians(direction)));
        final double frictionConstant = 0.6;
        double elapsedTime = System.nanoTime() / 1000000000.0 - startTime; //defining the value of t for the current call of render()

        double gravitationalPull = 9.81 / Math.pow(elapsedTime, 2); //not used yet, only comes in to play when the ball is on a slope

        //change in x and y during the elapsed time
        float xChange = (float) (directionCoefficientX * (initialSpeed - (frictionConstant  * elapsedTime)));
        float yChange = (float) (directionCoefficientY * (initialSpeed - (frictionConstant  * elapsedTime)));

        System.out.println("xLoc: " + xChange);
        System.out.println("yLoc: " + yChange);

        //if the change in direction becomes negative we no longer update the ball's location so that it comes to a stop
        if(initialSpeed - (frictionConstant * elapsedTime) > 0) {
            previousGolfballX = golfBall.x;
            previousGolfballY = golfBall.y;
            golfBall.x += xChange;
            golfBall.y += yChange;
        }
        else {
            ballStopped = true;
            initialCall = true;
        }

        System.out.println("ballLoc: " + golfBall.x);
        System.out.println("ballLoc: " + golfBall.y);
    }

    public boolean getBallStopped(){
        return ballStopped;
    }

    public static double[] differentiation(double[] arr) {
        double[] done = new double[arr.length - 1];
        for (int i = 0; i < done.length; i++) {
            done[i] = arr[i]*(arr.length-i-1);
        }
        return done;
    }

    public static double calcAngle(double x, double y){
        double angle;
        angle = (Math.acos(-x/(Math.sqrt(x*x + y*y))));
        angle = angle*180/Math.PI;
        if (y>=0)
            return angle;
        else
            return 360-angle;
    }

    public void setGravitationalForce(double gravitationalForce){
        this.gravitationalForce = gravitationalForce;
    }

    public void setFrictionConstant(double frictionConstant){
        this.frictionConstant = frictionConstant;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

}
