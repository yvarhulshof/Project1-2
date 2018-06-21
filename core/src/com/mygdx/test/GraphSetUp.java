package com.mygdx.test;

import java.util.ArrayList;
import java.util.List;

public class GraphSetUp {

    private Graph grid;
    //private int mapXSize;
    //private int mapYSize;
    List<Vertex> vertices = new ArrayList<Vertex>();
    List<Edge> edges = new ArrayList<Edge>();


    public GraphSetUp(int nrOfNodesX, int nrOfNodesY, int mapXSize, int mapYSize){
        int xLocation;
        int yLocation;
        int xId;
        int yId;
        String name = "";
        Vertex a = null;
        int spacingX = mapXSize / nrOfNodesX; //will round down to pixel, could cause problems
        int spacingY = mapYSize / nrOfNodesY; //will round down to pixel, could cause problems


        for(int i = 0; i < nrOfNodesX; i++){
            xId = i;
            xLocation = i * spacingX;

            for(int j = 0; j < nrOfNodesY; j++){
                yId = j;
                yLocation = j * spacingY;
                name = xId + " " + yId;
                a = new Vertex(name,xLocation,yLocation);

                if(i > 0)
                {
                    Vertex dest = vertices.get(vertices.size() -1); //not sure if right
                    Edge e = new Edge("", a, dest,1);
                }

                if(j > 0)
                {
                    Vertex dest = vertices.get(vertices.size() - nrOfNodesX); //not sure if right
                    Edge e = new Edge("", a, dest,1);
                }

            }
            vertices.add(a);
        }
    }
}