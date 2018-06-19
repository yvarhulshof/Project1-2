package com.mygdx.test;

import java.util.ArrayList;
import java.util.List;

public class GraphSetUp {

    private Graph grid;
    List<Vertex> vertices = new ArrayList<Vertex>();
    List<Edge> edges = new ArrayList<Edge>();

    public GraphSetUp(int nrOfNodesWidth, int nrOfNodesLength){
        String xLocation = "";
        String yLocation = "";
        String name = "";
        Vertex a = null;
        for(int i = 0; i < nrOfNodesWidth; i++){
            xLocation = Integer.toString(i);
            for(int j = 0; j < nrOfNodesLength; j++){
                yLocation = Integer.toString(j);
                name = xLocation + " " + yLocation;
                a = new Vertex(name,name);
            }
            vertices.add(a);


        }

    }
}
