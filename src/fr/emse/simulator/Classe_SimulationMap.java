package fr.emse.simulator;
import java.io.*;
import java.util.*;

import fr.emse.simulator.world.*;
import fr.emse.simulator.gui.*;

import java.util.Vector;
public class Classe_SimulationMap implements SimulationMap
{
	
	
	MapFrame frame;
	File f = new File("world.txt");
	int dizaine;
	int unites;
	public Classe_Cellule[][] Cellules ;
int i;
int j;
	
	public ArrayList<Classe_Robot> liste_Robot;
	public ArrayList<Classe_Robber> liste_Robber;
	
	public Classe_SimulationMap(File fichier)
	{
		
		 frame = new MapFrame(this);
		 Cellules = new Classe_Cellule[this.getNbRows()][this.getNbCols()];
		 for(i=0; i< this.getNbRows(); i++)
		 {
			 for(j=0; j < this.getNbCols(); j++)
			 {
				 this.Cellules[i][j] = new Classe_Cellule();
			 }
		 }
	// Cellules = new Classe_Cellule[3][4];
		
	}
	public void creer()
	{
		
		
	}
	
	@Override
	public Cell get(int row, int col) {
		// TODO Auto-generated method stub
		return this.Cellules[row][col];
	
	}

	@Override
	public int getNbCols() {
	
	try
		{
			FileInputStream fis2 = new FileInputStream(f);
			fis2.read();
			fis2.read();
			fis2.read();
			
			dizaine = Character.getNumericValue(fis2.read());
			unites = Character.getNumericValue(fis2.read());
		//	System.out.println(10*dizaine + unites);
			return(10*dizaine + unites);
			
		}
		catch(FileNotFoundException e)
		{
		 return -1;
		}
		catch(IOException e)
		{
		 return -1;
		}
	
	catch(NullPointerException e)
	{
	 return -1;
	}
		
	}

	@Override
	public int getNbRows() {
		try
		{
			
			FileInputStream fis3 = new FileInputStream(f);
			dizaine = Character.getNumericValue(fis3.read());
			unites = Character.getNumericValue(fis3.read());
		//	System.out.println(10*dizaine + unites);
			return(10*dizaine + unites);
		}
		catch(FileNotFoundException e)
		{
		 return 7;
		}
		catch(IOException e)
		{
		 return 5;
		}
		catch(NullPointerException e)
		{
		 return -1;
		}
		
	}

	
	
  
}


