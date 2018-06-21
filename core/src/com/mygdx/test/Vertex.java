package com.mygdx.test;

public class Vertex{
    final private String id;
    final private float xLoc;
    final private float yLoc;
    private int distance;
    private Vertex previous;


    public Vertex(String id, float xLoc, float yLoc) {
        this.id = id;
        this.xLoc = xLoc;
        this.yLoc = yLoc;

    }
    public String getId() {
        return id;
    }

    public float getXLoc(){ return xLoc;}

    public float getYLoc(){ return yLoc;}


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

