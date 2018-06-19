package com.mygdx.test;

import java.util.*;



public class GraphOptimalPath{

  //private List<Vertex> previousVertices;
  private List<Vertex> unvisitedVertices;
  private List<Edge> edges;

  public void updateDistances(Graph g, Vertex source){

    //--
    //initialization
    Vertex currentVertex = source;
    int currentVertexIndex = 0;

    int minDistance = Integer.MAX_VALUE;
    int alternativeDistance = Integer.MAX_VALUE;

    unvisitedVertices = g.getVertexes();
    edges = g.getEdges();

    for(Vertex vertex : unvisitedVertices)
    {
      vertex.setDistance(Integer.MAX_VALUE);
    }
    source.setDistance(0);
    //--

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

      for(Edge edge : edges)
      {
        for(Vertex vertex : g.getNeighbours(currentVertex.getId()))
        {
          if(edge.getSource() == currentVertex && edge.getDestination() == vertex)
          {
            alternativeDistance = currentVertex.getDistance() + edge.getWeight();
            if(alternativeDistance < vertex.getDistance())
            {
              vertex.setDistance(alternativeDistance);
              vertex.setPreviousVertex(currentVertex);
              alternativeDistance = Integer.MAX_VALUE;
            }
          }
        }
      }
      unvisitedVertices.remove(currentVertexIndex);
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
        result = s + result;
      }

      return result;
    }
}

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
