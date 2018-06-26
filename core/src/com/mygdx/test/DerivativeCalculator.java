/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.test;
;

import com.sun.nio.sctp.IllegalUnbindException;
import java.util.ArrayList;

/**
 *
 * @author sarit
 */
public class DerivativeCalculator {

    /**
     * This program is designed to derive mostly trigonometric, exponential and polynomial equations.
     * When calling the constructor please write trigonometric part first as the signs are copied at the end.
     * 
     */
   // private static String equation = "65cos(x^2)*4x^3";
    public static ArrayList<String> result = new ArrayList<String>();

    public  DerivativeCalculator(String equation) {

            ArrayList<String> parts = findCoefErasePlus(equation);// cuts in peaces

            ArrayList<Integer> findcoef = findCoeffOfXorY(equation);
            ArrayList<String> derPow = powerDerivation(findcoef, equation);

            ArrayList<String> trig = trigDerivative(equation);

            for( String each: trig){
                result.add(each);
            }

            for( String every: derPow){

                result.add(every);
            }


        }
        public String giveBackResult(){
            String ourResult=result.toString();
            return ourResult;

        }
//used for testing
    /*public static void main(String[] args) {

        ArrayList<String> parts = findCoefErasePlus(equation);
        System.out.println("-------------parts ");
        print(parts);
        // int findPow = findPow("3x^2+16sinx");
        //System.out.println("power: " + findPow);
        ArrayList<Integer> findcoef = findCoeffOfXorY(equation);
        System.out.println("-------------allNumbers:");
        for (int i : findcoef) {
            System.out.print(" " + i);
        }


         ArrayList<Integer> allNumbers;
        allNumbers = findNumbers(equation);
        System.out.println("-------------allNumbers:");
        for (int i : allNumbers) {
            System.out.print(" " + i);
        } 

        System.out.println("");
         
   ArrayList<String> allLetters = findLetters(equation);
        System.out.println("--------------allLetters ");

        print(allLetters);
      

        System.out.println("");
         
        ArrayList<String> derPow = powerDerivation(findcoef);//neeeeeeeeeeeeeeeeeeeeded
        System.out.println("-----------------powerDeriv");
        print(derPow);
        ArrayList<String> trig = trigDerivative(equation);

        System.out.printf("trig: %s%n", trig.toString());

         ArrayList<String> exp = exponentialDerivation(equation);
        System.out.println("-------------------exp");
        print(exp);
         
    }
    */


   /* public static void print(ArrayList<String> printed) {
        for (String i : printed) {
            System.out.print(" " + i);
        }

        System.out.println("                         ");

    }*/

    /**
     * *************************************************************************************************************************************************
     *
     * should be the beginning of everything and erases plus/minus , so we
     * continue to the next plus/minus where the back letters will be stored and
     * worked on
     * **********************************************************************************************************************************************
     * @param substring
     * @return
     */
    public static ArrayList<String> findCoefErasePlus(String substring) {
        //ArrayList<Character> partsOfEquasion = new ArrayList<Character>();
        ArrayList<String> results = new ArrayList<String>();

        StringBuilder partsOfEquasion = new StringBuilder();

        String partOfEq;

        for (int i = 0; i < substring.length(); i++) {
            if (substring.charAt(i) == 40) {
                while (substring.charAt(i) == 41) {
                    i++;
                }
            }

            if (substring.charAt(i) != 45 && substring.charAt(i) != 43) {
                //filling in the first part
                partsOfEquasion.append(substring.charAt(i));

            } else {
                // we have the first "node" here then we work on it until it's derrived, then we erase everything and work on other, after+
                //String builder
                if (partsOfEquasion.length() != 0) {
                    partsOfEquasion.append(substring.charAt(i));
                    partOfEq = partsOfEquasion.toString();
                    results.add(partOfEq);
                }

                partsOfEquasion.setLength(0);
            }

        }
        partOfEq = partsOfEquasion.toString();

        results.add(partOfEq);

        return results;
    }

