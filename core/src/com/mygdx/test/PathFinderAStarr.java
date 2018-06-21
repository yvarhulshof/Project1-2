/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.test;


import java.util.ArrayList;
import java.util.Collections;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 *
 * @author Sara
 */
public class PathFinderAStarr {

	ArrayList<Nodee> actual_path = new ArrayList<Nodee>();

	private Gridd myGrid;
	private Nodee start, goal;
	double distance;
	int g;
	int h;
	Nodee current;
	// constructor

	public PathFinderAStarr(Gridd field, Nodee start, Nodee goal) {

		this.myGrid = field;
		this.start = start;
		this.goal = goal;

	}

	public ArrayList<Nodee> AStar() {
		// HashMap<Node,Node> parentMap = new HashMap<Node,Node>();
		// The set of nodes already evaluated
		ArrayList<Nodee> closedSet = new ArrayList<Nodee>();

		ArrayList<Nodee> openSet = new ArrayList<Nodee>();

		for (Nodee n : myGrid.allMyNodes) {
			n.setGScore(Integer.MAX_VALUE);
			n.setHScore(Integer.MAX_VALUE);
		}

		openSet.add(start);
		start.setGScore(0);
		start.setHScore(dist_between(start, goal));
		current = start;

		while (!openSet.isEmpty()) {
			current = Collections.min(openSet);

			openSet.remove(current);
			closedSet.add(current);

			if (current.getX() == goal.getX() && current.getY() == goal.getY()) {
				return reconstruct_path(current, goal);// path has been found
			}

			for (Nodee neighbor : myGrid.getNeighbors(current)) {
				System.out.println("Going through all neighbours");
				if (closedSet.contains(neighbor)/* || !neighbor.isPassable() */) {
					// and Ignore the neighbor which is already evaluated.
					System.out.println("neighbour found in closed set");
					continue;
				}

				if (!openSet.contains(neighbor)) {
					System.out.println("neighbour not found in open set, putting it in");
					openSet.add(neighbor);
				}
				int tentative_gScore = current.getGScore() + dist_between(current, neighbor);
				if (tentative_gScore >= neighbor.getGScore()) // Discover a new node
				{
					System.out.println(tentative_gScore + " : " + neighbor.getGScore());
					continue;
				}
				System.out.println("Set parent");
				neighbor.setParent(current);
				neighbor.setGScore(tentative_gScore);
				neighbor.setHScore(neighbor.getGScore() + dist_between(neighbor, goal));

				// distance = neighbor.getFScore();

			}
		}
		System.out.println("Test.PathFinderAStar.AStar() returns null as no path found");
		return null;
	}

	public ArrayList<Nodee> reconstruct_path(Nodee startNode, Nodee endNode) {
		ArrayList<Nodee> total_path = new ArrayList<Nodee>();
		ArrayList<Nodee> actual_path = new ArrayList<Nodee>();
		Nodee currentNode = startNode;
		Nodee currentNodeParent = startNode.getParent();
		while (currentNodeParent != null) {
			total_path.add(currentNode);
			currentNode = currentNodeParent;
			currentNodeParent = currentNodeParent.getParent();
		}
                actual_path.add(start);
		for (int i = total_path.size()-1; i >= 0; i--) {
			actual_path.add(total_path.get(i));
		}
                
		return actual_path;

	}

	public int dist_between(Nodee from, Nodee to) {

		int xCoordinate = to.getX() - from.getX();
		int yCoordinate = to.getY() - from.getY();
		// eucledian distance
		// from.setHScore((int) Math.sqrt(Math.pow(xCoordinate, 2) +
		// Math.pow(yCoordinate, 2)));
		// chebyshev distance
		int max = Math.max(Math.abs(xCoordinate), Math.abs(yCoordinate));

		from.setHScore(max);

		return from.getHScore();
	}
}
