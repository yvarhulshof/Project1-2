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
    float xChange;
    float yChange;
    float lastShotX;
    float lastShotY;

    public PhysicsEngine(GolfBall golfBall){
        this.golfBall = golfBall;
    }

    public void moveBall(double direction, double initialSpeed){

        ballStopped = false;

        if(initialCall) {
            startTime = System.nanoTime() / 1000000000.0; //defining the value for which t = 0
            initialCall = false;

            double directionCoefficientX = (Math.cos(Math.toRadians(direction)));
//            System.out.println("directionCoefficientX = " + directionCoefficientX);
            double directionCoefficientY = (Math.sin(Math.toRadians(direction)));
//            System.out.println("directionCoefficienty = " + directionCoefficientY);
//            System.out.println("total direction = " + Math.sqrt(Math.pow(directionCoefficientX, 2) + Math.pow(directionCoefficientY, 2)));



            golfBall.setVX2((float) (directionCoefficientX*initialSpeed));
            System.out.println("initial vx2 " + golfBall.getVx2());
            golfBall.setVY2((float) (directionCoefficientY*initialSpeed));
            System.out.println("initial vy2 " + golfBall.getVy2());

            lastShotX = golfBall.x;
            lastShotY = golfBall.y;
        }

        initialCall = false;


        vx1 = golfBall.getVx2();
        //System.out.println("initial vx1 " + vx1);
        vy1 = golfBall.getVy2();

        if ((Math.abs(vx1 + (float) findfx()) <= 20) && (-mass * g * dx() >= -3) && (-mass * g * dy() >= -3) && (Math.abs(vy1 + (float) findfy())) <= 20){
            golfBall.setVX2(0);
            golfBall.setVY2(0);
            ballStopped = true;
            initialCall= true;
        }
        else{
            golfBall.setVX2(vx1 + (float) findfx());
            golfBall.setVY2(vy1 + (float) findfy());
        }

        //vx2 = vx1 + findfx();
        double elapsedTime = System.nanoTime() / 1000000000.0 - startTime; //defining the value of t for the current call of render()

//        double gravitationalPull = 9.81 / Math.pow(elapsedTime, 2); //not used yet, only comes in to play when the ball is on a slope

        //change in x and y during the elapsed time
         xChange = (float) ((1.0/60.0)*vx1);
         yChange = (float) ((1.0/60.0)*vy1);


//        if(vx1 <=0.1 && (vy1  <=0.1 || vx1 <= -0.1) && vy1 <= -0.1){
//            golfBall.setVX2(0);
//            golfBall.setVX2(0);
//
//        }

        //  System.out.println( " vx1 = " + vx1);
          //System.out.println("vy1 = " + vy1);

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
//        System.out.println("speedX " + golfBall.getVx2());
//        System.out.println("speedY : " + golfBall.getVy2());
//        System.out.println("balloccX : " + golfBall.x );
//        System.out.println("possssX " +positionX());
//        System.out.println("balloccY : " + golfBall.y );
//        System.out.println("posssY " + positionY());
    }

    public float positionX(){

        return lastShotX;
    }
    public float positionY(){

        return lastShotY;
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
        double d = 0.1 + 0.06*(golfBall.x); //for now, DO NOT APPLY
        double e = 0;
        double f = - 2*Math.cos(golfBall.x);
        return e;
    }
    public double dy(){
        double d = 0.2; //for now, DO NOT APPLY
        double e = 0;
        double f = - 2*Math.cos(golfBall.y);
        return e;
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