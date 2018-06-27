package com.mygdx.test;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.lang.reflect.Array;
import java.util.*;


public class GraphSetUp {

    //private int mapXSize;
    //private int mapYSize;
    List<Vertex> vertices = new ArrayList<Vertex>();
    List<Edge> edges = new ArrayList<Edge>();
    private Iterator<Vertex> iterator;
    private ArrayList<Integer> toBeRemovedIndices;


    public GraphSetUp(int nrOfNodesX, int nrOfNodesY, int mapXSize, int mapYSize,  TiledMapTileLayer collisionLayer) {
        float xLocation = 0;
        float yLocation = 0;
        int xId;
        int yId;
        String name = "";
        Vertex a = null;
        Vertex dest = null;
        int spacingX = mapXSize / nrOfNodesX; //will round down to pixel, could cause problems
        int spacingY = mapYSize / nrOfNodesY; //will round down to pixel, could cause problems

        float tileWidth = collisionLayer.getTileWidth();
        float tileHeight = collisionLayer.getTileHeight();

        for (int i = 0; i < nrOfNodesX; i++) {
            xId = i;
            xLocation = 32 + i * spacingX;
            //xLocation = i;

            for (int j = 0; j < nrOfNodesY; j++) {
                yId = j;
                yLocation = 32 + j * spacingY;
                //yLocation = j;
                name = "(" + xId + "," + yId + ")";
                a = new Vertex(name, xLocation, yLocation);
                vertices.add(a);
                if (j > 0) {
                    if (vertices.size() != 0) {
                        dest = vertices.get(vertices.size() - 2);
                        Edge e1 = new Edge("", a, dest, 1);
                        edges.add(e1);
                        Edge e2 = new Edge("", dest, a, 1);
                        edges.add(e2);
                    }
                }

                if (i > 0) {
                    dest = vertices.get(vertices.size() - nrOfNodesX - 1);
                    Edge e1 = new Edge("", a, dest, 1);
                    edges.add(e1);
                    Edge e2 = new Edge("", dest, a, 1);
                    edges.add(e2);
                }
            }
        }

        int vertexIndex = 0;
        int toBeRemovedIndex = 0;

        Queue<Vertex> vertexQueue = new LinkedList<>(vertices);

        toBeRemovedIndices = new ArrayList<Integer>();

        //for(int i = 0; i < nrOfNodesX; i++) {
        for (int i = 0; i < mapXSize / tileWidth; i++) {
            //xLocation = 32 + i * spacingX;
            xLocation = 32 + i * spacingX;
            //for (int j = 0; j < nrOfNodesY; j++) {
            for (int j = 0; j < mapYSize / tileHeight; j++) {
                yLocation = 32 + j * spacingY;
                int cellLocX = (int) (xLocation / tileWidth);
                int cellLocY = (int) (yLocation / tileHeight);
                TiledMapTileLayer.Cell possibleNodeCell = collisionLayer.getCell((int) (xLocation / tileWidth), (int) ((yLocation) / tileHeight));
                boolean wallCell = possibleNodeCell.getTile().getProperties().containsKey("solid");
                if (wallCell) {
                    //iterator = vertices.iterator();
                    //while (iterator.hasNext()) {
                    for (Vertex v : vertexQueue) {
                        //Vertex v = vertexQueue.remove();
                        //vertices.add(v);
                        vertexIndex++;
                        //Vertex v = iterator.next();
                        if (v.getXLoc() == xLocation && v.getYLoc() == yLocation) {
                            toBeRemovedIndices.add(vertexIndex);
                        }
                    }
                }
                //vertexQueue.remove(toBeRemovedIndex);
            }
        }
        //iterator = vertices.iterator();


        for(int i = 0; i < toBeRemovedIndices.size(); i++){
            vertices.remove((int) toBeRemovedIndices.get(i));
            for(int j = 1; j < toBeRemovedIndices.size(); j++){
                toBeRemovedIndices.set(j,toBeRemovedIndices.get(j-1));
            }
            for (Edge e : edges) {
                if (e.getSource() == null || e.getDestination() == null) {
                    edges.remove(e);
                }
            }

        }



        /*
        for(int i = 0; i < nrOfNodesX; i++) {
            xLocation = 32 + i * spacingX;
            for (int j = 0; j < nrOfNodesY; j++) {
                yLocation = 32 + j * spacingY;
                TiledMapTileLayer.Cell possibleNodeCell = collisionLayer.getCell((int) (xLocation / tileWidth), (int) ((yLocation) / tileHeight));
                boolean wallCell = possibleNodeCell.getTile().getProperties().containsKey("solid");
                if (wallCell) {
                    iterator = vertices.iterator();
                    while (iterator.hasNext()) {
                        vertexIndex++;
                        Vertex v = iterator.next();
                        if (v.getXLoc() == xLocation && v.getYLoc() == yLocation) {
                            toBeRemovedIndex = vertexIndex;
                            for (Edge e : edges) {
                                if (e.getSource() == null || e.getDestination() == null) {
                                    edges.remove(e);
                                }
                            }
                        }
                        vertices.remove(toBeRemovedIndex);
                    }
                }
                iterator = vertices.iterator();
            }
        }
        */

        //for(Vertex v : vertices){
        //if (v.getXLoc() == xLocation && v.getYLoc() == yLocation) {
        //vertices.remove(v);

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