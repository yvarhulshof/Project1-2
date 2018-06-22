package com.mygdx.test;

import java.util.ArrayList;
import java.util.List;

public class GraphSetUp {

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
        Vertex dest = null;
        int spacingX = mapXSize / nrOfNodesX; //will round down to pixel, could cause problems
        int spacingY = mapYSize / nrOfNodesY; //will round down to pixel, could cause problems


        for(int i = 0; i < nrOfNodesX; i++){
            xId = i;
            xLocation = i * spacingX;

            for(int j = 0; j < nrOfNodesY; j++){
                yId = j;
                yLocation = j * spacingY;
                name = "(" + xId + "," + yId + ")";
                a = new Vertex(name,xLocation,yLocation);

                vertices.add(a);
                System.out.println("added node");

                if(j > 0)
                {
                    if(vertices.size() != 0)
                    {
                        dest = vertices.get(vertices.size() - 2); //not sure if right
                        Edge e = new Edge("", a, dest,1);
                        edges.add(e);
                    }
                }

                if(i > 0)
                {
                    dest = vertices.get(vertices.size() - nrOfNodesX - 1); //not sure if right
                    Edge e = new Edge("", a, dest,1);
                    edges.add(e);
                }
            }
        }
        //g.setVertexes(vertices);
        //g.setEdges(edges);

    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }
}