    /**
     * *************************************************************************************************************************
     *
     * checks if there is a POWER(in the beginning) do not feed it the whole
     * equation it will result into an error as the limit of power is assumed
     *
     *****************************************************************************************************************************
     * @param ourSubstring part of equation or equation we put in
     * @return the power found by find numbers with cutting the string where
     * power is anticipated to be
     */
    public static int findPow(String ourSubstring) {
        for (int i = 0; i < ourSubstring.length(); i++) {
            if (ourSubstring.charAt(i) == 94) {// (!ourSubstring.contains("+")|| !ourSubstring.contains("-"))) {
                ArrayList<Integer> numbers = findNumbers(ourSubstring.substring(i + 1, ourSubstring.length()));
                return numbers.get(0);
            }
        }

        return 1;// working
    }

    /**
     * *****************************************************************************************************************************
     * //checks first COEFICIENT in a part, before + or-
     * ******************************************************************************************************************
     * @param substring the part of equation
     * @return the first number in the given string
     */
    static int i;

    public static ArrayList<Integer> findCoeffOfXorY(String eq) {
        ArrayList<String> parts = findCoefErasePlus(eq);
        ArrayList<Integer> coefficient = new ArrayList<>();

        for (String part : parts) {
            if (part.contains("y")) {
                i = part.indexOf("y");
            } else if (part.contains("x")) {
                i = part.indexOf("x");

            }

            int coef = toNotDuplicate(part, i);
            coefficient.add(coef);

        }
        return coefficient;
    }

    public static int findOutterCoeffOfXorY(String part) {
        //ArrayList<String> parts = findCoefErasePlus(substring);
        // ArrayList<Integer> coefficient = new ArrayList<>();

        //  for (String part : parts) {
        if (part.contains("y") || part.contains("x")) {
            int i;
            try {
                i = part.indexOf("y");

            } catch (Exception ex) {
                i = part.indexOf("x");
            }

            int coef = toNotDuplicate(part, i);
            // coefficient.add(coef);
            return coef;
        }
        return 1;

    }

    // try{substring.charAt(i)}
    //  for (int i = 0; i < substring.indexOf("x") || i < substring.indexOf("y"); i++) {
    //   if (substring.charAt(i) != 43 && substring.charAt(i) != 45 && substring.charAt(i+1)!= 94) {
    //}
    //}
    //return 1; // working
    public static int toNotDuplicate(String part, int i) {
        ArrayList<Integer> numbersNew = new ArrayList<>();
        if ((i - 1) > -1) {

            if (part.charAt(i - 1) > 48 && part.charAt(i - 1) < 57) {
                numbersNew = findNumbers(part);
                return numbersNew.get(0);
            } else {

                numbersNew.add(1);

            }
        } else {
            numbersNew.add(1);
        }
        return numbersNew.get(0);
    }

    /**
     * ********************************************************************************************************************
     * finds all existing numbers in the eqaution
     * *****************************************************************************************************************************************************
     * @param eqPart
     * @return
     */
    public static ArrayList<Integer> findNumbers(String eqPart) {

        ArrayList<Integer> numbers = new ArrayList<Integer>();

        StringBuilder number = new StringBuilder();

        boolean search = false;
        for (int i = 0; i < eqPart.length(); i++) {
            if (eqPart.charAt(i) > 47 && eqPart.charAt(i) < 58) {
                search = true;
                //char currNumber=substring.charAt(i);
                // parse chat to str then to int
                // Character.toString(currNumber);
                // numbers.add(Integer.parseInt(currNumber));

                number.append(eqPart.charAt(i));//add the number at hand

            } else {
                if (search) { //if before it was a number

                    String strNum = number.toString();
                    numbers.add(Integer.parseInt(strNum));
                    search = false;
                    number.setLength(0);

                }
            }

        }
        try {
            String strNumbers;
            strNumbers = number.toString();
            numbers.add(Integer.parseInt(strNumbers));
            return numbers;
        } catch (NumberFormatException ex) { // handle your exception
            numbers.add(1);
            return numbers;

        }

    }

