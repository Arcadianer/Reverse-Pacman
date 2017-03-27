
// PacMan.java
// Simple PacMan implementation

import ch.aplu.jgamegrid.*;
import ch.aplu.util.Console;
import nav.Route;
import scala.collection.parallel.ParIterableLike.Foreach;
import tree.Gaertner;
import tree.gamestate;
import util.Stopwatch;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.SynchronousQueue;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;
import javax.swing.plaf.synth.SynthSpinnerUI;

import org.omg.CORBA.INITIALIZE;

import block.block;
/**
 * MAIN CLASS 
 * This class is the core class which control's the game
 */
public class PacMan extends GameGrid implements gamecontroller {
	/**
	 * Number of Horizontal Blocks
	 */
	public static int nbHorzCells = 30;
	/**
	 * Number of Vertical Blocks
	 */
	public static int nbVertCells = 33;
	
	/**
	 * Actor of Pacman AI
	 */
	public static KIActor pacActor;

	/**
	 * List of all {@link Ghostplayer} Objects
	 */
	public static ArrayList<Ghostplayer> playerlist = new ArrayList<Ghostplayer>();
	/**
	 *{@link PacGrid} object of the {@link GameGrid} 
	 */
	public static PacGrid grid;
	/**
	 * Scanner object for console input
	 */
	public Scanner s;
	/**
	 * {@link TcpBridge} that connects the app with the game
	 */
	public TcpBridge tpb;
	
	BufferedImage bi;
	/**
	 * {@link KI} core object that controlls Pacman
	 * @see KI
	 */
	public static KI ki;
	/**
	 * {@link Sounds}-Object that is responsible for the sound in the game
	 * @see Sounds 
	 */
	public static Sounds sounds;
	/**
	 *@see Navigation 
	 */
	public static Navigation navi;
	/**
	 * Setting that enables AI for ghost
	 */
	public static boolean ghostki = false;
	/**
	 * Setting that idles the AI for the ghost
	 */
	public static boolean ghostdumm = false;
	/**
	 * Setting that enables the debug options
	 */
	public static boolean debug = false;
	/**
	 * Setting that draws the nodes on the Game-Grid
	 */
	public static boolean drawnode = false;
	/**
	 * Setting that draws the connections between the nodes on the Game-Grid
	 */
	public static boolean drawconnection = false;
	/**
	 * Setting that draws a coordinate system on the Game-Grid
	 */
	public static boolean drawcords = false;
	/**
	 * @see Navigation#precalclist
	 */
	public static Route[][][][] precalclist;
	/**
	 * Setting that draws Routes on the Game-Grid 
	 */
	public final static boolean drawrouts = false;
	private static GGBackground bg;
	public boolean pacwin = false;
	/**
	 * Setting that disables the Music
	 */
	public static boolean nomusic = false;
/**
 * Constructor of the PacMan Class
 */
	public PacMan(Sounds sn, PacGrid pg, int players, boolean ghostki) throws IOException, InterruptedException {
		super(nbHorzCells, nbVertCells, 30, false);
		KIData.updatestatus("INIT", Color.yellow);
		KIData.gm = this;
		this.sounds = sn;
		this.grid = pg;
		this.ghostki = ghostki;
		setVisible(true);
		setSimulationPeriod(KIData.simspeed);
		setTitle("REVERSE PAC-MAN");
		
		s = new Scanner(System.in);
		this.bg = getBg();
		bi = ImageIO.read(this.getClass().getResource("res/test.gif"));
		show();
		drawGrid(bg);
		if (drawcords)
			grid.drawcords(bg);
		this.navi = new Navigation();
		// navi.drawconnection(getBg());
		if (drawnode)
			navi.drawnode(getBg());
		if (drawconnection)
			navi.drawconnection(bg);

		// addActor(pacActor, new Location(14, 24));
		pacActor = new KIActor();
		pacActor.setSlowDown(1);
		addActor(pacActor, PacGrid.spawn_pacman);

	
		// TEST ZONE
	
		// navi.precalclist=navi.precalcallroutes();

		// INIT DONE
		KIData.updatestatus("SETUP", Color.yellow);
		howmanyplayer(players);

		System.out.println("========= READY TO CONNECT =========");
		KIData.updatestatus("Waiting", Color.yellow);
		

		if (!ghostki) {

			KIData.cs=new connectscreen(players);
			KIData.cs.setVisible(true);
		}
		tpb.ackall();
		tpb.startlisten();
		delay(1500);
		if(!ghostki)
		KIData.cs.setVisible(false);
		KIData.updatestatus("GO !!!", Color.green);
		if (!debug) {
			sounds.start.start();
			Thread.sleep(8707);
		}
		sounds.menusound.stop();

		sounds.gamemusic.loop(Clip.LOOP_CONTINUOUSLY);

		ArrayList<Location> ghlist = new ArrayList<Location>();

		for (Ghostplayer gh : playerlist) {
			ghlist.add(gh.getLocation());
		}
		if (playerlist.size() == 2 && PacGrid.Name.equals("level/level1")) {
			KIData.sk.startlog();
		}
		navi.getpills();

		show();
		doRun();

		// Loop to look for collision in the application thread
		// This makes it improbable that we miss a hit

		boolean justwait = true;
		while (justwait) {
			if (winforghost())
				justwait = false;
			if (pacwin())
				justwait = false;
			killghost();
			delay(10);
		}
		if (pacwin) {
			getBg().setPaintColor(Color.yellow);
			getBg().setFont(new Font("Arial", Font.BOLD, 96));
			getBg().drawText("PACMAN WINS", new Point(toPoint(new Location(2, 15))));
			KIData.updatestatus("PACMAN WINS", Color.green);
			KIData.sk.update(1, KIData.Score);
		} else {
			getBg().setPaintColor(Color.red);
			getBg().setFont(new Font("Arial", Font.BOLD, 96));
			getBg().drawText("Game Over", new Point(toPoint(new Location(2, 15))));
			KIData.updatestatus("Ghosts win", Color.red);
			KIData.sk.update(0, KIData.Score);
		}
		Sounds.chomp.stop();
		Sounds.gamemusic.stop();
		nomusic = true;
		KIData.sk.savelog();
		doPause();
		if(KIData.statmode){
			
			tpb.removeall();
		}
		
		if(!KIData.statmode){
			while(true){
				Thread.sleep(1000);
			}
		}
		setVisible(false);
		dispose();
		Thread.sleep(1000);

	}

