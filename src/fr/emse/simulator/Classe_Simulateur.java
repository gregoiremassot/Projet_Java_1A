package fr.emse.simulator;
import java.io.*;
import java.util.ArrayList;

import fr.emse.simulator.astar.AStarPathFinder;
import fr.emse.simulator.astar.EuclideanDistanceHeuristic;
import fr.emse.simulator.astar.PreferEmptyCellsLocalCost;
import fr.emse.simulator.gui.MapFrame;
import fr.emse.simulator.world.Cell;
import fr.emse.simulator.world.Occupant;

	public class Classe_Simulateur extends Simulator
	{
		int p;
	protected int nb_sacs;
	boolean continuer;
	int b=0;
	int nb_robber = 0;
	int time = 2000;
	int row_robot_after;
	int col_robot_after;
	int a;
	File fichier ;
	File fichier2 ;
	FileInputStream fis;
	FileInputStream fis4;
	int k;
	Classe_Cellule test = new Classe_Cellule();
	Classe_Cellule test3 = new Classe_Cellule();
	int i;
	public Classe_Coin[][] Coin ;
	public Classe_Robber[][] Robber ;
	public Classe_Robot[][] Robot ;
	
	public ArrayList<Classe_Coin> liste_Coin;
	public ArrayList<Classe_Robot> liste_Robot;
	public ArrayList<Classe_Robber> liste_Robber;
	int row_robot;
	int col_robot;
	int row_robber;
	int col_robber;
	int Nb_Cols;
	int Nb_Rows; 
	ArrayList<Cell> Liste2 = new ArrayList<Cell>();
	ArrayList<Cell> Liste2bis = new ArrayList<Cell>();
	ArrayList<Cell> Liste3 = new ArrayList<Cell>();
	ArrayList<Cell> Liste3bis = new ArrayList<Cell>();
	ArrayList<Cell> Listebis = new ArrayList<Cell>();
	ArrayList<Cell> Liste = new ArrayList<Cell>();
	Classe_Cellule Cellule_test2;
	ArrayList<Class<? extends Occupant>> toIgnore2;
	ArrayList<Class<? extends Occupant>> toIgnore ;
	AStarPathFinder solver;
	int taillebis = 0;
	 //int k=0;
	 int taille;
	 Classe_SimulationMap world ;
		int l = 0;
	public Classe_Simulateur(File f1) 
	{
		super(f1);
	}

	
	public boolean isOver() 
	{
		if(this.nb_robber != 0)
		{
			return  true;
		}
		else 
		{
			continuer = false;
		return false;
		}
	}


	public void loadMap(File arg0) {
		
		
		
		
		creer_Coin();
		creer_Robber();
		creer_Robot();
		liste_Coin = new ArrayList<Classe_Coin>();
		liste_Robot = new ArrayList<Classe_Robot>();
		liste_Robber = new ArrayList<Classe_Robber>();
		try
		{
			fichier = new File("world.txt");
			fis = new FileInputStream(fichier);
			world = new Classe_SimulationMap(fichier) ;
			this.Nb_Cols = world.getNbCols();
			this.Nb_Rows = world.getNbRows();
			fis.read();
			fis.read();
			fis.read();
			fis.read();
			fis.read();
			fis.read();
			
			
			
		for (int i=0; i< this.Nb_Rows; i++)
		{
			for (int j=0; j< this.Nb_Cols; j++)
			{
				try
				{
					
					//System.out.println("coucou");
					
					this.world.Cellules[i][j].Put_Cell_Natur(fis.read());
					
					
					this.world.Cellules[i][j].row = i;
					this.world.Cellules[i][j].col = j;
					
					
					if(world.Cellules[i][j].Get_Cell_Natur() == '$')
					{
						
						liste_Coin.add(Coin[i][j]);
						liste_Coin.get(liste_Coin.size()-1).row = world.Cellules[i][j].row;
						liste_Coin.get(liste_Coin.size()-1).col = world.Cellules[i][j].col;
						/*liste_Coin.get(liste_Coin.size()-1).actif = 1;*/
					}
					if(world.Cellules[i][j].Get_Cell_Natur() == 'D')
					{
						
						liste_Robot.add(Robot[i][j]);
						liste_Robot.get(liste_Robot.size()-1).row = world.Cellules[i][j].row;
						liste_Robot.get(liste_Robot.size()-1).col = world.Cellules[i][j].col;
						
						
					
					}
					if(world.Cellules[i][j].Get_Cell_Natur() == 'I')
					{
					
						liste_Robber.add(Robber[i][j]);
						nb_robber++;
						liste_Robber.get(liste_Robber.size()-1).row = world.Cellules[i][j].row;
						liste_Robber.get(liste_Robber.size()-1).col = world.Cellules[i][j].col;
						/*liste_Robber.get(liste_Robber.size()-1).actif = 1;*/
					}
					
					
				}
				catch(IndexOutOfBoundsException e)
				{
					
				}
				catch(NullPointerException e)
				{
					System.out.println("NullPointerExceptione");
				}
				
				
			}
			fis.read();
			
		}
		}
		catch(FileNotFoundException e)
		{
		  System.out.println("FileNotFoundException");
		}
		catch(IOException e)
		{
			System.out.println("IOException");
		}
		
		toIgnore2 = new ArrayList <Class<? extends Occupant>>();
		toIgnore2.add(Classe_Robber.class);
		 toIgnore = new ArrayList <Class<? extends Occupant>>();
		toIgnore.add(Classe_Robot.class);
		solver = new AStarPathFinder(world, new EuclideanDistanceHeuristic(), new PreferEmptyCellsLocalCost(1,3), toIgnore2);
		 Liste = new ArrayList<Cell>();
		 Listebis = new ArrayList<Cell>();
		//Cellule_test = new Classe_Cellule();
		
		
		Cellule_test2 = new Classe_Cellule();
	}

	@Override
	public void runOneStep() 
	{
		
	
			
		if(liste_Robot.size() != 0 && nb_robber != 0)
		{
			for(i=0;i < liste_Robot.size(); i++)
			{
				
				if(liste_Robber.size() != 0 )
				{
					
					row_robot = (AStar_Robot(liste_Robot.get(i).row,liste_Robot.get(i).col,this.world)).getRow();
					col_robot = (AStar_Robot(liste_Robot.get(i).row,liste_Robot.get(i).col,this.world)).getCol();
					if(world.Cellules[row_robot][col_robot].Get_Cell_Natur() == '_')
					{
					world.Cellules[liste_Robot.get(i).row][liste_Robot.get(i).col].Put_Cell_Natur('_');
					liste_Robot.get(i).row = row_robot;
					liste_Robot.get(i).col = col_robot;
					world.Cellules[liste_Robot.get(i).row][liste_Robot.get(i).col].Put_Cell_Natur('D');
					row_robot = (AStar_Robot(liste_Robot.get(i).row,liste_Robot.get(i).col,this.world)).getRow();
					col_robot = (AStar_Robot(liste_Robot.get(i).row,liste_Robot.get(i).col,this.world)).getCol();
					}
					if(world.Cellules[row_robot][col_robot].Get_Cell_Natur() == 'I' && liste_Robber.get(numero_robber(row_robot,col_robot)).actif == 1)
					{
						
					
					//liste_Robber.remove(numero_robber(row_robot,col_robot));
					liste_Robber.get(numero_robber(row_robot,col_robot)).actif = 0;
					world.Cellules[row_robot][col_robot].Put_Cell_Natur('_');
					nb_robber--;
					//System.out.println(liste_Robber.size());
					}
				}
			}
		}
		//this.world.frame.repaint(time);
		if(nb_robber != 0)
		{
		for(i=0;i < liste_Robber.size(); i++)
		{ p = i;
		 this.nb_sacs = liste_Robber.get(p).nombre_sacs;
		
			if(liste_Coin.size() != 0 &&  nb_sacs < 2)
			{
				if(liste_Robber.get(p).actif == 1)
				{
				//System.out.println("Après le if i = " +i+" et Nombre Sacs = "+this.nb_sacs);
				if(this.nb_sacs == 0)
				{
					
					this.jouer_Robber();
				}
				else if(this.nb_sacs == 1)
				{
					
					 if(liste_Robber.get(p).joue_1 == 1)
						{
						 
							this.jouer_Robber();
							liste_Robber.get(p).joue_1 = 1;
							liste_Robber.get(p).joue_2 = 0;
						}
					 else if(liste_Robber.get(p).joue_1 == 0)
						 if(liste_Robber.get(p).joue_2 == 1)
							{
							 
								this.jouer_Robber();
								liste_Robber.get(p).joue_1 = 1;
								liste_Robber.get(p).joue_2 = 1;
							}
						 else if(liste_Robber.get(p).joue_2 == 0)
						{
						 
							this.jouer_Robber();
							liste_Robber.get(p).joue_1 = 1;
							liste_Robber.get(p).joue_2 = 1;
						}
				}
				}
			}
			else if((liste_Coin.size() == 0 || liste_Robber.get(p).nombre_sacs == 2))
			{
				if(liste_Robber.get(p).actif == 1)
				{
				//System.out.println("Après le if i = " +i+" et Nombre Sacs = "+this.nb_sacs);
				/*row_robber = (AStar_Sortie(liste_Robber.get(i).row,liste_Robber.get(i).col,this.world)).getRow();
				col_robber = (AStar_Sortie(liste_Robber.get(i).row,liste_Robber.get(i).col,this.world)).getCol();
				if(world.Cellules[row_robber][col_robber].Get_Cell_Natur() == '_')
				{
				world.Cellules[liste_Robber.get(i).row][liste_Robber.get(i).col].Put_Cell_Natur('_');
				liste_Robber.get(i).row = row_robber;
				liste_Robber.get(i).col = col_robber;
				world.Cellules[liste_Robber.get(i).row][liste_Robber.get(i).col].Put_Cell_Natur('I');
				
				}
				if(liste_Robber.get(i).row == 0 || liste_Robber.get(i).col == 0 || liste_Robber.get(i).row == Nb_Rows-1 || liste_Robber.get(i).col == Nb_Cols - 1)
				{
					liste_Robber.get(numero_robber(row_robber,col_robber)).actif = 0;
					world.Cellules[row_robber][col_robber].Put_Cell_Natur('_');
					nb_robber--;
				}*/
				//this.jouer_Robber2();
					if(liste_Robber.get(p).nombre_sacs == 2)
					{
				if(liste_Robber.get(p).joue_1 == 1)
				{
				 
					this.jouer_Robber2();
					liste_Robber.get(p).joue_1 = 0;
					liste_Robber.get(p).joue_2 = 0;
				}
			 else if(liste_Robber.get(p).joue_1 == 0)
				{
					
				 if(liste_Robber.get(p).joue_2 == 1)
					{
					 
						
						liste_Robber.get(p).joue_1 = 1;
						liste_Robber.get(p).joue_2 = 0;
					}
				 else if(liste_Robber.get(p).joue_2 == 0)
					{
						
						liste_Robber.get(p).joue_2 = 1;
					}
				}
					}
					if(this.nb_sacs == 0)
					{
						
						this.jouer_Robber2();
					}
					else if(this.nb_sacs == 1)
					{
						
						 if(liste_Robber.get(p).joue_1 == 1)
							{
							 
								this.jouer_Robber2();
								liste_Robber.get(p).joue_1 = 0;
							}
						 else if(liste_Robber.get(p).joue_1 == 0)
							{
								
								liste_Robber.get(p).joue_1 = 1;
							}
					}
				}
				}
			}
		}
		}
		
	
	
	
public void creer_Coin()
{
	Coin = new Classe_Coin[this.Nb_Rows][this.Nb_Cols];
	for (int i=0; i< this.Nb_Rows; i++)
	{
		for (int j=0; j< this.Nb_Cols; j++)
		{
			Coin[i][j] = new Classe_Coin();
			
		}
	}
}

public void creer_Robber()
{
	Robber = new Classe_Robber[this.Nb_Rows][this.Nb_Cols];
	for (int i=0; i< this.Nb_Rows; i++)
	{
		for (int j=0; j< this.Nb_Cols; j++)
		{
			Robber[i][j] = new Classe_Robber();
			
		}
	}
}

public void creer_Robot()
{
	Robot = new Classe_Robot[this.Nb_Rows][this.Nb_Cols];
	for (int i=0; i< this.Nb_Rows; i++)
	{
		for (int j=0; j< this.Nb_Cols; j++)
		{
			Robot[i][j] = new Classe_Robot();
			
		}
	}
}


/*public void AStar_Robot(int row, int col, Classe_SimulationMap world)
{	
	
	
	 taille = 0;
	k = 0;
	Liste = null;
	
	for(i=0;i < liste_Robber.size(); i++)
	{
			
		Listebis = null;
				
				this.Listebis = solver.getShortestPath(world.Cellules[row][col], world.Cellules[liste_Robber.get(i).row][liste_Robber.get(i).col]);
				/*System.out.println(liste_Robber.get(i).row);
				System.out.println(liste_Robber.get(i).col);
				System.out.println("Coucou");
				taillebis = Listebis.size();
				if (k == 0)
				{
					taille = Listebis.size();
					this.Liste = solver.getShortestPath(world.Cellules[row][col], world.Cellules[liste_Robber.get(i).row][liste_Robber.get(i).col]);
					k++;
				}
				if(taillebis <= taille && k != 0)
				{
					
					this.Liste = solver.getShortestPath(world.Cellules[row][col], world.Cellules[liste_Robber.get(i).row][liste_Robber.get(i).col]);
				
				}
				
					
		
	}
	
	
}*/


public Classe_Cellule AStar_Robot(int row, int col, Classe_SimulationMap world)
{	
	
		
		
		//l++;
		k = 0;
	
	 taille = 0;
	
	
	for (int i=0; i< this.Nb_Rows; i++)
	{
		for (int j=0; j< this.Nb_Cols; j++)
		{
			
			if(world.Cellules[i][j].Get_Cell_Natur() == 'I' )
			{
			
				this.Listebis = solver.getShortestPath(world.Cellules[row][col], world.Cellules[i][j]);
				taillebis = Listebis.size();
				if (k == 0)
				{
					
					this.Liste = solver.getShortestPath(world.Cellules[row][col], world.Cellules[i][j]);
					taille = Listebis.size();
					k++;
				}
				else if(taillebis < taille && k!=0)
				{
					
					this.Liste = solver.getShortestPath(world.Cellules[row][col], world.Cellules[i][j]);
				
				}

			}
		}
	}
		return (Classe_Cellule)Liste.get(1);
}

public Classe_Cellule AStar_Robber(int row, int col, Classe_SimulationMap world)
{	
	
		
		
		l++;
		k = 0;
	
	 taille = 0;
	
	
	for (int i=0; i< this.Nb_Rows; i++)
	{
		for (int j=0; j< this.Nb_Cols; j++)
		{
			
			if(world.Cellules[i][j].Get_Cell_Natur() == '$'  /*liste_Coin.get(numero_coin(row,col)).actif == 1*/ )
			{
			
				this.Liste2bis = solver.getShortestPath(world.Cellules[row][col], world.Cellules[i][j]);
				taillebis = Liste2bis.size();
				if (k == 0)
				{
					taille = Liste2bis.size();
					this.Liste2 = solver.getShortestPath(world.Cellules[row][col], world.Cellules[i][j]);
					k++;
				}
				if(taillebis < taille && k!=0)
				{
					
					this.Liste2 = solver.getShortestPath(world.Cellules[row][col], world.Cellules[i][j]);
				
				}

			}
		}
	}
	
	return (Classe_Cellule)Liste2.get(1);
	/* else
		return null;*/
}	

public Classe_Cellule AStar_Sortie(int row, int col, Classe_SimulationMap world)
{	
	
		
		
		//l++;
		k = 0;
	
	 taille = 0;
	 this.Liste3 = solver.getShortestPath(world.Cellules[row][col], world.Cellules[0][0]);
	
	for (int i=0; i< this.Nb_Rows; i++)
	{
		
		
			if(world.Cellules[i][0].Get_Cell_Natur() == '_' )
			{
			
				this.Liste3bis = solver.getShortestPath(world.Cellules[row][col], world.Cellules[i][0]);
				taillebis = Liste3bis.size();
				if (k == 0)
				{
					
					this.Liste3 = solver.getShortestPath(world.Cellules[row][col], world.Cellules[i][0]);
					taille = Liste3.size();
					k++;
				}
				if(taillebis < taille && k!=0)
				{
					
					this.Liste3 = solver.getShortestPath(world.Cellules[row][col], world.Cellules[i][0]);
				
				}

			
		}
	}
	for (int i=0; i< this.Nb_Rows; i++)
	{
		
			
			if(world.Cellules[i][Nb_Cols-1].Get_Cell_Natur() == '_' )
			{
			
				this.Liste3bis = solver.getShortestPath(world.Cellules[row][col], world.Cellules[i][Nb_Cols-1]);
				taillebis = Liste3bis.size();
				if (k == 0)
				{
					
					this.Liste3 = solver.getShortestPath(world.Cellules[row][col], world.Cellules[i][Nb_Cols-1]);
					taille = Liste3.size();
					k++;
				}
				if(taillebis < taille && k!=0)
				{
					
					this.Liste = solver.getShortestPath(world.Cellules[row][col], world.Cellules[i][Nb_Cols-1]);
				
				}

			
		}
	}
	
	for (int j=0; j< this.Nb_Cols; j++)
	{
		
			
			if(world.Cellules[0][j].Get_Cell_Natur() == '_' )
			{
			
				this.Liste3bis = solver.getShortestPath(world.Cellules[row][col], world.Cellules[0][j]);
				taillebis = Liste3bis.size();
				if (k == 0)
				{
					
					this.Liste3 = solver.getShortestPath(world.Cellules[row][col], world.Cellules[0][j]);
					taille = Liste3.size();
					k++;
				}
				if(taillebis < taille && k!=0)
				{
					
					this.Liste3 = solver.getShortestPath(world.Cellules[row][col], world.Cellules[0][j]);
				
				}

			
		}
	}
	
	for (int j=0; j< this.Nb_Cols; j++)
	{
		
			
			if(world.Cellules[Nb_Rows-1][j].Get_Cell_Natur() == '_' )
			{
			
				this.Liste3bis = solver.getShortestPath(world.Cellules[row][col], world.Cellules[Nb_Rows-1][j]);
				taillebis = Liste3bis.size();
				if (k == 0)
				{
					
					this.Liste3 = solver.getShortestPath(world.Cellules[row][col], world.Cellules[Nb_Rows-1][j]);
					taille = Liste3.size();
					k++;
				}
				if(taillebis < taille && k!=0)
				{
					
					this.Liste3 = solver.getShortestPath(world.Cellules[row][col], world.Cellules[Nb_Rows-1][j]);
				
				}

			
		}
	}
		return (Classe_Cellule)Liste3.get(1);
}
int numero_robber(int row, int col)
{
	for(i=0; i< this.liste_Robber.size(); i++ )
	{
		if(this.liste_Robber.get(i).row == row && this.liste_Robber.get(i).col == col /*&& this.liste_Robber.get(i).actif == 1*/)
		{
			a = i;
			break;
		}
		
	}
	return a;
}

int numero_coin(int row, int col)
{
	for(i=0; i< this.liste_Coin.size(); i++ )
	{
		if(this.liste_Coin.get(i).row == row && this.liste_Coin.get(i).col == col /*&& this.liste_Coin.get(i).actif == 1*/)
		{
			a = i;
			break;
		}
		
	}
	return a;
}

int numero_robot(int row, int col)
{
	for(i=0; i< this.liste_Robot.size(); i++ )
	{
		if(this.liste_Robot.get(i).row == row && this.liste_Robot.get(i).row == col)
		{
			a = i;
			break;
		}
		
	}
	return a;
}

public void run(File file)
{
	continuer = true;
	this.loadMap(this.fichier);
	while(continuer)
	{
	this.world.frame.repaint(time);
	this.runOneStep();
	isOver();
	}
	System.out.println("Simulateur terminé");
	
	this.world.frame.repaint(time);
}
public void jouer_Robber()
{
	row_robber = (AStar_Robber(liste_Robber.get(p).row,liste_Robber.get(p).col,this.world)).getRow();
	col_robber = (AStar_Robber(liste_Robber.get(p).row,liste_Robber.get(p).col,this.world)).getCol();
	if(world.Cellules[row_robber][col_robber].Get_Cell_Natur() == '_')
	{
	world.Cellules[liste_Robber.get(p).row][liste_Robber.get(p).col].Put_Cell_Natur('_');
	liste_Robber.get(p).row = row_robber;
	liste_Robber.get(p).col = col_robber;
	world.Cellules[liste_Robber.get(p).row][liste_Robber.get(p).col].Put_Cell_Natur('I');
	row_robber = (AStar_Robber(liste_Robber.get(p).row,liste_Robber.get(p).col,this.world)).getRow();
	col_robber = (AStar_Robber(liste_Robber.get(p).row,liste_Robber.get(p).col,this.world)).getCol();
	}
	if(world.Cellules[row_robber][col_robber].Get_Cell_Natur() == '$' /*&& liste_Coin.get(numero_coin(row_robber,col_robber)).actif == 1*/)
	{
		
	world.Cellules[row_robber][col_robber].Put_Cell_Natur('_');
	liste_Coin.remove(numero_coin(row_robber,col_robber));
	liste_Robber.get(p).nombre_sacs =  liste_Robber.get(p).nombre_sacs + 1;
	//System.out.println("Avant le if i = " +p+" et Nombre Sacs = "+liste_Robber.get(p).nombre_sacs);	
	

	
	}
}
public void jouer_Robber2()
{ 
	
	row_robber = (AStar_Sortie(liste_Robber.get(p).row,liste_Robber.get(p).col,this.world)).getRow();
	col_robber = (AStar_Sortie(liste_Robber.get(p).row,liste_Robber.get(p).col,this.world)).getCol();
	
	if(world.Cellules[row_robber][col_robber].Get_Cell_Natur() == '_')
	{
	world.Cellules[liste_Robber.get(p).row][liste_Robber.get(p).col].Put_Cell_Natur('_');
	liste_Robber.get(p).row = row_robber;
	liste_Robber.get(p).col = col_robber;
	world.Cellules[liste_Robber.get(p).row][liste_Robber.get(p).col].Put_Cell_Natur('I');
	/*row_robber = (AStar_Sortie(liste_Robber.get(i).row,liste_Robber.get(i).col,this.world)).getRow();
	col_robber = (AStar_Sortie(liste_Robber.get(i).row,liste_Robber.get(i).col,this.world)).getCol();*/
	if(liste_Robber.get(p).row == 0 || liste_Robber.get(p).col == 0 || liste_Robber.get(p).row == Nb_Rows-1 || liste_Robber.get(p).col == Nb_Cols - 1)
	{
		liste_Robber.get(numero_robber(row_robber,col_robber)).actif = 0;
		world.Cellules[row_robber][col_robber].Put_Cell_Natur('_');
		nb_robber--;
	}
	/*else
	{
		liste_Robber.get(numero_robber(row_robber,col_robber)).actif = 0;
		world.Cellules[row_robber][col_robber].Put_Cell_Natur('_');
		nb_robber--;
	}*/
	}
	
	
}


}