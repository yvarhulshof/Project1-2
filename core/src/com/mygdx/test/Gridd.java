/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.test;

import com.mygdx.test.Nodee;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Sara
 */
public class Gridd extends Nodee {

    ArrayList<Nodee> allMyNodes = new ArrayList<Nodee>();
    private Nodee[][] myNodes;
    private Nodee startNode;
    private Nodee goalNode;

    private ArrayList<Nodee> getAllOfMyNodes() {

        for (int i = 0; i < myNodes.length; i++) {
            for (int j = 0; j < myNodes.length; j++) {
                Nodee tmp = myNodes[i][j];
                allMyNodes.add(tmp);
            }
        }
        return allMyNodes;

    }
    private PathFinderAStarr pathFinder;

    public Gridd(int width, int height, Nodee start, Nodee goal) {
        this.startNode = start;
        this.goalNode = goal;
        this.myNodes = new Nodee[width][height];
        fillInTheGridWithNodes();
        getAllOfMyNodes();

        pathFinder = new PathFinderAStarr(this, startNode, goalNode);
        ArrayList<Nodee> actual_path = pathFinder.AStar();
        printPath(actual_path);
        //printPath(pathFinder.jumpPointSearch());
        //printPath(pathFinder.aStarPathFind());
    }

    public void fillInTheGridWithNodes() {

        for (int i = 0; i < myNodes.length; i++) {
            for (int j = 0; j < myNodes.length; j++) {
                Nodee created = new Nodee(i, j, true);
                myNodes[i][j] = created;

            }
        }

    }

    /* Gets a node at the specified location in the grid
     * @param x
     * @param y
     * @return The node at the x,y coordinates
     */
    public Nodee getNode(int x, int y) {
        if (x >= this.myNodes.length || x < 0 || y >= this.myNodes[0].length || y < 0) {
            return null;
        }
        return this.myNodes[x][y];
    }

    /**
     * Checks if a node is passable at the specified location in the grid
     *
     * @param x
     * @param y
     * @return True if passable, false otherwise
     */
    public boolean isPassable(int x, int y) {
        if (x >= this.myNodes.length || x < 0 || y >= this.myNodes[0].length || y < 0) {
            return false;
        }
        return this.myNodes[x][y].isPassable();
    }

    /**
     * Retrieves the starting node for the path
     *
     * @return
     */
    public Nodee getStart() {
        return startNode;
    }

    /**
     * Retrieves the ending node for the path
     *
     * @return
     */
    public Nodee getEnd() {
        return goalNode;
    }
    //give a node and gives back all the things connected to it

    public ArrayList<Nodee> getNeighbors(Nodee currentNode) {

        ArrayList<Nodee> neighbours = new ArrayList<Nodee>();

       
        

      //  for (int t = 0; t < myNodes.length; t++) {// za koliko je brod dugacak a nama treba samo jedno
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (CheckValid(currentNode.getX() - 1 + i) && CheckValid(currentNode.getY() - 1 + j)) { // a je za x a y je b

                        if ((myNodes[currentNode.getX() - 1 + i][currentNode.getY() - 1 + j].isPassable())) {
                            neighbours.add(myNodes[currentNode.getX() - 1 + i][currentNode.getY() - 1 + j]);
                        }
                    }
                }
            }
            
        
        return neighbours;
    }

    Boolean CheckValid(int a) {
        if (a > -1 && a < myNodes[0].length) {
            return true;

        }
        return false;
    }


    

    /**
     * *****************************************************************************************************************************************************************
     * Prints the grid with the path highlighted with "X"s at each node that
     * includes the path
     *
     * @param thePath The path to print
     */
    public void printPath(ArrayList<Nodee> thePath) {
        String out = "";
        for (int r = 0; r < this.myNodes[0].length; r++) {
            Nodee[] row = this.myNodes[r];
            for (int c = 0; c < this.myNodes.length; c++) {
                Nodee node = getNode(c, r);
                if (node.equals(startNode)) {
                    out += "2 ";
                    continue;
                } else if (node.equals(goalNode)) {
                    out += "3 ";
                    continue;
                }
                if (node.isPassable()) {
                    if (!thePath.contains(node)) {
                        out += "0 ";
                    } else {
                        out += "x ";
                    }
                } else {
                    out += "1 ";
                }
            }
            out += "\n";
        }
        System.out.println(out);
    }//end printPath

    /**
     * To string method for this class. It just prints the grid
     *
     * @return
     */
    public String toString() {
        String out = "";
        for (int r = 0; r < this.myNodes.length; r++) {
            Nodee[] row = this.myNodes[r];
            for (Nodee node : row) {
                if (node.equals(startNode)) {
                    out += "2 ";
                    continue;
                } else if (node.equals(goalNode)) {
                    out += "3 ";
                    continue;
                }
                if (node.isPassable()) {
                    out += "0 ";
                } else {
                    out += "1 ";
                }
            }
            out += "\n";
        }
        return out;
    }//end toString

}
