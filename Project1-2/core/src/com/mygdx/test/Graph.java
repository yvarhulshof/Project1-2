package com.mygdx.test;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

class Neighbor{
    public int vertexNum;
    public Neighbor next;
    public Neighbor(int vnum, Neighbor nbr){
        this.vertexNum = vnum;
        next = nbr;
    }
}
class Vertex{
    String name;
    Neighbor adjList;
    Vertex(String name, Neighbor neighbors){
        this.name = name;
        this.adjList = neighbors;
    }
}
public class Graph {

    Vertex[] adjLists;

    public Graph() throws FileNotFoundException {
        FileHandle file = Gdx.files.local("GraphInfo.txt");
        File convertedFile = file.file();
        Scanner sc = new Scanner(convertedFile);
        adjLists = new Vertex[sc.nextInt()];

        // read vertices
        for (int i = 0; i < adjLists.length; i++) {
            adjLists[i] = new Vertex(sc.next(), null);
        }

        // read edges
        while (sc.hasNext()) {
            // read vertex names and translate to vertex numbers
            int v1 = indexForName(sc.next());
            int v2 = indexForName(sc.next());

            // add v2 in front of v1 list and v1 in front of v2's list
            adjLists[v1].adjList = new Neighbor(v2, adjLists[v1].adjList);
            adjLists[v2].adjList = new Neighbor(v1, adjLists[v2].adjList);
        }


    }


    int indexForName(String name) {
        for (int i = 0; i < adjLists.length; i++) {
            if (adjLists[i].name.equals((name))) {
                return i;
            }
        }
        return -1;
    }

    public void print() {
        System.out.println();
        for (int i = 0; i < adjLists.length; i++) {
            System.out.print(adjLists[i].name);
            for (Neighbor nbr = adjLists[i].adjList; nbr != null; nbr = nbr.next) {
                System.out.print("-->" + adjLists[nbr.vertexNum].name);
            }
            System.out.println("\n");
        }
    }

    public static void main(String[] args) throws IOException {
        Graph graph = new Graph();
        graph.print();
        }
    }
