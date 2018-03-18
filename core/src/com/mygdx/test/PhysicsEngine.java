package com.mygdx.test;

import com.badlogic.gdx.math.Rectangle;

public class PhysicsEngine {

    public float moveBall(double direction, double initialSpeed)
    {
        Main m = new Main();
        Rectangle golfBall = m.getGolfBall();
        float xLocation = (float) (golfBall.x * Math.cos(direction) * initialSpeed);
        //golfBall.y = (float) (golfBall.x * Math.sin(direction) * initialSpeed);

        return xLocation;
    }
}
