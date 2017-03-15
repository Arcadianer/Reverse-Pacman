
// PacGrid.java

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.beans.Expression;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.KeyStore.Entry.Attribute;
import java.util.ArrayList;

import javax.swing.InputMap;

import org.jfree.ui.HorizontalAlignment;
import org.omg.CORBA.INITIALIZE;

import block.*;

import ch.aplu.jgamegrid.*;

/**
 * Game-Grid Level Class This Class represents the Game-Grid Level
 */
public class PacGrid {
	/**
	 * Number of Horizontal Blocks
	 */
	private static int nbHorzCells = 30;
	/**
	 * Number of Vertical Blocks
	 */
	private static int nbVertCells = 33;
	/**
	 * Level Array in which the Level Layout is saved as Blocks
	 */
	public static block[][] maze = new block[getNbVertCells()][getNbHorzCells()];
	/**
	 * List of all Blocks that "can be" in the Level
	 */
	public static ArrayList<block> bl = new ArrayList<block>();
	/**
	 * String representation of the Level
	 */
	public static String mazestring;
	/**
	 * Spawn Location of Pacman
	 */
	public static Location spawn_pacman;
	/**
	 * Spawn Location of Ghost 1
	 */
	public static Location spawn_ghost1;
	/**
	 * Spawn Location of Ghost 2
	 */
	public static Location spawn_ghost2;
	/**
	 * Spawn Location of Ghost 3
	 */
	public static Location spawn_ghost3;
	/**
	 * Spawn Location of Ghost 4
	 */
	public static Location spawn_ghost4;
/**
 * Name of the Level
 */
	public static String Name;
	/**
	 * Maximum number of Pills
	 */
	public static int points;
/**
 * Constructor of Class
 * Loads level of given Path
 * @param lvlstring Path of Level to load
 */
	public PacGrid(String lvlstring) {
		blockinit();
		if (loadlevel(lvlstring)) {
			System.out.println("LEVEL LOADED " + lvlstring);
		}
		this.Name = lvlstring;

		System.out.println(mazestring.length());
		// Copy structure into integer array
		for (int i = 0; i < getNbVertCells(); i++) {
			for (int k = 0; k < getNbHorzCells(); k++) {
				maze[i][k] = toBlockObject(mazestring.charAt(getNbHorzCells() * i + k));

			}

		}

	}
/**
 * Loads level of given path
 * @param path Path of Level to load
 * @return True if Succsefull 
 * 
 */
	public boolean loadlevel(String path) {
		try {
			KIData.updatestatus("LOADING LEVEL", Color.yellow);
			String lvpath = path + ".txt";
			String stpath = path + ".awesome";
			InputStream is = getClass().getResourceAsStream(lvpath);
			InputStream si = getClass().getResourceAsStream(stpath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			BufferedReader sbr = new BufferedReader(new InputStreamReader(si));
			String stemp = sbr.readLine();
			while (!(stemp == null)) {
				settingsread(stemp);
				stemp = sbr.readLine();

			}
			maze = new block[getNbVertCells()][getNbHorzCells()];
			String temp = br.readLine();
			mazestring = "";
			while (!(temp == null)) {
				mazestring = mazestring + temp;
				System.out.println(temp);
				temp = br.readLine();

			}

			for (int a = 0; a < mazestring.length(); a++) {
				if ((mazestring.charAt(a) == '.') || mazestring.charAt(a) == 'p') {
					this.points++;
				}
			}
			System.out.println("###LEVEL SETTINGS###");
			System.out.println("Horizontal Cells: "+nbHorzCells);
			System.out.println("Vertical Cells: "+nbVertCells);
			System.out.println("Number of Pills: " + points);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}

	}
/**
 *Gets number of horizontal blocks
 *@return Number of horizontal blocks 
 */

	public static int getnbhor() {
		return getNbHorzCells();

	}
	/**
	 *Gets number of vertical blocks
	 *@return Number of vertical blocks 
	 */
	public static int getnbver() {
		return getNbVertCells();

	}
/**
 *Draws coordinate system on Game-Grid
 */

	public void drawcords(GGBackground bg) {
		bg.setPaintColor(Color.red);
		bg.setFont(new Font("Arial", Font.BOLD, 10));
		for (int a = 0; a < getnbver(); a++) {

			bg.drawText("" + a, new Point(15, a * 30 + 15));
		}
		for (int a = 0; a < getnbhor(); a++) {
			bg.drawText("" + a, new Point(a * 30 + 15, 15));
		}
	}
/**
 * Interprets settings string and sets attributes
 * @param command String of the settings file
 */
	private void settingsread(String command) {
		if (command.contains("hor:")) {
			String temp = command.substring(4);
			setNbHorzCells(Integer.parseInt(temp));
			PacMan.nbHorzCells = Integer.parseInt(temp);
		} else if (command.contains("ver:")) {
			String temp = command.substring(4);
			setNbVertCells(Integer.parseInt(temp));
			PacMan.nbVertCells = Integer.parseInt(temp);
		} else if (command.contains("spawn_pac:")) {
			String temp = command.substring(10);
			String[] templ = temp.split("\\|");
			spawn_pacman = new Location(Integer.valueOf(templ[0]), Integer.valueOf(templ[1]));
		} else if (command.contains("spawn_ghost1:")) {
			String temp = command.substring(13);
			String[] templ = temp.split("\\|");
			spawn_ghost1 = new Location(Integer.valueOf(templ[0]), Integer.valueOf(templ[1]));
		} else if (command.contains("spawn_ghost2:")) {
			String temp = command.substring(13);
			String[] templ = temp.split("\\|");
			spawn_ghost2 = new Location(Integer.valueOf(templ[0]), Integer.valueOf(templ[1]));
		} else if (command.contains("spawn_ghost3:")) {
			String temp = command.substring(13);
			String[] templ = temp.split("\\|");
			spawn_ghost3 = new Location(Integer.valueOf(templ[0]), Integer.valueOf(templ[1]));
		} else if (command.contains("spawn_ghost4:")) {
			String temp = command.substring(13);
			String[] templ = temp.split("\\|");
			spawn_ghost4 = new Location(Integer.valueOf(templ[0]), Integer.valueOf(templ[1]));
		}

	}
/**
 * Initialize Blocklist
 */
	public void blockinit() {
		bl.add(new dot());
		bl.add(new justblockall());
		bl.add(new nothing());
		bl.add(new ohor());
		bl.add(new overtical());
		bl.add(new ocorner1());
		bl.add(new ocorner2());
		bl.add(new ocorner3());
		bl.add(new ocorner4());
		bl.add(new t1());
		bl.add(new t2());
		bl.add(new t3());
		bl.add(new t4());
		bl.add(new e1());
		bl.add(new e2());
		bl.add(new e3());
		bl.add(new e4());
		bl.add(new powerpille());
	}

	/*
	 * public void invertlevel(String s){ for(int a=0;a<nbVertCells;a++){
	 * for(int b=0;b<nbHorzCells;b++){ try{ System.out.print(maze[a][b].id);
	 * }catch(NullPointerException x){ System.out.print(" "); } }
	 * System.out.println(); } }
	 */
	
	/**
	 * Gets the block object from maze array
	 * @param location Location of Block
	 * @return Block objects of location  
	 */
	public static block getCell(Location location) {
		block temp = bl.get(2);
		try {
			temp = maze[location.y][location.x];
		} catch (Exception e) {

		}
		return temp;
	}
@Deprecated
	public boolean isInnerRegion(Location location) {
		if (location.equals(new Location(14, 13)) || location.equals(new Location(15, 13))
				|| location.equals(new Location(14, 14)) || location.equals(new Location(15, 14))
				|| location.equals(new Location(13, 15)) || location.equals(new Location(15, 15))
				|| location.equals(new Location(15, 15)) || location.equals(new Location(16, 16))) {
			return true;
		}
		return false;
	}
/**
 * Converts char to block object
 * @return Block of given char
 */
	private block toBlockObject(char c) {
		String test = String.valueOf(c);

		for (block blocks : bl) {
			if (blocks.id.equals(test)) {

				return blocks.clone();
			}
		}
		return null;
	}
	/**
	 *Gets number of vertical blocks
	 *@return Number of vertical blocks 
	 */
	public static int getNbVertCells() {
		return nbVertCells;
	}

	public static void setNbVertCells(int nbVertCells) {
		PacGrid.nbVertCells = nbVertCells;
	}

	/**
	 *Gets number of horizontal blocks
	 *@return Number of horizontal blocks 
	 */
	public static int getNbHorzCells() {
		return nbHorzCells;
	}

	public static void setNbHorzCells(int nbHorzCells) {
		PacGrid.nbHorzCells = nbHorzCells;
	}
/**
 * Clones Level
 */

	public static block[][] clonemaze() {
		block[][] clone = new block[getnbver()][getnbhor()];
		for (int a = 0; a < maze.length; a++) {
			block[] mazerow = maze[a];
			for (int b = 0; b < mazerow.length; b++) {
				clone[a][b] = maze[a][b].clone();
			}
		}

		return clone;

	}

}
