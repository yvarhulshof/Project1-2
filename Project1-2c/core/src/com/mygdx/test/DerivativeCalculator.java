package com.mygdx.test;
import java.util.ArrayList;
public class DerivativeCalculator {

    private static String equation = "3x^2+16sinx";
    public static ArrayList<String> result = new ArrayList<String>();

    public static void main(String[] args) {


        ArrayList<String> parts = findCoefErasePlus(equation);
        System.out.println("-------------parts ");
        print(parts);


        int findPow = findPow(equation);
        System.out.println("power: " + findPow);


        int findcoef = findOuterCoeff(equation);
        String findCoefStr = Integer.toString(findcoef);
        System.out.println("First coefficient: " + findCoefStr);



        ArrayList<Integer> allNumbers;
        allNumbers = findNumbers(equation);
        System.out.println("-------------allNumbers:");
        for (int i : allNumbers)
        {
            System.out.print(" " + i);
        }

        System.out.println("");

        ArrayList<String> allLetters = findLetters(equation);
        System.out.println("--------------allLetters ");

        print(allLetters);

        System.out.println("");

        ArrayList<String> derPow = powerDerivation( findCoefStr);
        System.out.println("-----------------powerDeriv");
        print(derPow);
        ArrayList<String> trig = trigDerivative(equation);

        System.out.printf("trig: %s%n", trig.toString());

        ArrayList<String> exp = exponentialDerivation(equation);
        System.out.println("-------------------exp");
        print(exp);

    }

    public static void print(ArrayList<String> printed) {
        for (String i : printed)
            System.out.print(" " + i);

        System.out.println("                         ");

    }

    /**
     * *************************************************************************************************************************************************
     *
     * should be the beginning of everything and erases plus/minus, so we
     * continue to the next plus/minus where the back letters will be stored and
     * worked on
     * **********************************************************************************************************************************************
     */
    public static ArrayList<String> findCoefErasePlus(String substring) {
        //ArrayList<Character> partsOfEquasion = new ArrayList<Character>();
        ArrayList<String> result = new ArrayList<String>();

        StringBuilder partsOfEquasion = new StringBuilder();

        String partOfEq = "";

        for (int i = 0; i < substring.length(); i++) {


            partOfEq = "";

            if (substring.charAt(i) != 45 && substring.charAt(i) != 43) {
                //filling in the first part
                partsOfEquasion.append(substring.charAt(i));
            } else {
                // we have the first "node" here then we work on it until it's derrived, then we erase everything and work on other, after+
                //String builder
                partOfEq = partsOfEquasion.toString();

                result.add(partOfEq);
                partsOfEquasion.setLength(0);
            }

        }

        partOfEq = partsOfEquasion.toString();

        result.add(partOfEq);

        return result;
    }

    /**
     * *************************************************************************************************************************
     *
     * check if there is a POWER(in the beginning)
     *
     *****************************************************************************************************************************
     * @param ourSubstring part of equation or equation we put in
     * @return the power found by find numbers with cutting the string where power is anticipated to be
     */
    public static int findPow(String ourSubstring) {
        for (int i = 0; i <ourSubstring.length(); i++) {
            if (ourSubstring.charAt(i) == 94) {
                ArrayList<Integer> numbers = findNumbers(ourSubstring.substring( i+1,ourSubstring.length()));
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
     * @return the first number in the  given srting
     */
    public static int findOuterCoeff(String substring) {
        for (int i = 0; i < substring.indexOf("x") || i < substring.indexOf("y"); i++) {
            if (substring.charAt(i) != 43 && substring.charAt(i) != 45) {
                ArrayList<Integer> numbersNew = findNumbers(substring.substring(0));

                return numbersNew.get(0);

            }
        }
        return 1; // working
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
                    if (number.length() > 0) {
                        String strnum = number.toString();
                        numbers.add(Integer.parseInt(strnum));
                        search = false;
                        number.setLength(0);

                    }

                }
            }

        }
        String strNumbers;
        strNumbers = number.toString();
        numbers.add(Integer.parseInt(strNumbers));

        return numbers;
    }

