package com.mygdx.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public class PhysicsEngine {

    GolfBall golfBall;
    Circle goal;
    Rectangle water;
    boolean ballStopped = true;

    static boolean initialCall = true;
    static double startTime = 0;
   // static double previousGolfballX = 0;
    //static double previousGolfballY = 0;

    private double frictionConstant = 0.6;
    private double g = 9.81;
    private double maxSpeed;
    private double mass = 1;

    private float vx1;
    private float vy1;
    private double slopey;
    private double slopex;

    public PhysicsEngine(GolfBall golfBall){
        this.golfBall = golfBall;
    }

    public void moveBall(double direction, double initialSpeed){

        ballStopped = false;

        if(initialCall) {
            startTime = System.nanoTime() / 1000000000.0; //defining the value for which t = 0
            initialCall = false;

            double directionCoefficientX = (Math.cos(Math.toRadians(direction)));
            double directionCoefficientY = (Math.sin(Math.toRadians(direction)));
            golfBall.setVX2((float) (directionCoefficientX*initialSpeed));
            System.out.println("initial vx2 " + golfBall.getVx2());
            golfBall.setVY2((float) (directionCoefficientY*initialSpeed));

        }

        initialCall = false;


        vx1 = golfBall.getVx2();
        System.out.println("initial vx1 " + vx1);
        vy1 = golfBall.getVy2();

        golfBall.setVX2(vx1 + (float) findfx());
        //vx2 = vx1 + findfx();
        golfBall.setVY2(vy1 + (float) findfy());

        final double frictionConstant = 0.6;
        double elapsedTime = System.nanoTime() / 1000000000.0 - startTime; //defining the value of t for the current call of render()

        double gravitationalPull = 9.81 / Math.pow(elapsedTime, 2); //not used yet, only comes in to play when the ball is on a slope

        //change in x and y during the elapsed time
        float xChange = (float) ((1.0/60.0)*vx1);
        float yChange = (float) ((1.0/60.0)*vy1);

        System.out.println("xChange: " + xChange);
        System.out.println("yChange: " + yChange);

        //if the change in direction becomes negative we no longer update the ball's location so that it comes to a stop
        //if(initialSpeed - (frictionConstant * elapsedTime) > 0) {
            golfBall.x += xChange;
            golfBall.y += yChange;
            //z = x+y;
        //}
        //else {
        //    ballStopped = true;
        //    initialCall = true;
        //}

        System.out.println("ballLoc: " + golfBall.x);
        System.out.println("ballLoc: " + golfBall.y);
    }

    public double findfx(){
        double G; //= 0; //for now, DO NOT APPLY
        double H;
        double fx;
        slopex = dx();
        G = -mass * g * slopex;

        H = -frictionConstant*mass*g*(vx1/(Math.sqrt((vx1*vx1)+(vy1*vy1))));
        fx = G + H;
        return fx;
    }
    public double findfy(){
        double G; //= 0; // = 0; for now, DO NOT APPLY
        double H;
        double fy;
        slopey = dy();
        G = -mass * g * slopey;

        H = -frictionConstant*mass*g*(vy1/(Math.sqrt((vx1*vx1)+(vy1*vy1))));
        fy = G + H;
        return fy;
    }
    public double dx(){
        double d = 0; //for now, DO NOT APPLY
        return d;
    }
    public double dy(){
        double d = 0; //for now, DO NOT APPLY
        return d;
    }

    public boolean getBallStopped(){
        return ballStopped;
    }

  /*  public static double[] differentiation(double[] arr) {
        double[] done = new double[arr.length - 1];
        for (int i = 0; i < done.length; i++) {
            done[i] = arr[i]*(arr.length-i-1);
        }
        return done;
    } */

    public static double calcAngle(double x, double y){
        y = -y;
        x = -x;
        double angle;
        angle = (Math.acos(x/(Math.sqrt(x*x + y*y))));
        angle = angle*180/Math.PI;
        if (y>=0)
            return angle;
        else
            return 360-angle;
    }

    public void setGravitationalForce(double gravitationalForce){
        this.g = gravitationalForce;
    }

    public void setFrictionConstant(double frictionConstant){
        this.frictionConstant = frictionConstant;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

}