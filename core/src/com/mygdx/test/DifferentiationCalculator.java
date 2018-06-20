package com.mygdx.test;

import java.util.ArrayList;

public class DifferentiationCalculator {

//    private String f;
//    private ArrayList<Double> coefs = new ArrayList<Double>();
//    private double t;
//    private double y0;
//    private double x;
//    private double y;
    private double h;
    private double friction;
    private double g;
    private double mass;
    private double slopex;
    private double slopey;
    private double p;
    private double vx;
    private double vy;


//    public static void main(String[] args){
//        DifferentiationCalculator dc = new DifferentiationCalculator(0.2, "-f*m*g*(v/sqrt)");
//        dc.calculate();
//    }

    public DifferentiationCalculator(double h, double mass, double friction, double g)
    {
        this.h = h;
        this.mass = mass;
        this.friction = friction;
        this.g = g;
    }

    public void setParam(double vx, double vy, double slopex, double slopey)
    {
        this.vx = vx;
        this.vy = vy;
        this.slopex = slopex;
        this.slopey = slopey;

    }


    //constructor to get all info from above
    public double calculateDifferentiation(char v){
//        t = 10;
//        y0 = 2;
//        double w = y0;
//        double g = t;
//        double count = 0.0;
//        coefs.add(w);
//        //test

        if (v == 'x'){

       //     System.out.println("runkax = " + c4rkx(slopex));
        //    System.out.print("normalx = " + fx(vx, vy, slopex));
            return c4rkx(slopex);
            //return fx(vx, vy, slopex);
        }

        else{

     //       System.out.println("runkay = " + c4rky(slopey));
      //      System.out.print("normaly = " + fy(vx, vy, slopey));
            return c4rky(slopey);
            //return  fy(vx, vy, slopey);

        }


//        for(int j = 1; j <= 2; j++){
//            count += h;
//            w = c4rk(w, count);
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

    public double c4rkx(double slope){
        double k1 = fx(vx, vy, slope);
        double k2 = fx(vx + 1/2*h, vy + 1/2*k1, slope);
        double k3 = fx(vx + 1/2*h, vy + 1/2*k2, slope);
        double k4 = fx(vx + h, vy + k3, slope);

        double k = (k1 + 2*k2 + 2*k3 + k4)/6;

        return k;
    }

    public double c4rky(double slope){
        double k1 = fy(vx, vy, slope);
        double k2 = fy(vx + 1/2*h, vy + 1/2*k1, slope);
        double k3 = fy(vx + 1/2*h, vy + 1/2*k2, slope);
        double k4 = fy(vx + h, vy + k3, slope);

        double k = (k1 + 2*k2 + 2*k3 + k4)/6;

        return k;
    }
//
//    public double AB3SandAM3Smethod(double t, double h){
//
//        double w = coefs.get(coefs.size()-1);
//        double wmin1 = coefs.get(coefs.size()-2);
//        double wmin2 = coefs.get(coefs.size()-3);
//
//        double temp = w + 1/12*h*(23*f(f,t, w) - 16*f(f,t-h, wmin1) + 5*f(f,t-2*h, wmin2));
//
//        return w + 1/24*h*(9*f(f,t+h, temp) + 19*f(f,t, w) - 5*f(f,t-h, wmin1) + f(f,t-2*h, wmin2));
//
//
//
//    }




    public double fx(double vx, double vy, double dx)
    {
        double f = - mass*g*dx - friction*mass*g*(vx/(Math.sqrt((vx*vx)+(vy*vy) + 0.000001))) ;
        return f;
    }

    public double fy(double vx, double vy, double dy)
    {
        double f = - mass*g*dy - friction*mass*g*(vy/(Math.sqrt((vx*vx)+(vy*vy) + 0.000001))) ;
        return f;
    }

//    public double function(String s, double t, double y){
//        ArrayList<String> parts = findParts(s);
//        ArrayList<Boolean> partSign = searchSign(s);
//        System.out.println("-------------parts ");
//        print(parts);
//        for (int i = 0; i < partSign.size(); i++)
//        {
//            if (partSign.get(i))
//                System.out.print("-");
//            else
//                System.out.print("+");
//        }
//        System.out.println("");
//
//
//        double result = 0;
//        for(int i = 0; i < parts.size(); i++){
//            if (partSign.get(i) == false)
//                result += calcPart(parts.get(i), t, y);
//            else
//                result -= calcPart(parts.get(i), t, y);
//        }
//
//        System.out.println(result);
//
//
////        int findPow = DerivativeCalculator.findPow(s);
////        System.out.println("power: " + findPow);
////
////        int findcoef = DerivativeCalculator.findOuterCoeff(s);
////        String findCoefStr = Integer.toString(findcoef);
////        System.out.println("First coefficient: " + findCoefStr);
////
////        ArrayList<Integer> allNumbers;
////        allNumbers = DerivativeCalculator.findNumbers(s);
////        System.out.println("-------------allNumbers:");
////        for (int i : allNumbers)
////        {
////            System.out.print(" " + i);
////        }
////
////        System.out.println("");
//
//        return result;
//    }

//    public ArrayList<Boolean> searchSign(String s){
//        ArrayList<Boolean> signs = new ArrayList<>();
//
//        if (s.charAt(0) == 45)
//            signs.add(true);
//        else
//            signs.add(false);
//
//        for (int i = 1; i < s.length(); i++){
//
//            if (s.charAt(i) == 40)
//                while (s.charAt(i) != 41)
//                    i++;
//            else{
//                if (s.charAt(i) == 43)
//                    signs.add(false);
//                if (s.charAt(i) == 45)
//                    signs.add(true);
//            }
//
//
//        }
//        return signs;
//    }
//
//
//    public ArrayList<String> findParts(String s) {
//        //ArrayList<Character> partsOfEquation = new ArrayList<Character>();
//        ArrayList<String> result = new ArrayList<String>();
//
//        StringBuilder partsOfEquation = new StringBuilder();
//
//        String partOfEq;
//
//        for (int i = 0; i < s.length(); i++) {
//            if (s.charAt(i) == 40) {
//                while (s.charAt(i) != 41) {
//                    partsOfEquation.append(s.charAt(i));
//                    i++;
//                }
//                partsOfEquation.append(s.charAt(i));
//            }
//
//            else{
//                if (s.charAt(i) != 45 && s.charAt(i) != 43)
//                    partsOfEquation.append(s.charAt(i)); //filling in the first part
//
//                else {
//                    // we have the first "node" here then we work on it until it's derived, then we erase everything and work on other, after+
//                    //String builder
//                    if (partsOfEquation.length() != 0){
//                        partOfEq = partsOfEquation.toString();
//
//                        result.add(partOfEq);
//                    }
//
//                    partsOfEquation.setLength(0);
//                }
//
//            }
//
//
//        }
//
//        partOfEq = partsOfEquation.toString();
//
//        result.add(partOfEq);
//
//        return result;
//    }
//
//
//    public double calcPart(String s, double t, double y){
//        ArrayList<String> pieces = new ArrayList<>();
//        StringBuilder partsOfEquation = new StringBuilder();
//
//        String uniquePiece;
//
//        for (int i = 0; i < s.length(); i++) {
//
//            uniquePiece = "";
//            int c = s.charAt(i);
//
//
//            if (c != 42) {
//                //filling in the first part
//                partsOfEquation.append(s.charAt(i));
//            } else {
//                // we have the first "node" here then we work on it until it's derrived, then we erase everything and work on other, after+
//                //String builder
//                uniquePiece = partsOfEquation.toString();
//
//                pieces.add(uniquePiece);
//                partsOfEquation.setLength(0);
//            }
//
//
//        }
//        uniquePiece = partsOfEquation.toString();
//
//        pieces.add(uniquePiece);
//
//        System.out.print("pieces : ");
//        print(pieces);
//
//        double result = 1;
//        for (int i = 0; i < pieces.size(); i++)
//            result *= mult(pieces.get(i), t, y);
//
//        return result;
//    }
//
//
//    public double mult(String s, double t, double y) {
//
//        double result = 0;
//        for (int i = 0; i < s.length(); i++) {
//            if (s.charAt(i) == '^'){
//                String subStr1 = "";
//                for (int j = 0; j < i; j++) {
//                    subStr1 += s.charAt(j);
//                }
//                String subStr2 = "";
//                for (int j = i+1; j < s.length(); j++) {
//                    subStr2 += s.charAt(j);
//                }
//                result = 1;
//                for (int j = 0; j < calcPart(subStr2,t,y); j++) {
//                    result *= calcPart(subStr1,t,y);
//                }
//                return result;
//            }
//        }
//
//        for (int i = 0; i < s.length(); i++) {
//            if (s.charAt(i) > 47 && s.charAt(i) < 58){//0,1,2,3,4,5,6,7,8,9
//                if (i == s.length()-1)
//                    result += ((int) s.charAt(i)-48);
//
//                else
//                    result += ((int) s.charAt(i)-48)*(10^(s.length()-i-2));
//            }
//            else if (s.charAt(i) == 116)//t
//                return t;
//            else if (s.charAt(i) == 121)//y
//                return y;
//            else if (s.length() > 3 && s.charAt(i) == 115 && s.charAt(i+1) == 105 && s.charAt(i+2) == 110){//sin
//                String subStr = "";
//                for (int j = i+3; j < s.length(); j++) {
//                    subStr += s.charAt(j);
//                }
//                return Math.sin(calcPart(subStr, t, y));
//            }
//            else if (s.length() > 3 && s.charAt(i) == 99 && s.charAt(i+1) == 111 && s.charAt(i+2) == 115){//cos
//                String subStr = "";
//                for (int j = i+3; j < s.length(); j++) {
//                    subStr += s.charAt(j);
//                }
//                return Math.cos(calcPart(subStr, t, y));
//            }
//            else if (s.charAt(i) == 112 && s.charAt(i+1) == 105 ) {//pi
//
//                return Math.PI;
//            }
//            else
//                result += 1;
//        }
//
//        return result;
//    }
//
//
//
//
//    public static void print(ArrayList<String> printed) {
//        for (String i : printed)
//            System.out.print(" " + i);
//
//        System.out.println("                         ");
//
//    }


}
