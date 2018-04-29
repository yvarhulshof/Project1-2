package com.mygdx.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileInput {

    private ArrayList<Double> directionValues;
    private ArrayList<Double> speedValues;

    public int getNrOfLines() {
        FileHandle file = Gdx.files.local("MapInput.txt");

        boolean exists = Gdx.files.local("MapInput.txt").exists();
        System.out.println(exists);

        //String text = file.readString();
        //System.out.println(text);

        File convertedFile = file.file();

        String[] fileInfo;

        int nrOfLines = 0;

        try {
            Scanner lineScanner = new Scanner(convertedFile);
            while (lineScanner.hasNext()) {
                if (lineScanner.next() == "") break;
                nrOfLines++;
            }
        }
            catch(IOException exception)
            {
                System.out.println("file not found");
            }
            return nrOfLines;
    }


    public String[] readMapInfo() {

        FileHandle file = Gdx.files.local("MapInput.txt");

        boolean exists = Gdx.files.local("MapInput.txt").exists();
        System.out.println(exists);

        //String text = file.readString();
        //System.out.println(text);

        File convertedFile = file.file();

        String[] fileInfo;

        try {
            int nrOfLines = 0;
            Scanner lineCounter = new Scanner(convertedFile);
            while(lineCounter.hasNextLine()){
                if(lineCounter.nextLine() == "") break;
                    nrOfLines++;
            }
            fileInfo = new String[nrOfLines];

            Scanner lineReader = new Scanner(convertedFile);

            for(int i = 0; i < nrOfLines; i++){
                fileInfo[i] = lineReader.nextLine();
            }
            return fileInfo;
        }
        catch (IOException exception)
        {
            System.out.println("file not found");
        }

        //System.out.println(fileInfo[3]);

        return null;
    }

    public void readSwingInfo(){


        FileHandle file = Gdx.files.local("GolfswingInput.txt");
        File convertedFile = file.file();

        ArrayList<String> swingInput = new ArrayList<>();

        try {
            Scanner lineScanner = new Scanner(convertedFile);
            while(lineScanner.hasNext())
                swingInput.add(lineScanner.nextLine());
            }
        catch(IOException exception)
        {
            System.out.println("file not found");
        }

        parseSwingDirections(swingInput);
    }

    public void parseSwingDirections(ArrayList<String> swingInfo){

        directionValues = new ArrayList<>();
        speedValues = new ArrayList<>();

        for(int i = 0; i < swingInfo.size();i++){
            if(i % 3 == 0) directionValues.add(Double.parseDouble(swingInfo.get(i)));
            else if(i % 3 == 1) speedValues.add(Double.parseDouble(swingInfo.get(i)));
        }
    }

    public ArrayList<Double> getDirectionValues() {
        return directionValues;
    }

    public ArrayList<Double> getSpeedValues() {
        return speedValues;
    }
}
