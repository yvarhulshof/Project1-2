package com.mygdx.test;

import java.util.*;

public class Graph {
    private List<Vertex> vertexes;
    private List<Edge> edges;

    public Graph(List<Vertex> vertexes, List<Edge> edges) {
        this.vertexes = vertexes;
        this.edges = edges;
    }

    public void setVertexes(List<Vertex> vertexes){
      this.vertexes = vertexes;
    }

    public List<Vertex> getVertexes() {
        return vertexes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public boolean adjacent(String x, String y)	{
        // Returns true when there’s an edge from x to y

        Vertex vX = null;
        Vertex vY = null;

        for(int i = 0; i < vertexes.size(); i++)
        {
          if(x == vertexes.get(i).getId())
            vX = vertexes.get(i);

          if(y == vertexes.get(i).getId())
            vY = vertexes.get(i);
        }
        for(int i = 0; i < edges.size(); i++)
        {
          if(edges.get(i).getSource() == vX && edges.get(i).getDestination() == vY)
            return true;
          if(edges.get(i).getSource() == vY && edges.get(i).getDestination() == vX)
            return true;
        }
        return false;
    }

    public List<Vertex> getNeighbours(String vertex) {
        // Returns all neighbours of a given vertex

        Vertex vX = null;
        List<Edge> connectedEdges = new ArrayList<Edge>();
        List<Vertex> neighbours = new ArrayList<Vertex>();

        //find vertex belong to the string giving its ID
        for(int i = 0; i < vertexes.size(); i++)
        {
          //System.out.println("checkB");
          if(vertex.equals(vertexes.get(i).getId())){
            vX = vertexes.get(i);
          }
        }

        for(int i = 0; i < edges.size(); i++)
        {
          if(edges.get(i) != null && (edges.get(i).getSource() == vX))
            //connectedEdges.add(edges.get(i));
            neighbours.add(edges.get(i).getDestination());

          if(edges.get(i) != null && (edges.get(i).getDestination() == vX))
            neighbours.add(edges.get(i).getSource());
        }

        //we also need to find the edges for which vX is the destination
        /*
        for(int i = 0; i < connectedEdges.size(); i++)
        {
          neighbours.add(connectedEdges.get(i).getDestination());
        }
        */
        return neighbours;
    }
}