	public static GGBackground getbg() {
		return bg;
	}
/**
 * Initialize {@link Ghostplayer}-objects
 * @param players Number of players between 1-4 
 */
	private void howmanyplayer(int players) throws IOException {
		tpb = new TcpBridge();
		System.out.println("========= NEW PACMAN GAME =========\n");
		boolean numberok = false;
		int numberplayers = 0;
		while (!numberok) {
			System.out.print("HOW MANY PLAYERS (1-4) : ");
			numberplayers = players;
			System.out.println(players);
			if ((0 <= numberplayers) && (4 >= numberplayers)) {
				numberok = true;

			} else {
				System.out.println("Number out of Range (1-4)");
			}
		}
		Ghostplayer temp = null;
		Ghostplayer temp1 = null;
		Ghostplayer temp2 = null;
		Ghostplayer temp3 = null;
		switch (numberplayers) {
		case 0:
			temp = new Ghostplayer(this, 5, navi);
			tpb.addPlayer(temp, "GHOST GHOST");
			playerlist.add(temp);
			addActor(temp, PacGrid.spawn_ghost1, Location.NORTH);
			KIData.zeroghost=true;
			break;
		case 1:
			temp = new Ghostplayer(this, 1, navi);
			tpb.addPlayer(temp, "PLAYER 1");
			playerlist.add(temp);
			addActor(temp, PacGrid.spawn_ghost1, Location.NORTH);
			break;
		case 2:
			temp = new Ghostplayer(this, 1, navi);
			tpb.addPlayer(temp, "PLAYER 1");
			playerlist.add(temp);
			addActor(temp, PacGrid.spawn_ghost1, Location.NORTH);
			temp1 = new Ghostplayer(this, 2, navi);
			tpb.addPlayer(temp1, "PLAYER 2");
			playerlist.add(temp1);
			addActor(temp1, PacGrid.spawn_ghost2, Location.NORTH);
			break;
		case 3:
			temp = new Ghostplayer(this, 1, navi);
			tpb.addPlayer(temp, "PLAYER 1");
			playerlist.add(temp);
			addActor(temp, PacGrid.spawn_ghost1, Location.NORTH);
			temp1 = new Ghostplayer(this, 2, navi);
			tpb.addPlayer(temp1, "PLAYER 2");
			playerlist.add(temp1);
			addActor(temp1, PacGrid.spawn_ghost2, Location.NORTH);
			temp2 = new Ghostplayer(this, 3, navi);
			tpb.addPlayer(temp2, "PLAYER 3");
			playerlist.add(temp2);
			addActor(temp2, PacGrid.spawn_ghost3, Location.NORTH);

			break;
		case 4:
			temp = new Ghostplayer(this, 1, navi);
			tpb.addPlayer(temp, "PLAYER 1");
			playerlist.add(temp);
			addActor(temp, PacGrid.spawn_ghost1, Location.NORTH);
			temp1 = new Ghostplayer(this, 2, navi);
			tpb.addPlayer(temp1, "PLAYER 2");
			playerlist.add(temp1);
			addActor(temp1, PacGrid.spawn_ghost2, Location.NORTH);
			temp2 = new Ghostplayer(this, 3, navi);
			tpb.addPlayer(temp2, "PLAYER 3");
			playerlist.add(temp2);
			addActor(temp2, PacGrid.spawn_ghost3, Location.NORTH);
			temp3 = new Ghostplayer(this, 4, navi);
			tpb.addPlayer(temp3, "PLAYER 4");
			playerlist.add(temp3);
			addActor(temp3, PacGrid.spawn_ghost4, Location.NORTH);
			break;

		}
		System.out.println(numberplayers + " PLAYERS ADDED");
		for (Ghostplayer ghostplayer : playerlist) {
			ghostplayer.setSlowDown(3);
		}
		if (ghostki) {
			tpb = new TcpBridge();
		}

	}
/**
 * Checks if ghost won 
 * @return true if ghost won
 */
	public boolean winforghost() {
		boolean result = false;
		if (KIActor.getpower == false) {
			Location pacloc = pacActor.getLocation();
			for (Ghostplayer ghostplayer : playerlist) {
				if (ghostplayer.getLocation().equals(pacloc)) {
					result = true;
					Location loc = pacActor.getLocation();
					pacActor.removeSelf();

					addActor(new Actor("sprites/explosion3.gif"), loc);

					Sounds.death.start();
				}
			}
		}
		return result;
	}
/**
 * Kills ghost if the kill conditions are met 
 */
	public void killghost() {
		if (KIActor.getpower) {
			Location pacloc = pacActor.getLocation();
			for (Ghostplayer gh : playerlist) {
				if (gh.getLocation().equals(pacloc)) {
					System.out.println("EXECUTE GHOST KILL");
					try {
						gh.getcon().disconnect();
						Sounds.eatghost.setFramePosition(0);
						Sounds.eatghost.start();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println("Cant not Disconnect Client (maybe ghost ki?)");
					}
					gh.removeSelf();
					playerlist.remove(gh);
					gh = null;
					System.out.println("GHOST KILLED");
					break;
				}
			}
		}

	}
/**
 * Checks if Pacman wins
 * @return true if Pacman has won
 */
	public boolean pacwin() {

		if ((pacActor.nbPills) >= PacGrid.points ||  (!KIData.zeroghost&&playerlist.size() == 0)) {

			Sounds.ppsound.stop();
			Sounds.gamemusic.stop();
			pacwin = true;
			return true;
		}
		return false;
	}
/**
 * Draws Pac-Grid 
 */
	private void drawGrid(GGBackground bg) {
		bg.clear(Color.black);
		bg.setPaintColor(Color.white);
		for (int y = 0; y < nbVertCells; y++) {
			for (int x = 0; x < nbHorzCells; x++) {
				Location location = new Location(x, y);
				block a = grid.getCell(location);

				a.draw(location, bg);

			}
		}
		show();
	}

