package com.mygdx.test;

import com.sun.javafx.scene.control.skin.VirtualFlow;

import java.util.ArrayList;
import java.util.List;

public class DijkstraTest {

    private Graph g;
    private GraphSetUp gs;
    private GraphOptimalPath gop;
    private List<Vertex> vertices = new ArrayList<Vertex>();
    private List<Vertex> verticesClone;

    public void findOptimalPath(){
        g = new Graph(null,null);
        gs = new GraphSetUp(g, 20,20,640,480);
        vertices = g.getVertexes();
        verticesClone = new ArrayList<Vertex>(vertices);

        System.out.println("size: " + vertices.size());
        gop = new GraphOptimalPath();

        gop.updateDistances(g, vertices.get(0));

        System.out.println("size: " + verticesClone.size());

        System.out.println(verticesClone.get(0).getId());

        String result = gop.findOptimalPath(g,verticesClone.get(0), verticesClone.get(verticesClone.size()));
        //System.out.println("The optimal path is: " + result);
    }
}
