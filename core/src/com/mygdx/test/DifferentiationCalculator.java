package com.mygdx.test;

import java.util.ArrayList;

public class DifferentiationCalculator {

    public String f;
    public ArrayList<Double> coefs = new ArrayList<Double>();
    double t;
    double y0;
    double h;

    public static void main(String[] args){
        DifferentiationCalculator dc = new DifferentiationCalculator();
        dc.calculate();
    }
//constructor to get all info from above
    public void calculate(){
        h = 0.5;
        t = 10;
        y0 = 2;
        double w = y0;
        double g = t;
        double count = 0.0;
        coefs.add(w);
        f = "-3x^2+16sin(y+x-4)-54x";
        //test
        f(f, 54, 69);

//
//        for(int j = 1; j <= 2; j++){
//            count += h;
//            w = c4rk(h, w, count);
//            coefs.add(w);
//
//        }
//
//        while (count < g-h){
//            count += h;
//            w = AB3SandAM3Smethod(t, h);
//            coefs.add(w);
//        }

    }

//    public double c4rk(double h, double w, double t){
//        double k1 = h*f(t,w);
//        double k2 = h*f(t + 1/2*h, w + 1/2*k1);
//        double k3 = h*f(t + 1/2*h, w + 1/2*k2);
//        double k4 = h*f(t + h, w + k3);
//
//        double k = 1/6*(k1 + 2*k2 + 2*k3 + k4);
//
//        return w + k;
//    }

//    public double AB3SandAM3Smethod(double t, double h){
//
//        double w = coefs.get(coefs.size()-1);
//        double wmin1 = coefs.get(coefs.size()-2);
//        double wmin2 = coefs.get(coefs.size()-3);
//
//        double temp = w + 1/12*h*(23*f(t, w) - 16*f(t-h, wmin1) + 5*f(t-2*h, wmin2));
//
//        return w + 1/24*h*(9*f(t+h, temp) + 19*f(t, w) - 5*f(t-h, wmin1) + f(t-2*h, wmin2));
//
//
//
//    }






    public double f(String s, double t, double y){
        ArrayList<String> parts = findParts(s);
        ArrayList<Boolean> partSign = searchSign(s);
        System.out.println("-------------parts ");
        print(parts);
        for (int i = 0; i < partSign.size(); i++)
        {
            if (partSign.get(i))
                System.out.print("-");
            else
                System.out.print("+");
        }
        System.out.println("");


        double result = 0;
        for(int i = 0; i < parts.size(); i++){
            if (partSign.get(i) == false)
                result += calcPart(parts.get(i));
            else
                result -= calcPart(parts.get(i));
        }

        System.out.println(result);


//        int findPow = DerivativeCalculator.findPow(s);
//        System.out.println("power: " + findPow);
//
//        int findcoef = DerivativeCalculator.findOuterCoeff(s);
//        String findCoefStr = Integer.toString(findcoef);
//        System.out.println("First coefficient: " + findCoefStr);
//
//        ArrayList<Integer> allNumbers;
//        allNumbers = DerivativeCalculator.findNumbers(s);
//        System.out.println("-------------allNumbers:");
//        for (int i : allNumbers)
//        {
//            System.out.print(" " + i);
//        }
//
//        System.out.println("");

        return 1;
    }

    public ArrayList<Boolean> searchSign(String s){
        ArrayList<Boolean> signs = new ArrayList<>();

        if (s.charAt(0) == 45)
            signs.add(true);
        else
            signs.add(false);

        for (int i = 1; i < s.length(); i++){

            if (s.charAt(i) == 40)
                while (s.charAt(i) != 41)
                    i++;
            else{
                if (s.charAt(i) == 43)
                    signs.add(false);
                if (s.charAt(i) == 45)
                    signs.add(true);
            }


        }
        return signs;
    }


    public ArrayList<String> findParts(String s) {
        //ArrayList<Character> partsOfEquation = new ArrayList<Character>();
        ArrayList<String> result = new ArrayList<String>();

        StringBuilder partsOfEquation = new StringBuilder();

        String partOfEq;

        for (int i = 0; i < s.length(); i++) {

            if (s.charAt(i) == 40) {
                while (s.charAt(i) != 41) {
                    partsOfEquation.append(s.charAt(i));
                    i++;
                }
                partsOfEquation.append(s.charAt(i));
            }

            else{
                if (s.charAt(i) != 45 && s.charAt(i) != 43)
                    partsOfEquation.append(s.charAt(i)); //filling in the first part

                else {
                    // we have the first "node" here then we work on it until it's derived, then we erase everything and work on other, after+
                    //String builder
                    if (partsOfEquation.length() != 0){
                        partOfEq = partsOfEquation.toString();

                        result.add(partOfEq);
                    }

                    partsOfEquation.setLength(0);
                }

            }


        }

        partOfEq = partsOfEquation.toString();

        result.add(partOfEq);

        return result;
    }


    public double calcPart(String s){
        double result = 0;

        result = 1;


        return result;
    }
//
//    public double calcPower(String string){
//
//    }














    public static void print(ArrayList<String> printed) {
        for (String i : printed)
            System.out.print(" " + i);

        System.out.println("                         ");

    }


}
