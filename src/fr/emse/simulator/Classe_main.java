package fr.emse.simulator;
import java.io.*;

import fr.emse.*;
import fr.emse.simulator.gui.MapFrame;
public class Classe_main
{

public static void main(String[] args)
	{
	File file_main = new File("world.txt");
	
	Classe_Simulateur simulateur = new Classe_Simulateur(file_main);
	simulateur.run(file_main);
		
		
		
	}
}