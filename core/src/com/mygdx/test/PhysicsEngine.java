package com.mygdx.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class PhysicsEngine {

    private Rectangle golfBall;
    private Vector2 ballCoordinates;

    static boolean initialCall = true;
    static double startTime = 0;

    public PhysicsEngine(Rectangle golfBall, Vector2 ballCoordinates){
        this.golfBall = golfBall;
        this.ballCoordinates = ballCoordinates;
    }

    public float moveBall(double direction, double initialSpeed)
    {
        float xLocation = (float) (golfBall.x * Math.cos(Math.toDegrees(direction)) * initialSpeed);

        //golfball.x = (float) (golfBall.x * Math.cos(Math.toDegrees(direction)) * initialSpeed);
        //golfBall.y = (float) (golfBall.x * Math.sin(direction) * initialSpeed);

        return xLocation;
    }

    public void moveBallVector(double direction, double initialSpeed){

        if(initialCall) startTime = System.nanoTime() / 1000000000.0;
        initialCall = false;

        float xLocation = (float) (Math.cos(Math.toRadians(direction)) * initialSpeed * Math.pow(System.nanoTime() / 1000000000.0 - startTime,1.2));
        float yLocation = (float) (Math.sin(Math.toRadians(direction)) * initialSpeed * Math.pow(System.nanoTime() / 1000000000.0 - startTime,1.2));

        //float xLocation = (float) (Math.cos(Math.toRadians(direction)) * initialSpeed * Math.pow(Gdx.graphics.getDeltaTime(),1.2));
        //float yLocation = (float) (Math.sin(Math.toRadians(direction)) * initialSpeed * Math.pow(Gdx.graphics.getDeltaTime(),1.2));

        //float xLocation = (float) (golfBall.x * Math.cos(Math.toDegrees(direction)) * initialSpeed * Gdx.graphics.getDeltaTime());
        //float yLocation = (float) (golfBall.y * Math.sin(Math.toDegrees(direction)) * initialSpeed * Gdx.graphics.getDeltaTime());

        golfBall.x += xLocation;
        golfBall.y += yLocation;

        //ballCoordinates.add(xLocation,yLocation);
        //golfBall.x = ballCoordinates.x;
        //golfBall.y = ballCoordinates.y;
        //return ballCoordinates;
    }
}
