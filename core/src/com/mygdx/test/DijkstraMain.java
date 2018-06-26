package com.mygdx.test;

import com.sun.javafx.scene.control.skin.VirtualFlow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DijkstraMain {

    private Graph g;
    private GraphSetUp gs;
    private GraphOptimalPath gop;
    private List<Vertex> vertices = new ArrayList<Vertex>();
    private List<Edge> edges = new ArrayList<Edge>();
    private List<Vertex> verticesClone;
    private String[] nodes;
    private static ArrayList<Float> wayX;
    private static ArrayList<Float> wayY;

    public ArrayList<ArrayList<Float>> findOptimalPath(){
        gs = new GraphSetUp(10,10,575,415);

        vertices = gs.getVertices();
        edges = gs.getEdges();
        verticesClone = new ArrayList<Vertex>(vertices);

        g = new Graph(vertices,edges);

        gop = new GraphOptimalPath();

        verticesClone = gop.updateDistances(g, vertices.get(0));

        Vertex destination = verticesClone.get(verticesClone.size()-1);

        String result = gop.findOptimalPath(g,verticesClone.get(0), destination);


        //TODO allow the AI to use found nodes as midpoints/goals for its shots

        /*
        nodes = new String[result.length()];
        wayX = new float[nodes.length];
        wayY = new float[nodes.length];
        for(int i =1; i <= result.length(); i++) {
            nodes[i - 1] = result.substring(i - 1, i);
        }
        */


        wayX = new ArrayList<Float>();
        wayY = new ArrayList<Float>();

        //for(int i = 0; destination != null; i++)
            while(destination != null)
            {
            //vertexPath.add(destination.getId());
            wayX.add(destination.getXLoc());
            wayY.add(destination.getYLoc());
            destination = destination.getPreviousVertex();
        }

        Collections.reverse(wayX);
        Collections.reverse(wayY);
        int i = 0;
        while(i < wayX.size()-2){
            //for(int i = 0; i < wayX.size()-2; i++){
            if((wayX.get(i).equals(wayX.get(i+1))) && wayX.get(i).equals(wayX.get(i+2))){
                wayX.remove(i+1);
                wayY.remove(i+1);
                i = 0;
            }
            else if((wayY.get(i).equals(wayY.get(i+1))) && wayY.get(i).equals(wayY.get(i+2))){
                wayX.remove(i+1);
                wayY.remove(i+1);
                i = 0;
            }
            else i++;
        }
        wayX.remove(0);
        wayY.remove(0);

        System.out.println("The optimal x path is: " + wayX);
        System.out.println("The optimal y path is: " + wayY);
        System.out.println("The optimal  path is: " + result);
        /*
        for(int i = 0; i < verticesClone.size(); i++){
            wayX[i] = verticesClone.get(i).getXLoc();
            wayY[i] = verticesClone.get(i).getYLoc();
        }
        */



        /*
        boolean found = false;
        int v =0;
        int i = 0;
        int count = 0;
        while(!found) {
            if (nodes[v].equals(verticesClone.get(i).getId())) {
                wayX[v] = verticesClone.get(i).getXLoc();
                wayY[v] = verticesClone.get(i).getYLoc();
                v++;
                count++;
            }
            else{
                i++;
            }
            if(i == verticesClone.size()) {
                i = 0;
            }
            if (count == nodes.length) {
                found = true;
            }
        }
        */

        ArrayList<ArrayList<Float>> midpoints = new ArrayList<ArrayList<Float>>(4);
        midpoints.add(wayX);
        midpoints.add(wayY);
        return midpoints;
    }
}
