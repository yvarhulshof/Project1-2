package com.mygdx.test;

import java.util.ArrayList;

public class DifferentiationCalculator {

    public String f;
    public ArrayList<Double> result = new ArrayList<Double>();
    double y;
    double t;
    double y0;
    double h;
//constructor to get all info from above
    public void calculate(){

        double w = y0;
        double g = t;
        result.add(w);

        int i = 1;
        for(int j = 1; j < 3; j++){
            i++;
            t += h;
            w = c4rk(h, w, t);
            result.add(w)

        }

        while (t < g-h){
            t += h;
            w = AB3SandAM3Smethod(t, h, i);
            i ++;
            results.add(w);
        }



        ArrayList<String> parts = DerivativeCalculator.findCoefErasePlus(equation);
        System.out.println("-------------parts ");
        print(parts);
    }



    public static void print(ArrayList<String> printed) {
        for (String i : printed)
            System.out.print(" " + i);

        System.out.println("                         ");

    }


}
