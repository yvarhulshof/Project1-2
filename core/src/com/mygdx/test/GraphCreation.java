package com.mygdx.test;

import java.util.*;


/*public class GraphCreation{



  List<Vertex> vertices = new ArrayList<Vertex>();
  List<Edge> edges = new ArrayList<Edge>();

  Vertex v1 = new Vertex("A",100,200);
  Vertex v2 = new Vertex("B",150,200);
  Vertex v3 = new Vertex("C", 200,200);
  Vertex v4 = new Vertex("D", 250,250);
  Vertex v5 = new Vertex("E", 300,250);
  Vertex v6 = new Vertex("F", 350,300);
  Vertex v7 = new Vertex("G", 400,400);
  Vertex v8 = new Vertex("H", 300,400);
  Vertex v9 = new Vertex("I", 250,350);
  Vertex v10 = new Vertex("J", 200,400);

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

  List<Vertex> vertices2 = new ArrayList<Vertex>();
  for(int i = 0; i < vertices.size();i++){
    vertices2.add(vertices.get(i));
  }

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
  String[] nodes = new String[result.length()];
  int[] wayX = new int[nodes.length];
  int[]wayY = new int[nodes.length];
  for(int i =1; i <= result.length(); i++) {
    nodes[i - 1] = result.substring(i - 1, i);
  }
  boolean found = false;
  int v =0; int i = 0;
  int count = 0;
  while(!found) {
    System.out.println("vertex  " + vertices2.get(i).getId() + "  nodes " + nodes[v]);
    System.out.println("v " + v + " i " + i);
    if (nodes[v].equals(vertices2.get(i).getId())) {
      wayX[v] = vertices2.get(i).getXLoc();
      wayY[v] = vertices2.get(i).getYLoc();
      v++;
      count++;
      System.out.println("v updt " + v);
    }
    else{
      i++;
      System.out.println("i updt " +i);

    }
    if(i == vertices2.size()){
      i=0;
    }
//    System.out.println("x " + wayX[i] + " y " + wayY[i]);



      if (count == nodes.length) {
        found = true;
      }
    }
    System.out.println(vertices2.size());
 for(int i =0; i < vertices2.size()-1; i++ ){
    System.out.println("vertex  " + vertices2.get(i).getId() + "  nodes " + nodes[v]);

    if(nodes[v].equals(vertices2.get(i).getId())){
      wayX[v] = vertices2.get(i).getXLoc();
      wayY[v] = vertices2.get(i).getYLoc();
      v++;
      i = 0;
    }
  }
  //for(int i = 0; i < nodes.length; i++){
   // System.out.print(nodes[i]);
  //}
 for(int j = 0; j < result.length(); j++){
  System.out.println( "node: " +nodes[j] + " x " + wayX[j] + " y " + wayY[j]);
  }


  } */

