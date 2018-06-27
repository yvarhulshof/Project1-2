package com.mygdx.test;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import java.util.*;


public class GraphSetUp {

    List<Vertex> vertices = new ArrayList<Vertex>();
    List<Edge> edges = new ArrayList<Edge>();
    private Iterator<Vertex> iterator;
    private ArrayList<Integer> toBeRemovedIndices;

/** set up the graph with nodes and vertices */
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
            for (int j = 0; j < nrOfNodesY; j++) {
                yId = j;
                yLocation = 32 + j * spacingY;
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
        /** get the indexes of the vertices where there is a wall between 2 nodes */
        Queue<Vertex> vertexQueue = new LinkedList<>(vertices);
        toBeRemovedIndices = new ArrayList<Integer>();
        for (int i = 0; i < mapXSize / tileWidth; i++) {
            xLocation = 32 + i * spacingX;
            for (int j = 0; j < mapYSize / tileHeight; j++) {
                yLocation = 32 + j * spacingY;
                int cellLocX = (int) (xLocation / tileWidth);
                int cellLocY = (int) (yLocation / tileHeight);
                TiledMapTileLayer.Cell possibleNodeCell = collisionLayer.getCell((int) (xLocation / tileWidth), (int) ((yLocation) / tileHeight));
                boolean wallCell = possibleNodeCell.getTile().getProperties().containsKey("solid");
                if (wallCell) {
                    for (Vertex v : vertexQueue) {
                        vertexIndex++;
                        if (v.getXLoc() == xLocation && v.getYLoc() == yLocation) {
                            toBeRemovedIndices.add(vertexIndex);
                        }
                    }
                }

            }
        }
        /** removes the vertices when there is a wall between 2 nodes */
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
    }
    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }
}