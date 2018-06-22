package com.mygdx.test;

import java.util.*;



public class GraphOptimalPath{
  public String result;
  public ArrayList<String> vertexPath;
  //private List<Vertex> previousVertices;
  private List<Vertex> unvisitedVertices;
  private List<Vertex> cloneVertices;
  private List<Edge> edges;

  public List<Vertex> updateDistances(Graph g, Vertex source){

    //--
    //initialization
    Vertex currentVertex = source;
    int currentVertexIndex = 0;

    int minDistance = Integer.MAX_VALUE;
    int alternativeDistance = Integer.MAX_VALUE;

    unvisitedVertices = g.getVertexes();
    cloneVertices = new ArrayList<Vertex>();
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
              cloneVertices.add(vertex);
              alternativeDistance = Integer.MAX_VALUE;
            }
          }
        }
      }
      //cloneVertices.add(unvisitedVertices.get(currentVertexIndex));
      unvisitedVertices.remove(currentVertexIndex);
    }
    return cloneVertices;
  }

    public String findOptimalPath(Graph g, Vertex source, Vertex destination){
      //save all instances of currentVertex.toString() in a string array
      //set currentVertex to destination
      //set currentVertex = currentVertex.getPrevious() while previous != null
      //append strings in the string array and return the resulting string

      Vertex currentVertex = destination;
       vertexPath = new ArrayList<>();

      while(currentVertex != null)
      {
        vertexPath.add(currentVertex.getId());
        currentVertex = currentVertex.getPreviousVertex();
      }

       result = "";

      for(String s : vertexPath){
        result = s + result;
      }

      return result;
    }
}


