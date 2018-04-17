package com.mygdx.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public class PhysicsEngine {

    GolfBall golfBall;
    Circle goal;
    Rectangle water;
    boolean ballStopped = true;
    boolean ballBlocked = false;

    boolean ballBlockedX = false;
    boolean ballBlockedY = false;


    static boolean initialCall = true;
    static double startTime = 0;
    private boolean usingMethod3 = false;

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

    private TiledMapTileLayer collisionLayer;

    private float collisionCoordsX;
    private float collisionCoordsY;

    public PhysicsEngine(GolfBall golfBall, TiledMapTileLayer collisionLayer){
        this.golfBall = golfBall;
        this.collisionLayer = collisionLayer;
    }

    public void moveBall(double direction, double initialSpeed){

        float oldXCoords = golfBall.getXCoords();
        float oldYCoords = golfBall.getYCoords();

        float tileWidth = collisionLayer.getTileWidth();
        float tileHeight = collisionLayer.getTileHeight();

        boolean collisionX = false;
        boolean collisionY = false;

        ballStopped = false;

        if(initialCall) {
            startTime = System.nanoTime() / 1000000000.0; //defining the value for which t = 0
            initialCall = false;

            double directionCoefficientX = (Math.cos(Math.toRadians(direction)));
            double directionCoefficientY = (Math.sin(Math.toRadians(direction)));


            golfBall.setVX2((float) (directionCoefficientX*initialSpeed));
            System.out.println("initial vx2 " + golfBall.getVx2());
            golfBall.setVY2((float) (directionCoefficientY*initialSpeed));
            System.out.println("initial vy2 " + golfBall.getVy2());

            lastShotX = golfBall.x;
            lastShotY = golfBall.y;
        }

        initialCall = false;


        vx1 = golfBall.getVx2();
        vy1 = golfBall.getVy2();
/*
        if(!usingMethod3)
        {
            if      (((Math.abs(vx1 + (float) findfx()) <= 20) &&
                    (-mass * g * dx() == 0) && (-mass * g * dy() == 0) &&
                    (Math.abs(vy1 + (float) findfy())) <= 20) ||
                    (ballBlocked))
            {
                //System.out.println("check");
                golfBall.setVX2(0);
                golfBall.setVY2(0);
                ballStopped = true;
                ballBlocked = false;
                initialCall = true;
            } else {
                golfBall.setVX2(vx1 + (float) findfx());
                golfBall.setVY2(vy1 + (float) findfy());
            }
        }
*/

        //the ball is stopped if: its abs. speed is less than 20
        //and either the gravity inflicted on the ball is 0
        //or the ball is blocked in both x and y directions

        if(!usingMethod3)
        {
            if      (((Math.abs(vx1 + (float) findfx()) <= 20) && ((Math.abs(vy1 + (float) findfy())) <= 20)) &&
                    ((-mass * g * dx() == 0) && (-mass * g * dy() == 0)) || (ballBlockedX && ballBlockedY))
            {
                ballStopped = true;
                initialCall = true;
            }
            else {
                if(!ballBlockedX) golfBall.setVX2(vx1 + (float) findfx());
                if(!ballBlockedY) golfBall.setVY2(vy1 + (float) findfy());
            }
        }
        System.out.println("ballStopped = " + ballStopped);


        double elapsedTime = System.nanoTime() / 1000000000.0 - startTime; //defining the value of t for the current call of render()


        //change in x and y during the elapsed time
         xChange = (float) ((1.0/60.0)*vx1);
         yChange = (float) ((1.0/60.0)*vy1);



        //checking if the ball collides with the squares surrounding it

        int xOffset = 0;
        int yOffset = 0;

        if(golfBall.getVx2() < 0){
            //top left
            TiledMapTileLayer.Cell collisionCellTopLeft =  collisionLayer.getCell((int) (golfBall.x / tileWidth), (int) ((golfBall.y + 2*golfBall.radius) / tileHeight));
            collisionX = collisionCellTopLeft.getTile().getProperties().containsKey("solid");

            //center left
            //if we already found it collides with top left we don't want to check again because we would set the collision variable to false
            if(!collisionX) {
                TiledMapTileLayer.Cell collisionCellCenterLeft = collisionLayer.getCell((int) (golfBall.x / tileWidth), (int) ((golfBall.y + golfBall.radius) / tileHeight));
                collisionX = collisionCellTopLeft.getTile().getProperties().containsKey("solid");
            }

            //bottom left
            //if we already found it collides with top left or center left we don't want to check again because we would set the collision variable to false
            if(!collisionX) {
                TiledMapTileLayer.Cell collisionCellBottomLeft = collisionLayer.getCell((int) (golfBall.x / tileWidth), (int) ((golfBall.y) / tileHeight));
                collisionX = collisionCellTopLeft.getTile().getProperties().containsKey("solid");
            }

            if(collisionX) xOffset = 10;

        }
        else if(golfBall.getVx2() > 0){
            //top right
            TiledMapTileLayer.Cell collisionCellTopRight =  collisionLayer.getCell((int) ((golfBall.x + 2*golfBall.radius) / tileWidth), (int) ((golfBall.y + golfBall.radius) / tileHeight));
            collisionX = collisionCellTopRight.getTile().getProperties().containsKey("solid");

            //center right
            if(!collisionX) {
                TiledMapTileLayer.Cell collisionCellCenterRight = collisionLayer.getCell((int) ((golfBall.x + golfBall.radius) / tileWidth), (int) ((golfBall.y + golfBall.radius / 2) / tileHeight));
                collisionX = collisionCellTopRight.getTile().getProperties().containsKey("solid");
            }

            //bottom right
            if(!collisionX) {
                TiledMapTileLayer.Cell collisionCellBottomRight = collisionLayer.getCell((int) (golfBall.x / tileWidth), (int) ((golfBall.y) / tileHeight));
                collisionX = collisionCellTopRight.getTile().getProperties().containsKey("solid");
            }

            if(collisionX) xOffset = -10;
        }

        //if we have a collision on X, we set the balls xCoords to those of the previous frame and set its speed in x direction to 0
        if(collisionX){
            golfBall.x = oldXCoords+xOffset;
            golfBall.setVX2(0);
            ballBlockedX = true;
        }


        if(golfBall.getVy2() < 0) {
            //bottom left
            TiledMapTileLayer.Cell collisionCellBottomLeft = collisionLayer.getCell((int) ((golfBall.x) / tileWidth), (int) ((golfBall.y / tileHeight)));
            collisionY = collisionCellBottomLeft.getTile().getProperties().containsKey("solid");

            //bottom center
            if (!collisionY) {
                TiledMapTileLayer.Cell collisionCellBottomCenter = collisionLayer.getCell((int) ((golfBall.x + golfBall.radius) / tileWidth), (int) ((golfBall.y + golfBall.radius) / tileHeight));
                collisionY = collisionCellBottomCenter.getTile().getProperties().containsKey("solid");
            }

            //bottom right
            if (!collisionY) {
                TiledMapTileLayer.Cell collisionCellBottomRight = collisionLayer.getCell((int) ((golfBall.x + 2 * golfBall.radius) / tileWidth), (int) ((golfBall.y) / tileHeight));
                collisionY = collisionCellBottomRight.getTile().getProperties().containsKey("solid");
            }

            if(collisionY) yOffset = 10;
        }
        else if(golfBall.getVy2() > 0) {
                //top left
                TiledMapTileLayer.Cell collisionCellTopLeft = collisionLayer.getCell((int) ((golfBall.x) / tileWidth), (int) ((golfBall.y + 2 * golfBall.radius) / tileHeight));
                collisionY = collisionCellTopLeft.getTile().getProperties().containsKey("solid");

                //top center
                if (!collisionY) {
                    TiledMapTileLayer.Cell collisionCellTopCenter = collisionLayer.getCell((int) ((golfBall.x + golfBall.radius) / tileWidth), (int) ((golfBall.y + 2 * golfBall.radius) / tileHeight));
                    collisionY = collisionCellTopCenter.getTile().getProperties().containsKey("solid");
                }

                //top right
                if (!collisionY) {
                    TiledMapTileLayer.Cell collisionCellTopRight = collisionLayer.getCell((int) ((golfBall.x + 2 * golfBall.radius) / tileWidth), (int) ((golfBall.y + 2 * golfBall.radius) / tileHeight));
                    collisionY = collisionCellTopRight.getTile().getProperties().containsKey("solid");
                }

            if(collisionY) yOffset = -10;
            }


        if (collisionY){
            golfBall.y = oldYCoords+yOffset;
            golfBall.setVY2(0);
            ballBlockedY = true;
        }

        /*if(!ballBlockedX)*/ golfBall.x += xChange;
        /*if(!ballBlockedY)*/ golfBall.y += yChange;
            //z = x+y;
        //}
        //else {
        //    ballStopped = true;
        //    initialCall = true;
        //}
        System.out.println("speedX " + golfBall.getVx2());
        System.out.println("speedY : " + golfBall.getVy2());
        System.out.println("balloccX : " + golfBall.x );
        //System.out.println("possssX " +positionX());
        System.out.println("balloccY : " + golfBall.y );
        //System.out.println("posssY " + positionY());
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

        H = -frictionConstant*mass*g*(vx1/(Math.sqrt((vx1*vx1)+(vy1*vy1) + 0.000001)));
        fx = G + H;
        return fx;
    }
    public double findfy(){
        double G; //= 0; // = 0; for now, DO NOT APPLY
        double H;
        double fy;
        slopey = dy();
        G = -mass * g * slopey;

        H = -frictionConstant*mass*g*(vy1/(Math.sqrt((vx1*vx1)+(vy1*vy1) + 0.000001)));
        fy = G + H;
        return fy;
    }
    public double dx(){
        double d = 1;
        return d;
    }
    public double dy(){
        double d = 1;
        return d;
    }

    public boolean getBallStopped(){
        return ballStopped;
    }

    public boolean getBallBlocked(){
        return ballBlocked;
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

    public void useMethod3(boolean used){
        usingMethod3 = used;
    }

    public boolean isBallBlockedX() {
        return ballBlockedX;
    }

    public void setBallBlockedX(boolean ballBlockedX) {
        this.ballBlockedX = ballBlockedX;
    }

    public boolean isBallBlockedY() {
        return ballBlockedY;
    }

    public void setBallBlockedY(boolean ballBlockedY) {
        this.ballBlockedY = ballBlockedY;
    }
}