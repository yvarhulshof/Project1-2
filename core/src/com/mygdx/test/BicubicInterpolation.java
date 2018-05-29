package com.mygdx.test;

/**
 * The user enters height values for 16 points which lie on a 4x4 grid (hs), after which the program calculates a bicubic
 * spline which connects these points
 */



public class BicubicInterpolation {

    private double[][] hs; //array containing specified height values for the points [x][y]
    private double[] h_of_xs;

    public BicubicInterpolation(double[][] hs) {
        this.hs = hs;
        this.h_of_xs = hs[0];
    }

    public double findHeightYDimension(double[] h_of_xs, double x){
        /*return Math.pow(x,3) * (-0.5 * h_of_xs[0] + 1.5 * h_of_xs[1] - 1.5 * h_of_xs[2] + 0.5 * h_of_xs[3])
                + Math.pow(x,2) * (h_of_xs[0] - 2.5 * h_of_xs[1] + 2 * h_of_xs[2] - 0.5 * h_of_xs[3])
                + x * (-0.5 * h_of_xs[0] + 0.5*h_of_xs[2]) + h_of_xs[1];
        */

        return h_of_xs[1] + 0.5 * x*(h_of_xs[2] - h_of_xs[0] + x*(2.0*h_of_xs[0] - 5.0*h_of_xs[1] + 4.0*h_of_xs[2] - h_of_xs[3] + x*(3.0*(h_of_xs[1] - h_of_xs[2]) + h_of_xs[3] - h_of_xs[0])));
    }

    public double findHeightXandYDimensions(double x, double y){
        double[] xs = new double[4];
        xs[0] = findHeightYDimension(hs[0],y);
        xs[1] = findHeightYDimension(hs[1],y);
        xs[2] = findHeightYDimension(hs[2],y);
        xs[3] = findHeightYDimension(hs[3],y);
        return findHeightYDimension(xs,x);
    }
}