    /**
     * *************************************************************************************************************************************************
     * gives all existing letters in the given eq
     *
     **************************************************************************************************************************************************
     * @param substring
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
                    if (letter.length() > 0) {

                        letter = new StringBuilder();
                        search = false;
                    }
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
    public static ArrayList<String> powerDerivation( String firstCoeff) {
        ArrayList<String> parts = findCoefErasePlus(equation);

        ArrayList<String> powerDerived = new ArrayList<String>();

        for (String part : parts) {
            if (part.contains("^") || part.contains("x") || part.contains("y")) {
                int findPow = findPow(part);
                int inndexOfX = part.indexOf("x");
                int inndexOfY = part.indexOf("y");
                if (part.charAt(inndexOfY + 1) == '^' || part.charAt(inndexOfX + 1) == '^') {

                    String powS = Integer.toString(findPow);

                    //for (int j = 0; j < part.length(); j++) {
                    // if ((part.charAt(j) == 'x' || part.charAt(j) == 'y') && part.charAt(j + 1) == '^') {
                    int coef = Integer.parseInt(firstCoeff);
                    //int derivedCoef = coef * pow;
                    String strNewCoef = Integer.toString(coef * findPow);
                    String strCoef = Integer.toString(coef);

                    String newPow = Integer.toString(findPow - 1);

                    String part1 = part.replace(powS, newPow);
                    String part2 = part1.replace(strCoef, strNewCoef);

                    powerDerived.add(part2); // derived power

                    if (!part.isEmpty()) {
                        part = "";
                    }
                }

            }
        }
        return powerDerived;
    }

    /**
     * ***********************************************************************************************************************************
     *
     * //Table law: trigonometry
     * *******************************************************************************************************************************
     */
    public static ArrayList<String> trigDerivative(String equation) {

        ArrayList<String> parts = findCoefErasePlus(equation);

        String sin = "sin";
        String cos = "cos";
        String tan = "tan";
        String cot = "cot";
        String sec = "sec";
        String csc = "csc";

        for (int i = 0; i < parts.size(); i++) {

            String chX = parts.get(i);

            if (chX.toLowerCase().contains("sin")) {
                java.lang.String replace = chX.replace("sin", "cos");
                parts.add(i, replace);

            } else if (chX.toLowerCase().contains(cos.toLowerCase())) {
                java.lang.String replace = chX.replace("cos", "-sin");
                parts.add(i, replace);

            } else if (chX.toLowerCase().contains(tan.toLowerCase())) {
                java.lang.String replace = chX.replace("tan", "sec^2");
                parts.add(i, replace);
            } else if (chX.toLowerCase().contains(cot.toLowerCase())) {
                java.lang.String replace = chX.replace("cot", "-csc^2");
                parts.add(i, replace);
            } else if (chX.toLowerCase().contains(sec.toLowerCase())) {
                java.lang.String replace = chX.replace("sec", "sec()tan()");
                parts.add(i, replace);

            }

        }
        return parts;
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
    public static ArrayList<String> exponentialDerivation(String equasion) {

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
     * derives fractions 1/(u^n) = 1/(u^(n+1))
     * f/g=f'*g-f*g'/g^2
     * *******************************************************************************************************************************************
     */
    public static ArrayList<String> fractionDerivation(ArrayList<String> nomAndDenom) {

        String nom = nomAndDenom.get(0);
        String denom = nomAndDenom.get(1);

        // 1 derive nom
        java.lang.String nomDer = repetitiveQuestioning(nom);

        //2 derive denom
        java.lang.String denomDer= repetitiveQuestioning(denom);
        //3 multiply derived nom and denom
        productDer(denom,nomDer);

        // 4 multiply derived denom and nom
        productDer(nom,denomDer);
        //5 substract the 3 and 4
        //divide the 5 by denom^2
        return null;// for now
    }
    static java.lang.String powDer;
    static java.lang.String trigDer;
    static java.lang.String expDer;

    //repetitive questioning for (f/g)' and (f*g)'
    public static String repetitiveQuestioning(String nomAndDenom){
        //cehck if power
        if(nomAndDenom.contains("^")){
            int findOuterCoeff = findOuterCoeff(nomAndDenom);

            ArrayList<java.lang.String> powerDerivation = powerDerivation(Integer.toString(findOuterCoeff));
            powDer   = powerDerivation.toString();



        }

        try{ ArrayList<String> trigDerivative = trigDerivative(powDer); // "if" above was visited
            trigDer  = trigDerivative.toString();


        }catch(Exception e){ ArrayList<String> trigDerivative = trigDerivative(nomAndDenom); //if the "if" statement above wasn't wisited
            trigDer  = trigDerivative.toString();}

        if(nomAndDenom.contains("e")){

            ArrayList<java.lang.String> exponentialDerivation = exponentialDerivation(trigDer);
            expDer  = exponentialDerivation.toString();
            return expDer;

        }else{
            return trigDer;}



    }


    //(f*g)'=f'*g+f*g'
    // we derive g and f
    //how do we multiply the 2?
    // let's say we have 2x*x^3
    // this becomes: 2x^4
    public static void productDer(String nom,String denom){
        if(nom.contains("*") && denom.contains("*")){



        }



    }


}