package com.mygdx.test;

import com.badlogic.gdx.math.Circle;

public class GolfBall extends Circle {

    private float vx2;
    private float vy2;

    public GolfBall(float vx2, float vy2){
        super(800 / 2 - 64 / 2, 20, 25);
        this.vx2 = vx2;
        this.vy2 = vy2;
    }

    public void setVX2(float vx2){
        this.vx2 = vx2;
    }

    public void setVY2(float vy2){
        this.vy2 = vy2;
    }

    public float getVx2() {
        return vx2;
    }

    public float getVy2() {
        return vy2;
    }
}
