package com.mygdx.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileInput {

    public String[] readMapInfo() {

        FileHandle file = Gdx.files.local("MapInput.txt");

        boolean exists = Gdx.files.local("MapInput.txt").exists();
        System.out.println(exists);

        //String text = file.readString();
        //System.out.println(text);

        File convertedFile = file.file();

        String[] fileInfo = new String[4];

        try {
            Scanner lineScanner = new Scanner(convertedFile);
            for(int i = 0;lineScanner.hasNext(); i++){
                fileInfo[i] = lineScanner.nextLine();
            }
        }
        catch (IOException exception)
        {
            System.out.println("file not found");
        }

        System.out.println(fileInfo[3]);

        return fileInfo;

    }

    public ArrayList<String> readSwingInfo(){


        FileHandle file = Gdx.files.local("MapInput.txt");
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

        return swingInput;
    }

    public ArrayList<Double> parseSwingDirections(ArrayList<String> swingInfo){

        //while()
        return null;
    }
}
