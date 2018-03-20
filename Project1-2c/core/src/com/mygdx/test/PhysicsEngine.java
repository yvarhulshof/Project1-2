package com.mygdx.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public class PhysicsEngine {


    //Rectangle golfBall;
    Circle golfBall;
    boolean ballStopped = false;

    static boolean initialCall = true;
    static double startTime = 0;
    static double previousGolfballX = 0;
    static double previousGolfballY = 0;


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

        //System.out.println("xLoc: " + xChange);
        //System.out.println("yLoc: " + yChange);

        //if the change in direction becomes negative we no longer update the ball's location so that it comes to a stop
        if(initialSpeed - (frictionConstant * elapsedTime) > 0) {
            previousGolfballX = golfBall.x;
            previousGolfballY = golfBall.y;
            golfBall.x += xChange;
            golfBall.y += yChange;
            //System.out.println("check");
        }
        else {
            ballStopped = true;
            initialCall = true;
        }

        //System.out.println("ballLoc: " + golfBall.x);
        //System.out.println("ballLoc: " + golfBall.y);
    }

    public boolean getBallStopped(){
        return ballStopped;
    }
}