    /**
     * *************************************************************************************************************************************************
     * gives all existing letters in the given eq
     *
     *
     **************************************************************************************************************************************************
     * @param substring whole equation
     * @return
     */
    public static ArrayList<String> findLetters(String substring) {

        ArrayList<String> letters;
        letters = new ArrayList<String>();

        StringBuilder letter = new StringBuilder();
        boolean search = false;

        for (int i = 0; i < substring.length(); i++) {
            if (substring.charAt(i) > 67 && substring.charAt(i) < 122 && substring.charAt(i) != (94)) {
                search = true;
                letter.append(substring.charAt(i));
            } else {
                if (search) {

                    letter = new StringBuilder();

                    search = false;

                }
            }
        }
        letters.add(letter.toString());
        return letters;
    }

    /**
     * **************************************************************************************************************************************
     * Table, 1st law: power derivation !!!!please make sure roots are
     * represented as powers!!!!
     *
     *
     *
     * @param firstCoeff
     *
     * @return
     * *********************************************************************************************************
     */
    static int indexOfYorX;

    public static ArrayList<String> powerDerivation(ArrayList<Integer> firstCoeff,String equation) {
        ArrayList<String> parts = findCoefErasePlus(equation);

        ArrayList<String> powerDerived = new ArrayList<String>();
        for (int part = 0; part < parts.size(); part++) {
            int findPow = findPow(parts.get(part));

            if (parts.get(part).contains("x")) {
                if (parts.get(part).indexOf("x") == -1) {
                    powerDerived.add("1");
                }

            } else if (parts.get(part).contains("y")) {
                indexOfYorX = parts.get(part).indexOf("y");
            }

            if (parts.get(part).contains("^") && (parts.get(part).contains("y") || parts.get(part).contains("x"))) {
                //      parts.stream().filter((part) -> (part.contains("^") && (part.contains("x") || part.contains("y")))).forEachOrdered((part) -> {

                int inndexOfPow = parts.get(part).indexOf("^");
                String beforePowMark = Character.toString(parts.get(part).charAt(inndexOfPow - 1));
                if (beforePowMark.equals("y") || beforePowMark.equals("x")) {
                    String powS = Integer.toString(findPow);
                    //for (int j = 0; j < part.length(); j++) {
                    // if ((part.charAt(j) == 'x' || part.charAt(j) == 'y') && part.charAt(j + 1) == '^') {
                    int coef;
                    coef = firstCoeff.get(part);

                    //int derivedCoef = coef * pow;
                    String strNewCoef = Integer.toString(coef * findPow);
                    String strCoef = Integer.toString(coef);
                    String newPow = Integer.toString(findPow - 1);

                    try {
                        if (parts.get(part).charAt(inndexOfPow - 2) > 48 && parts.get(part).charAt(inndexOfPow - 2) < 57) {
                            String part1 = parts.get(part).replace(powS, newPow);
                            String part2 = part1.replace(strCoef, strNewCoef);
                            powerDerived.add(part2); // derived power
                        }
                    } catch (IndexOutOfBoundsException ex) { // add number before y or x
                        char[] inParts = parts.get(part).toCharArray();
                        StringBuilder addOne = new StringBuilder();
                        addOne.append(1);
                        for (int i = 1; i < inParts.length + 1; i++) {
                            addOne.append(inParts[i - 1]);
                        }
                        String newPart = addOne.toString();
                        String part1 = newPart.replace(powS, newPow);
                        String part2 = part1.replace("1", strNewCoef);

                        powerDerived.add(part2);
                    }

                }
            } else {

                int indexNegative = indexOfYorX - 1;
                if (parts.get(part).indexOf((indexNegative)) == -1) {
                    powerDerived.add("1");
                } else {
                    powerDerived.add(parts.get(part));
                }

            }

        }
        return powerDerived;
    }

