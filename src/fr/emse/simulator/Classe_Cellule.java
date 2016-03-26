package fr.emse.simulator;
import java.util.ArrayList;

import fr.emse.simulator.astar.AStarPathFinder;
import fr.emse.simulator.astar.EuclideanDistanceHeuristic;
import fr.emse.simulator.astar.PreferEmptyCellsLocalCost;
import fr.emse.simulator.world.*;


public class Classe_Cellule implements Cell
{
	Classe_Occupant Cellule_vide = new Classe_Occupant();
	int Type_Cellule = '_';
	public int row = 0;
	public int col = 0;
	
	Classe_Robot Cellule_Robot = new Classe_Robot();
	Classe_Wall Cellule_Wall = new Classe_Wall();
	 Classe_Coin Cellule_Coin = new Classe_Coin();
	 Classe_Robber Cellule_Robber = new Classe_Robber();
	
	 
	public Classe_Cellule()
	{
		
	}
	@Override
	public int getCol() {
		// TODO Auto-generated method stub
		return this.col;
	}

	@Override
	public Occupant getOccupant() 
	{
		if(this.Type_Cellule == 'D')
			return Cellule_Robot;
		else if(this.Type_Cellule == 'I')
			return Cellule_Robber;
		else if(this.Type_Cellule == '$')
			return Cellule_Coin;
		else if(this.Type_Cellule == '#')
			return Cellule_Wall;
		else
			return Cellule_vide;
		}

	@Override
	public int getRow() {
		// TODO Auto-generated method stub
		return this.row;
	}

	@Override
	public boolean isEmpty() 
	{
		if(Type_Cellule == '_')
			return true;
		else
			return false;
	}
	public void Put_Cell_Natur(int i)
	{
		Type_Cellule = i;
	}
	
	public int Get_Cell_Natur()
	{
		return this.Type_Cellule;
	}
	

	
	
}