/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.test;

/**
 *
 * @author Sara
 */
public class Nodee implements Comparable<Nodee> {
     private int myX, myY;
    private int gScore, hScore;
    public boolean  passable;
    private Nodee parent;
    

    Nodee() {
        }

    public Nodee getParent() {
        return parent;
    }

    public void setParent(Nodee myParentNode) {
        this.parent = myParentNode;
        
    }
    
  
   
    
    /**
     * Default constructor
     * @param x X coordinate
     * @param y y coordinate
     * @param passable
     */
    public Nodee(int x, int y, boolean passable)
    {
        this.myX = x;
        this.myY = y;
        this.passable =passable;
        this.gScore = 0;
        this.hScore = 0;
    }//end constructor
    
    /**
     * Sets this nodes location
     * @param x
     * @param y 
     */
    public void setLocation(int x, int y) {
        this.myX = x; this.myY = y;
    }//end setLocation
    
    /**
     * Gets this nodes x coordinate
     * @return x coordinate
     */
    public int getX() {
        return this.myX;
    }//end getX
    
    /**
     * Gets this node's y coordinate
     * @return y coordinate
     */
    public int getY() {
        return this.myY;
    }//end getY
    
    
    public int getFScore() {
        return hScore + gScore;
    }//end getFScore
    
    /**
     * Sets this nodes heuristic score
     * @param hscore 
     */
    public void setHScore(int hscore)
    {
        this.hScore = hscore;
    }//end setHScore
    
    /**
     * Sets this node's gScore
     * @param gScore 
     */
    public void setGScore(int gScore)
    {
        this.gScore = gScore;
    }//end setGScore
    
    /**
     * Gets this node's G-score
     * @return 
     */
    public int getGScore()
    {
        return this.gScore;
    }//end getGScore
    
    
     public boolean isPassable()
    {
        return this.passable;
    }//end isPassable
    
     
    /**
     * Standard toString method
     * @return 
     */
    public String toString()
    {
        return "X: " + this.myX + " Y: " + this.myY + " F: " + getFScore() + " G: " + getGScore();
    }//end toString
    
    //give a node and gives back all the things connected to it

    public int getHScore() {
        return hScore;
    }
    
    @Override
    public int compareTo(Nodee n) {
    	if (n.hScore > hScore)
    		return -1;
    	if (n.hScore < hScore)
    		return 1;
    	return 0;
    }
  
    
    
}