    /**
     * ***********************************************************************************************************************************
     * replaces the trigonometric part in the equation, doesn't touch the rest
     * //Table law: trigonometry
     * ******************************************************************************************************************************
     * @param equation
     * @param
     * @return
     */
    public static ArrayList<String> trigDerivative(String equation) {

        ArrayList<String> parts = findCoefErasePlus(equation);
        ArrayList<String> trigParts = new ArrayList<>();

        for (int i = 0; i < parts.size(); i++) {

            String chX = insideTrigSolver(parts.get(i));

            if (chX.toLowerCase().contains("sin")) {
                String replace = chX.replace("sin", "cos");

                trigParts.add(replace);

            } else if (chX.toLowerCase().contains("cos")) {
                java.lang.String replace = chX.replace("cos", "(-sin)");

                trigParts.add(replace);

            } else if (chX.toLowerCase().contains("tan")) {
                java.lang.String replace = chX.replace("tan", "(sec^2)");

                trigParts.add(replace);
            } else if (chX.toLowerCase().contains("cot")) {
                java.lang.String replace = chX.replace("cot", "(-csc^2)");

                trigParts.add(replace);
            } else if (chX.toLowerCase().contains("sec")) {
                java.lang.String replace = chX.replace("sec", "(sec()tan())");
                trigParts.add(replace);

            }

        }
        return trigParts;
    }

    /**
     * *********************************************************************************************************************************************
     * Finds EXPONENT derivative -> changing the coefficient only equation
     * separated earlier by + and/or - is now been worked on separately. A
     * separated part will be scanned for any powers, then the index number is
     * found which will help us save the coefficient of the exponent as long as
     * it's a number. Later on the coefficient number will be used in the
     * derivation step, where the same coefficient number is multiplied by the
     * power of the exponent while the power stays intact.
     * ********************************************************************************************************************************************
     */
    public static ArrayList<String> exponentialDerivation(String equation) {

        ArrayList<String> parts = findCoefErasePlus(equation);
        ArrayList<String> derExp = new ArrayList<String>();

        StringBuilder coef = new StringBuilder();
        for (int j = 0; j < parts.size(); j++) { //

            int pow = findPow(parts.get(j));
            int indexOfE = parts.get(j).indexOf("e");

            for (int i = 0; i < indexOfE; i++) {
                if (parts.get(j).charAt(i) < 59 && parts.get(j).charAt(i) > 46) {
                    coef.append(i);

                    String strCoef = coef.toString();
                    int coeffOfE = Integer.parseInt(strCoef);
                    int newCoef = pow * coeffOfE;
                    String strnewCoef = Integer.toString(newCoef);

                    derExp.add(parts.get(j).replace(strCoef, strnewCoef));

                }
            }
        }
        return derExp;
    }

    /**
     * *****************************************************************************************************************************************
     * simplifies the fraction by separating it accordingly to the nom and denom
     * *******************************************************************************************************************************************
     */
    static String nominator;
    static int numOfLine = 0;

    public static ArrayList<String> fractionDecomposition(String eqPart) {
        StringBuilder num = new StringBuilder();
        int indexOfLine = 0;

        for (int i = 0; i < eqPart.length(); i++) {
            num.append(i);
            if (numOfLine < 2) {// in order to have nom and denom only
                if (eqPart.charAt(i) == '/') {
                    num.deleteCharAt(i);
                    nominator = num.toString();
                    numOfLine++;
                    indexOfLine = i;
                    num.delete(0, i - 1);
                }
            }

        }
        String denominator = num.toString();

        if (numOfLine >= 2) {
            //recursion and call/ return other functions to work on this part
            ArrayList<String> nomDenom = fractionDecomposition(nominator);
            ArrayList<String> denom = fractionDecomposition(denominator);

        } else {

            ArrayList<String> fraction = new ArrayList<String>();
            fraction.add(nominator);
            fraction.add(denominator);
            return fraction;

        }
        return null;
    }

