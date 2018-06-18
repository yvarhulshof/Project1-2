package com.mygdx.test;

public class Vertex {
    final private String id;
    final private String name;
    private int distance;
    private Vertex previous;


    public Vertex(String id, String name) {
        this.id = id;
        this.name = name;
    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vertex other = (Vertex) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

    public void setDistance(int distance){
      this.distance = distance;
    }

    public int getDistance(){
      return distance;
    }

    public void setPreviousVertex(Vertex previous){
      this.previous = previous;
    }

    public Vertex getPreviousVertex(){
      return previous;
    }

}
