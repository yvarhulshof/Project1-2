package com.mygdx.test;


import com.sun.javafx.geom.Edge;
import sun.security.provider.certpath.Vertex;

import java.util.*;

public class OptimalPath{
/*
  private List<Vertex> previousVertices;
  private List<Vertex> unvisitedVertices;
  private List<Edge> edges;

  public void updateDistances(Graph g, Vertex source){

    //initialization
    Vertex currentVertex = source;
    int currentVertexIndex = 0;
    //Vertex nextVertex = null;
    //Vertex minVertex = null;
    int minDistance = Integer.MAX_VALUE;
    int alternativeDistance = Integer.MAX_VALUE;

    unvisitedVertices = g.getVertexes();
    edges = g.getEdges();

    for(Vertex vertex : unvisitedVertices)
    {
      vertex.setDistance(Integer.MAX_VALUE);
    }
    unvisitedVertices.get(0).setDistance(0);
    //

    while(unvisitedVertices.size() > 0)
    {
      for(int i = 0; i < unvisitedVertices.size(); i++)
      {
        if(unvisitedVertices.get(i).getDistance() < minDistance)
        {
          minDistance = unvisitedVertices.get(i).getDistance();
          currentVertex = unvisitedVertices.get(i);
          currentVertexIndex = i;
        }
      }
      minDistance = Integer.MAX_VALUE;
      unvisitedVertices.remove(currentVertexIndex);

      g.setVertexes(unvisitedVertices);

      for(Edge edge : edges)
      {
        System.out.println("check-1");
        System.out.println(currentVertex.getId());
        for(Vertex vertex : g.getNeighbours(currentVertex.getId()))
        //maybe currentVertex isn't connected to edges anymore?
        {
          System.out.println("check0");
          if(edge.getSource() == currentVertex && edge.getDestination() == vertex)
          {
            System.out.println("check1");
            alternativeDistance = currentVertex.getDistance() + edge.getWeight();
            if(alternativeDistance < vertex.getDistance())
            {
              System.out.println("check2");
              vertex.setDistance(alternativeDistance);
              vertex.setPreviousVertex(currentVertex);
            }
          }
        }
      }
    }
  }

    public String findOptimalPath(Graph g, Vertex source, Vertex destination){
      //save all instances of currentVertex.toString() in a string array
      //set currentVertex to destination
      //set currentVertex = currentVertex.getPrevious() while previous != null
      //append strings in the string array and return the resulting string

      Vertex currentVertex = destination;
      ArrayList<String> vertexPath = new ArrayList<>();

      while(currentVertex != null)
      {
        vertexPath.add(currentVertex.toString());
        currentVertex = currentVertex.getPreviousVertex();
      }

      String result = "";

      for(String s : vertexPath){
        result = result + s;
      }

      //String result = String.join(vertexPath, ", ");

      return result;
    } */
}
  /*
  public void updateDistances(Graph g, Vertex source){

    initialize();

    int minWeight;
    Vertex currentVertex = source;
    Vertex nextVertex = null;
    Vertex minVertex = null;


    //check for the vertex we're currently at what its edges are
    for(Edge edge : edges)
    {
      for(Vertex vertex : g.getNeighbours(currentVertex.getId()))
      {
      if(edge.getSource() == currentVertex && edge.getDestination() == vertex)
        currentEdges.add(edge);
      }
    }

    //set initial min weight of the current edges to first found weight
    minWeight = currentEdges.get(0).getWeight();

    //update the distances for neighbour nodes, and store the node with the lowest distance
    //find the min weight of the current edges
    for(Edge edge : currentEdges)
    {
      nextVertex = edge.getDestination();
      nextVertex.setDistance(edge.getWeight());
      if(edge.getWeight() < minWeight)
      {
        minWeight = edge.getWeight();
        minVertex = nextVertex;
      }
    }

    currentVertex = minVertex;

    /*
    for(Edge edge : edges)
    {
      if(edge.getWeight() < minWeight){
        minWeight = edge.getWeight();
        nextVertex = edge.getDestination();
      }
    }
    */



/*
Suppose that CONTROL, a secret U.S. government counterintelligence agency based in Washington, D.C.,
has build a communication network that links n stations spread across the world using m communication channels
between pairs of stations. Suppose further that the evil spy agency, KAOS, is able to eavesdrop on some number,
k, of these channels and that CONTROL knows the k channels that have been compromised. Now, CONTROL has a message
M, that it wants to send from its headquarters station, s, to one of its field stations, t.
The problem is that the message is super secret and should traverse a path that minimizes the number of
compromised edges that occur along this path.

Write an algorithm to solve CONTROL’s problem.

Hint: Think about how you can assign weights to edges. Which algorithm is most appropriate for this problem?
Given that you will use this algorithm, which graph implementation should you use?
*/