    /**
     * *****************************************************************************************************************************************
     * derives fractions 1/(u^n) = 1/(u^(n+1)) f/g=f'*g-f*g'/g^2
     * *******************************************************************************************************************************************
     */
    /*    public static ArrayList<String> fractionDerivation(ArrayList<String> nomAndDenom) {

        String nom = nomAndDenom.get(0);
        String denom = nomAndDenom.get(1);

        // 1 derive nom
        java.lang.String nomDer = repetitiveQuestioning(nom);

        //2 derive denom
        java.lang.String denomDer = repetitiveQuestioning(denom);
        //3 multiply derived nom and denom
//        productDer(denom, nomDer);

        // 4 multiply derived denom and nom
        //      productDer(nom, denomDer);
        //5 substract the 3 and 4
        //divide the 5 by denom^2
        return null;// for now
    }
    static java.lang.String powDer;
    static java.lang.String trigDer;
    static java.lang.String expDer;

    //repetitive questioning for (f/g)' and (f*g)'
    public static String repetitiveQuestioning(String nomAndDenom) {
        //cehck if power
        if (nomAndDenom.contains("^")) {
            int findOuterCoeff = findOutterCoeffOfXorY(nomAndDenom);
            Integer.toString(findOuterCoeff);
            

            ArrayList<java.lang.String> powerDerivation = powerDerivation(Integer.toString(findOuterCoeff));
            powDer = powerDerivation.toString();

        }

        try {
            ArrayList<String> trigDerivative = trigDerivative(powDer); // "if" above was visited
            trigDer = trigDerivative.toString();

        } catch (Exception e) {
            ArrayList<String> trigDerivative = trigDerivative(nomAndDenom); //if the "if" statement above wasn't wisited
            trigDer = trigDerivative.toString();
        }

        if (nomAndDenom.contains("e")) {

            ArrayList<java.lang.String> exponentialDerivation = exponentialDerivation(trigDer);
            expDer = exponentialDerivation.toString();
            return expDer;

        } else {
            return trigDer;
        }

    }

    //(f*g)'=f'*g+f*g'
    // we derive g and f
    //how do we multiply the 2?
    // let's say we have 2x*x^3
    // this becomes: 2x^4
     */
    static int outsideOfAll;

    public static String insideTrigSolver(String part) {
        StringBuilder coef = new StringBuilder();
        StringBuilder newBuilder = new StringBuilder();
        char[] array = part.toCharArray();
        ArrayList<Character> copyArray = new ArrayList<Character>();
        for (char each : array) {
            copyArray.add(each);
        }

        if (part.contains("(") && part.contains(")")) {
            int i = part.indexOf("(");
            int j = part.indexOf(")");
            String inner = part.substring(i + 1, j);
            if (inner.contains("^")) {
                String coeff;
                int innerPow = findPow(inner);

                if ((array[0] > 97 && array[0] < 122)) { //if first letter and no number, add number
                    coef.append("1");

                    coeff = coef.toString();
                    int newCoef = Integer.parseInt(coeff);
                    outsideOfAll = innerPow * (newCoef);
                } else {
                    for (int index = 0; i < array.length; index++) {
                        if (array[index] > 48 && array[index] < 57) {

                            coef.append(array[index]);
                            copyArray.remove(0);

                        } else {
                            coeff = coef.toString();
                            int newCoef = Integer.parseInt(coeff);
                            outsideOfAll = innerPow * (newCoef);
                            break;
                        }

                    }
                }

                newBuilder.append(Integer.toString(outsideOfAll));
                newBuilder.append("*");
                for (int index = 0; index < copyArray.size(); index++) {//for each

                    newBuilder.append(copyArray.get(index));

                }
                return newBuilder.toString();

            }

        }
        return newBuilder.toString();

    }

}
