package com.mygdx.test;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
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
    private static ArrayList<Float> wayX;
    private static ArrayList<Float> wayY;
    private TiledMapTileLayer collisionLayer;

    public DijkstraMain(TiledMapTileLayer collisionLayer){
        this.collisionLayer = collisionLayer;
    }

    public ArrayList<ArrayList<Float>> findOptimalPath(){
        gs = new GraphSetUp(10,10,575,415, collisionLayer);

        vertices = gs.getVertices();
        edges = gs.getEdges();
        verticesClone = new ArrayList<Vertex>(vertices);

        g = new Graph(vertices,edges);

        gop = new GraphOptimalPath();

        verticesClone = gop.updateDistances(g, vertices.get(0));

        Vertex destination = verticesClone.get(verticesClone.size()-1);

        String result = gop.findOptimalPath(g,verticesClone.get(0), destination);

        wayX = new ArrayList<Float>();
        wayY = new ArrayList<Float>();

        //for(int i = 0; destination != null; i++)

        //we want to remove a location if exactly one of the coordinates is the same as that coordinate
        //for the previous location

        while(destination != null)
        {
            wayX.add(destination.getXLoc());
            wayY.add(destination.getYLoc());
            destination = destination.getPreviousVertex();
        }

        Collections.reverse(wayX);
        Collections.reverse(wayY);

        /*
            for(int i = 0; i < wayX.size()-2; i++){
                System.out.println(wayX.get(i));
                System.out.println(wayX.get(i+1));
                if((wayX.get(i).equals(wayX.get(i+1)) && wayX.get(i).equals(wayX.get(i+2))) || (wayY.get(i).equals(wayY.get(i+1)) && wayY.get(i).equals(wayY.get(i+2)))){
                    wayX.remove(i+1);
                    wayY.remove(i+1);
                    i = 0;
                }
            }
        */

      /*int i = 0;
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
        } */
        for(int i = 0; i < wayX.size()-1; i++){
            if((wayX.get(i).equals(wayX.get(i+1)) || wayY.get(i).equals(wayY.get(i+1)))){
                wayX.remove(i+1);
                wayY.remove(i+1);
                i = 0;
            }
        }

        wayX.remove(0);
        wayY.remove(0);

        System.out.println("way x: " + wayX);
        System.out.println("way y: " + wayY);
        ArrayList<ArrayList<Float>> midpoints = new ArrayList<ArrayList<Float>>(4);
        midpoints.add(wayX);
        midpoints.add(wayY);
        return midpoints;
    }
}
