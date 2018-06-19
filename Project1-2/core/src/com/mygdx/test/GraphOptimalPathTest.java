package com.mygdx.test;

import java.util.*;


public class GraphOptimalPathTest{

  public static void main(String[] args) {

  List<Vertex> vertices = new ArrayList<Vertex>();
  List<Edge> edges = new ArrayList<Edge>();

  Vertex v1 = new Vertex("V01", "A");
  Vertex v2 = new Vertex("V02", "B");
  Vertex v3 = new Vertex("V03", "C");
  Vertex v4 = new Vertex("V04", "D");
  Vertex v5 = new Vertex("V05", "E");
  Vertex v6 = new Vertex("V06", "F");
  Vertex v7 = new Vertex("V07", "G");
  Vertex v8 = new Vertex("V08", "H");
  Vertex v9 = new Vertex("V09", "I");
  Vertex v10 = new Vertex("V10", "J");

  vertices.add(v1);
  vertices.add(v2);
  vertices.add(v3);
  vertices.add(v4);
  vertices.add(v5);
  vertices.add(v6);
  vertices.add(v7);
  vertices.add(v8);
  vertices.add(v9);
  vertices.add(v10);

  //weight for compromised channels is 1, otherwise weight is 0
  Edge e1A = new Edge("E01", v1 , v2, 1);
  Edge e1B = new Edge("E01", v2 , v1, 1);

  Edge e2A = new Edge("E02", v1 , v3, 1);
  Edge e2B = new Edge("E02", v3 , v1, 1);

  Edge e3A = new Edge("E03", v1 , v4, 0);
  Edge e3B = new Edge("E03", v4 , v1, 0);

  Edge e4A = new Edge("E04", v2 , v3, 1);
  Edge e4B = new Edge("E04", v3 , v2, 1);

  Edge e5A = new Edge("E05", v4 , v3, 0);
  Edge e5B = new Edge("E05", v3 , v4, 0);

  Edge e6A = new Edge("E06", v3 , v6, 1);
  Edge e6B = new Edge("E06", v6 , v3, 1);

  Edge e7A = new Edge("E07", v3 , v5, 1);
  Edge e7B = new Edge("E07", v5 , v3, 1);

  Edge e8A = new Edge("E08", v5 , v7, 1);
  Edge e8B = new Edge("E08", v7 , v5, 1);

  Edge e9A = new Edge("E09", v6 , v8, 0);
  Edge e9B = new Edge("E09", v8 , v6, 0);

  Edge e10A = new Edge("E10", v7 , v8, 0);
  Edge e10B = new Edge("E10", v8 , v7, 0);

  Edge e11A = new Edge("E11", v9 , v10, 0);
  Edge e11B = new Edge("E11", v10 , v9, 0);

  edges.addAll(Arrays.asList(e1A,e2A,e3A,e4A,e1B,e2B,e3B,e4B,e5A,e5B,e6A,e6B,e7A,e7B,e8A,e8B,e9A,e9B,e10A,e10B,e11A,e11B));

  Graph g = new Graph(vertices,edges);

  GraphOptimalPath gop = new GraphOptimalPath();

  gop.updateDistances(g, v1);
  String result = gop.findOptimalPath(g,v1,v7);
  System.out.println("The optimal path is: " + result);

  }
}
