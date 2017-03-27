import java.awt.Color;
import java.util.ArrayDeque;
import java.util.ArrayList;

import ch.aplu.jgamegrid.Location;
import ch.aplu.jgamegrid.Location.CompassDirection;
import tree.Gaertner;
import tree.Tree_Node;

/**
 * Data Class for AI important informations like the score or the current
 * selected Algorithm This information is only used for Debuging or Data
 * visualization purposes
 */
public class KIData {
	/**
	 * Current Status of the AI
	 */
	public static String KISTATUS = "";
	/**
	 * Score of the current Game
	 */
	public static int Score = 0;
	/**
	 * State of the AI System
	 */
	public static boolean running = false;
	/**
	 * Color of the KISTATUS message
	 */
	public static Color messagecolore = Color.GREEN;

	/**
	 * List of all {@link kidataupdater} that need to be updated
	 */
	public static ArrayList<kidataupdater> updatelist = new ArrayList<kidataupdater>();
	/**
	 * @see Gaertner
	 */
	public static int pills;
	/**
	 * @see Gaertner
	 */
	public static boolean pacpower;
	/**
	 * @see Gaertner
	 */
	public static double utillity;
	/**
	 * @see Gaertner
	 */
	public static boolean isterminal;
	/**
	 * @see Gaertner
	 */
	public static int pills_left;
	/**
	 * @see Gaertner
	 */
	public static double pacwalked;
	/**
	 * @see Gaertner
	 */
	public static double ghost_k;
	/**
	 * @see Gaertner
	 */
	public static double pilldistance;
	/**
	 * @see Gaertner
	 */
	public static double pill_remain;
	/**
	 * @see Gaertner
	 */
	public static double pillscore;
	/**
	 * @see Gaertner
	 */
	public static double ppill_remain;
	/**
	 * @see Gaertner
	 */
	public static double ppilldistance;
	/**
	 * @see Gaertner
	 */
	public static int Ppills;
	/**
	 * @see Gaertner
	 */
	public static int Ppillscore;
	/**
	 * @see Gaertner
	 */
	public static CompassDirection cdd = CompassDirection.NORTH;
	/**
	 * @see Gaertner
	 */
	public static int KI_select = KI.MIN_MAX_AI;
	/**
	 * @see Gaertner
	 */
	public static Tree_Node root;
	public static gamecontroller gm;
	public static Tree_Node resultleave;
	public static int Tree_Debth = 2;
	/**
	 * Distance that triggers the ghost scare event in the smart reflex agent
	 * @see KI#ghostavoid()
	 */
	public static int Ghost_Scare_distance=3;
	
	/**
	 * Enables A*-Search Drawing
	 */
	public static boolean draw_asearch=false;
	
	
	/**
	 * Tick speed of the Game
	 */
	public static int simspeed = 60;
	/**
	 * Location of Pacman
	 */
	public static Location pacloc;
	/**
	 * @see Statskeeper
	 */
	public static Statskeeper sk;
	/**
	 * Enables the statmode
	 */
	public static boolean statmode = false;
	/**
	 * Connectscreen 
	 */
	public static connectscreen cs;
	
	public static boolean zappelswitch = false;
	/**
	 * Activates zeroghost state
	 */
	public static boolean zeroghost=false;

	/**
	 * Adds an {@link kidataupdater} to the Updaterlist
	 * 
	 * @param up
	 *            {@link kidataupdater} object to add
	 */
	public static void addupdater(kidataupdater up) {
		updatelist.add(up);
	}

	/**
	 * calls update in all {@link kidataupdater} classes
	 */
	public synchronized static void update() {
		for (kidataupdater kidataupdater : updatelist) {
			kidataupdater.update();
		}
	}

	/**
	 * Updates the Status of the AI
	 * 
	 * @param message
	 *            Message to set
	 * @param c
	 *            Color of the message
	 */
	public synchronized static void updatestatus(String message, Color c) {
		KIData.KISTATUS = message;
		KIData.messagecolore = c;
		System.out.println("[KI MESSAGE] " + message);
		KIData.update();
	}

	/**
	 * Resets all data
	 */
	public static void reset() {
		KISTATUS = "";
		Score = 0;
		running = false;
		messagecolore = Color.green;
		updatelist = new ArrayList<kidataupdater>();
		pills = 0;
		pacpower = false;
		utillity = 0.0;
		isterminal = false;
		pills_left = 0;
		pacwalked = 0;
		ghost_k = 0;
		pilldistance = 0;
		pill_remain = 0;
		pillscore = 0;
		ppill_remain = 0;
		ppilldistance = 0;
		Ppills = 0;
		Ppillscore = 0;
		cdd = CompassDirection.NORTH;
		KI_select = KI.MIN_MAX_AI;
		root = null;
		gm = null;
		resultleave = null;
		Tree_Debth = 2;
		simspeed = 60;
		pacloc = null;
		sk = null;
		statmode = false;
	}
}