	public boolean isGhostki() {
		return ghostki;
	}

	public void setGhostki(boolean ghostki) {
		this.ghostki = ghostki;
	}

	public boolean isGhostdumm() {
		return ghostdumm;
	}

	public void setGhostdumm(boolean ghostdumm) {
		this.ghostdumm = ghostdumm;
	}

	public static void main(String[] args)
			throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
		if(args.length>0){
			System.out.println("[STAT MODE ACTIVE]");
			System.out.println("SIMSPEED=5");
			
			int aiselect=Integer.valueOf(args[0]);
			
		
		
			KIData.reset();
			KIData.statmode=true;
			KIData.KI_select=aiselect;
			KIData.simspeed=5;
			System.out.println("PP WAIT TIME :"+ (long) (KIData.simspeed*16.66666));
			PacMan.debug=true;
			PacMan.playerlist=new ArrayList<Ghostplayer>();
			PacMan.nomusic=true;
			PacMan pm = new PacMan(new Sounds(), new PacGrid("level/level1"), 2, true);
			pm=null;
			
			
		
		
		}else{
			
			
		
		System.out.println("REVERSE PACMAN v1.0 beta.1");
		Mainmenu mu = new Mainmenu();

		mu.setVisible(true);
		while (mu.wait) {
			Thread.sleep(1000);
		}
		mu.wait = true;

		PacMan pm = new PacMan(mu.sn, mu.pg, mu.Player, mu.Ghostki);

		mu.dispose();
		mu = null;
	}
		System.exit(0);

	}

	public static ArrayList<Ghostplayer> getPlayerlist() {
		return playerlist;
	}
/*
 * (non-Javadoc)
 * @see gamecontroller#pause()
 */
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		doPause();
		KIData.updatestatus("PAUSE", Color.RED);
	}
/*
 * (non-Javadoc)
 * @see gamecontroller#resume()
 */
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		doRun();
	}
/*
 * (non-Javadoc)
 * @see gamecontroller#musicstop()
 */
	@Override
	public void musicstop() {
		// TODO Auto-generated method stub
		nomusic = true;
		Sounds.gamemusic.stop();
		Sounds.ppsound.stop();

	}
/*
 * (non-Javadoc)
 * @see gamecontroller#musicplay()
 */
	@Override
	public void musicplay() {
		// TODO Auto-generated method stub
		nomusic = false;
		Sounds.gamemusic.start();
	}
/*
 * (non-Javadoc)
 * @see gamecontroller#setgamespeed(int)
 */
	@Override
	public void setgamespeed(int speed) {
		// TODO Auto-generated method stub
		doPause();
		setSimulationPeriod(speed);
		doRun();
	}
/*
 * (non-Javadoc)
 * @see gamecontroller#dostep()
 */
	@Override
	public void dostep() {
		// TODO Auto-generated method stub
		KIData.updatestatus("STEP", Color.yellow);

		doStep();
	}

}
