package fr.emse.simulator;

import java.util.ArrayList;

import fr.emse.simulator.astar.AStarPathFinder;
import fr.emse.simulator.astar.EuclideanDistanceHeuristic;
import fr.emse.simulator.astar.PreferEmptyCellsLocalCost;
import fr.emse.simulator.world.*;

public class Classe_Robber implements Robber 
{
 public int nombre_sacs = 0;
 public int row;
 public int col;
 public int actif = 1;
public int joue_1 = 1;
public int joue_2 = 1;
	public Classe_Robber()
	{
		//super(this);
	}
	
}