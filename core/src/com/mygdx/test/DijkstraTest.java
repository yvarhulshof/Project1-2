package com.mygdx.test;

import com.sun.javafx.scene.control.skin.VirtualFlow;

import java.util.ArrayList;
import java.util.List;

public class DijkstraTest {

    private Graph g;
    private GraphSetUp gs;
    private GraphOptimalPath gop;
    private List<Vertex> vertices = new ArrayList<Vertex>();
    private List<Edge> edges = new ArrayList<Edge>();
    private List<Vertex> verticesClone;

    public void findOptimalPath(){
        gs = new GraphSetUp(3,3,640,480);

        vertices = gs.getVertices();
        edges = gs.getEdges();
        verticesClone = new ArrayList<Vertex>(vertices);

        g = new Graph(vertices,edges);

        gop = new GraphOptimalPath();

        verticesClone = gop.updateDistances(g, vertices.get(0));


        String result = gop.findOptimalPath(g,verticesClone.get(0), verticesClone.get(verticesClone.size()-1));
        System.out.println("The optimal path is: " + result);

        //TODO fix problem that might be caused by all edges having weight 1 (min. distance doesn't update in GraphOptimalPath, maybe because no choice better than another can be found because of the weights)
    }
}
