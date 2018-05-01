package com.mygdx.test;

/**
 * The user enters height values for 16 points which lie on a 4x4 grid (hs), after which the program calculates a bicubic
 * spline which connects these points
 */




public class BicubicInterpolation {

    private double[][] hs; //array containing specified height values for the points [x][y]

    public BicubicInterpolation(double[][] hs){
        this.hs = hs;
    }

    public double findHeightYDimension(double[][] hs, int x){
        return Math.pow(x,3) * (-0.5 * hs[x][0] + 1.5 * hs[x][1] - 1.5 * hs[x][2] + 0.5 * hs[x][3])
                + Math.pow(x,2) * (hs[x][0] - 2.5 * hs[x][1] + 2 * hs[x][2] - 0.5 * hs[x][3])
                + x * (-0.5 * hs[x][0] + 0.5*hs[x][2]) + hs[x][1];
    }

    public double findHeightXandYDimensions(double[][] hs, int y){
        double[] xs = new double[4];
        xs[0] = findHeightYDimension(hs[0][y]);
        xs[0] = findHeightYDimension(hs[0][y]);
        xs[0] = findHeightYDimension(hs[0][y]);
        xs[0] = findHeightYDimension(hs[0][y]);

    }
}
