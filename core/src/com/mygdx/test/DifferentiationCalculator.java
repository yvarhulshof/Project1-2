package com.mygdx.test;

import java.util.ArrayList;

public class DifferentiationCalculator {

    public String f;
    public ArrayList<Double> coefs = new ArrayList<Double>();
    double t;
    double y0;
    double h;
//constructor to get all info from above
    public void calculate(){
        h = 0.5;
        t = 10;
        y0 = 2;
        double w = y0;
        double g = t;
        double count = 0.0;
        coefs.add(w);

        for(int j = 1; j <= 2; j++){
            count += h;
            w = c4rk(h, w, count);
            coefs.add(w);

        }

        while (count < g-h){
            count += h;
            w = AB3SandAM3Smethod(t, h);
            coefs.add(w);
        }



        ArrayList<String> parts = DerivativeCalculator.findCoefErasePlus(equation);
        System.out.println("-------------parts ");
        print(parts);
    }

    public double c4rk(double h, double w, double t){
        double k1 = h*f(t,w);
        double k2 = h*f(t + 1/2*h, w + 1/2*k1);
        double k3 = h*f(t + 1/2*h, w + 1/2*k2);
        double k4 = h*f(t + h, w + k3);

        double k = 1/6*(k1 + 2*k2 + 2*k3 + k4);

        return w + k;
    }

    public double AB3SandAM3Smethod(double t, double h){

        double w = coefs.get(coefs.size()-1);
        double wmin1 = coefs.get(coefs.size()-2);
        double wmin2 = coefs.get(coefs.size()-3);

        double temp = w + 1/12*h*(23*f(t, w) - 16*f(t-h, wmin1) + 5*f(t-2*h, wmin2));

        return w + 1/24*h*(9*f(t+h, temp) + 19*f(t, w) - 5*f(t-h, wmin1) + f(t-2*h, wmin2));



    }




    public static double f(double t, double y){
        // i need your help here :)
    }




    public static void print(ArrayList<String> printed) {
        for (String i : printed)
            System.out.print(" " + i);

        System.out.println("                         ");

    }


}
