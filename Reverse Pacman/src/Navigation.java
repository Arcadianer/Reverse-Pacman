import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.spi.CalendarNameProvider;

import javax.print.attribute.standard.NumberUpSupported;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import org.omg.CORBA.INITIALIZE;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GGBackground;
import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;
import ch.aplu.jgamegrid.Location.CompassDirection;
import nav.*;

import util.Stopwatch;
import block.*;
/**
 * Navigation class for Pacman (is a kind of GPS)
 * Creates Routes and calculates distances
 */
public class Navigation {
	/**
	 * Boolean Array of possible locations on which Pacman can walk
	 * Usage : movinglist[y][x]
	 */
	boolean[][] movinglist;
	/**
	 * List of all nodes of the Map
	 * @see node
	 */
	ArrayList<node> nodelist = new ArrayList<node>();
	ArrayList<Location> pilllist = new ArrayList<Location>();
	ArrayList<Location> walklist = new ArrayList<Location>();
	private int movingblocks = 0;
	int done = 0;
	String Aserchstats = "";
	public final int blacklistcount = 3;
	public Route[][][][] precalclist;
/**
 * Constructor of Navigation Class
 * Automatically scanns map to generate Node map for Navigation
 */
	public Navigation() {
		log("INITIALIZEING");
		movinglist = new boolean[PacGrid.getnbver()][PacGrid.getnbhor()];
		log("ANALIZING LEVEL");
		getallblocksthatyoucanwalkon();
		log("GET NODES");
		getnode();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log("ROAD MAP");
		connecttonode();
		getpills();
		log("NAVIGATION MAP DONE");

	}
	/**
	 * Prints message as Log Message
	 * @param message Message to print
	 */
	public void log(String message) {
		KIData.updatestatus(message, Color.yellow);
		System.out.println("[Navigation] " + message);
	}
/**
 * Method that scans for possible node connections in map
 */
	private void connecttonode() {
		for (node nodes : nodelist) {

			if (nodes.partner[0]) {
				boolean temp = movinglist[nodes.y - 1][nodes.x];
			
				for (int a = -1; temp; a--) {
					for (node nodesto : nodelist) {

						Location test1 = new Location(nodesto.x, nodesto.y);
						Location test2 = new Location(nodes.x, nodes.y + a);
						
						// System.out.println("{"+nodes.name+"|"+test2.toString()+";"+nodesto.name+"|"+test1.toString()+";"+a+"}");
						if (test1.toString().equals(test2.toString())) {
							way newway = new way(nodesto, a);
							nodes.nodelist[0] = newway;
							System.out.println("NODE CONNECTED");
							System.out.println("{" + nodes.name + "|" + "(" + nodes.x + "," + nodes.y + ")" + ";"
									+ nodesto.name + "|" + test1.toString() + ";" + a + "}");
							temp = false;
							break;
						}
					}
					if (temp)
						temp = movinglist[nodes.y + a][nodes.x];
				}
			}
			if (nodes.partner[1]) {
				boolean temp = movinglist[nodes.y + 1][nodes.x];
				for (int a = 1; temp; a++) {
					for (node nodesto : nodelist) {
						if (nodesto.name.equals("N8")) {

						}
						Location test1 = new Location(nodesto.x, nodesto.y);
						Location test2 = new Location(nodes.x, nodes.y + a);
						if (test2 == new Location(7, 8)) {
							System.out.println();
						}
						// System.out.println("{"+nodes.name+"|"+test2.toString()+";"+nodesto.name+"|"+test1.toString()+";"+a+"}");
						if (test1.toString().equals(test2.toString())) {
							way newway = new way(nodesto, a);
							nodes.nodelist[1] = newway;
							System.out.println("NODE CONNECTED");
							System.out.println("{" + nodes.name + "|" + "(" + nodes.x + "," + nodes.y + ")" + ";"
									+ nodesto.name + "|" + test1.toString() + ";" + a + "}");
							temp = false;
							break;
						}
					}
					if (temp)
						temp = movinglist[nodes.y + a][nodes.x];
				}

			}
			if (nodes.partner[2]) {
				boolean temp = movinglist[nodes.y][nodes.x - 1];
				for (int a = -1; temp; a--) {
					for (node nodesto : nodelist) {
						Location test1 = new Location(nodesto.x, nodesto.y);
						Location test2 = new Location(nodes.x + a, nodes.y);
						// System.out.println("{"+nodes.name+"|"+test2.toString()+";"+nodesto.name+"|"+test1.toString()+";"+a+"}");
						if (test1.toString().equals(test2.toString())) {
							way newway = new way(nodesto, a);
							nodes.nodelist[2] = newway;
							System.out.println("NODE CONNECTED");
							System.out.println("{" + nodes.name + "|" + "(" + nodes.x + "," + nodes.y + ")" + ";"
									+ nodesto.name + "|" + test1.toString() + ";" + a + "}");
							temp = false;
							break;
						}
					}
					if (temp)
						temp = movinglist[nodes.y][nodes.x + a];
				}

			}
			if (nodes.partner[3]) {
				boolean temp = movinglist[nodes.y][nodes.x + 1];
				for (int a = 1; temp; a++) {
					for (node nodesto : nodelist) {
						Location test1 = new Location(nodesto.x, nodesto.y);
						Location test2 = new Location(nodes.x + a, nodes.y);
						// System.out.println("{"+nodes.name+"|"+test2.toString()+";"+nodesto.name+"|"+test1.toString()+";"+a+"}");
						if (test1.toString().equals(test2.toString())) {
							way newway = new way(nodesto, a);
							nodes.nodelist[3] = newway;
							System.out.println("NODE CONNECTED");
							System.out.println("{" + nodes.name + "|" + "(" + nodes.x + "," + nodes.y + ")" + ";"
									+ nodesto.name + "|" + test1.toString() + ";" + a + "}");
							temp = false;
							break;
						}
					}
					if (temp)
						temp = movinglist[nodes.y][nodes.x + a];
				}

			}

		}

	}
/**
 * Scans map for blocks that Pacman can walk on
 * 
 */
	private void getallblocksthatyoucanwalkon() {
		int asa = 0;
		for (int a = 0; a < PacGrid.getnbver(); a++) {
			String temp = "";
			for (int b = 0; b < PacGrid.getnbhor(); b++) {
				block br = PacGrid.maze[a][b];
				movinglist[a][b] = !br.blockmovment;

				if (movinglist[a][b]) {
					temp = temp + "-";
					asa++;
					walklist.add(new Location(b, a));
				} else {
					temp = temp + "x";
				}
			}
			System.out.println(temp);
		}
		System.out.println("Block on that you can walk on "+asa);
		this.movingblocks = asa;
	}
/**
 * Gets all pills and adds them to the pilllist
 */
	public void getpills() {
		pilllist.clear();
		for (int y = 0; y < PacGrid.getnbver(); y++) {
			for (int x = 0; x < PacGrid.getnbhor(); x++) {
				block bl = PacGrid.maze[y][x];
				if (bl.id.equals(".")) {
					pilllist.add(new Location(x, y));
				} else if (bl.id.equals("p")) {
					pilllist.add(new Location(x, y));
				}
			}
		}
	}
/**
 * Serches for nodes
 */
	private void getnode() {
		int number = 1;
		for (int a = 0; a < PacGrid.getnbver(); a++) {

			for (int b = 0; b < PacGrid.getnbhor(); b++) {
				if (movinglist[a][b]) {
					node temp = new node(b, a);
					temp.partner[0] = movinglist[a - 1][b];
					temp.partner[1] = movinglist[a + 1][b];
					temp.partner[2] = movinglist[a][b - 1];
					temp.partner[3] = movinglist[a][b + 1];
					boolean pattern1[] = { false, false, true, true };
					boolean pattern2[] = { true, true, false, false };
					boolean patterntest = true;
					if (Arrays.equals(pattern1, temp.partner)) {
						patterntest = false;
					}
					if (Arrays.equals(pattern2, temp.partner)) {
						patterntest = false;
					}
					if (patterntest) {
						temp.name = "N" + number;
						number++;
						nodelist.add(temp);
						System.out.println(temp.toString());
					}
				}
			}

		}
	}
/**
 * Draws nods on the Game-Grid
 * @param bg {@link GGBackground} Object to draw
 */
	public void drawnode(GGBackground bg) {
		bg.setPaintColor(Color.red);
		bg.setFont(new Font("Arial", Font.BOLD, 10));
		for (node nods : nodelist) {

			bg.drawText(nods.name, new Point(nods.x * 30 + 15, nods.y * 30 + 15));

		}

	}
/**
 * Draws connection between Nodes
 * @param bg {@link GGBackground} Object to draw
 */
	public void drawconnection(GGBackground bg) {
		bg.setPaintColor(Color.red);
		bg.setFont(new Font("Arial", Font.BOLD, 10));
		for (node nods : nodelist) {

			for (way ways : nods.nodelist) {
				if (!(ways == null)) {
					bg.drawLine(nods.x * 30 + 15, nods.y * 30 + 15, ways.endpoint.x * 30 + 15,
							ways.endpoint.y * 30 + 15);
				}
			}

		}

	}
/**
 * Searches for a possible route thrue the Game-Grid
 * @param start Start Location
 * @param goal End Location
 * @return {@link Route}
 */
	public Route ASearch(Location start, Location goal) {

		Route result = new Route(start, goal);
		int intervall = 0;
		if (!simplereach(start, goal)) {
			Route devroute = result;
			ArrayList<Route> routedb = new ArrayList<Route>();
			boolean firstround = true;
			// Find nearest Node
			way nearest = null;
			int length = 1000000000;

			for (node nodes : nodelist) {
				int temp = simplelenghth(new Location(nodes.x, nodes.y), start)
						+ simplelenghth(new Location(nodes.x, nodes.y), goal) * 2;
				if ((temp < length) && simplereach(start, nodes.getlocation())) {
					nearest = new way(nodes, simplelenghth(start, new Location(nodes.x, nodes.y)));
					length = temp;
				}
				if (length == 0) {

					break;
				}
			}
			devroute.addway(nearest);

			routedb.add(devroute);
			ArrayList<blacknode> blacklist = new ArrayList<blacknode>();
			blacklist.add(new blacknode(devroute.lastnode()));
			boolean finished = false;
			while (!finished) {

				// develope route
				node lastnode = devroute.lastnode();
				for (int a = 0; a < 4; a++) {
					if (lastnode.partner[a]) {
						Route temproute = new Route(devroute);
						temproute.addway(lastnode.nodelist[a]);
						boolean block = false;
						for (blacknode blacknode : blacklist) {
							if (blacknode.getnode().equals(temproute.lastnode())) {
								if (blacknode.getcount() > blacklistcount) {
									block = true;

								}
								break;
							}
						}
						if (!block)
							routedb.add(temproute);
					}
				}

				// Select new devroute
				if (firstround) {
					routedb.remove(devroute);
					firstround = false;
				}
				Route min = routedb.get(0);
				for (Route route : routedb) {
					if (route.getscore() < min.getscore()) {
						min = route;
					}

				}

				devroute = min;
				routedb.remove(devroute);
				boolean isinit = false;
				for (blacknode blacknode : blacklist) {
					if (blacknode.getnode().equals(devroute.lastnode())) {
						isinit = true;
						blacknode.add();
						break;
					}

				}
				if (!isinit)
					blacklist.add(new blacknode(devroute.lastnode()));
				Aserchstats = "<Start " + start.toString() + "| Goal " + goal.toString() + "| " + intervall
						+ " Intervall| RouteDB Size " + routedb.size() + " | DEVROUTE STRING " + devroute.toString()
						+ ">";
				// check for reach

				if (devroute.lastnode().getlocation().toString().equals(goal.toString())) {
					result = devroute;
					finished = true;
				} else if (simplereach(devroute.lastnode().getlocation(), goal)) {
					devroute.addway(new way(new node(goal), simplelenghth(devroute.lastnode().getlocation(), goal)));
					finished = true;
					result = devroute;
				}
				intervall++;

			}

		} else {
			result.addway(new way(new node(goal), simplelenghth(start, goal)));
		}

		result.finishroute();
		
		return result;
	}
/**
 * Calculates direct length between start and goal location
 * @param start Start Location
 * @param goal End Location
 * @return Length to Block
 */
	public int simplelenghth(Location start, Location goal) {
		int luftlinie = 0;
		int xtemp = Math.abs(start.x - goal.x);
		int ytemp = Math.abs(start.y - goal.y);
		luftlinie = (int) Math.sqrt((xtemp * xtemp) + (ytemp * ytemp));
		return luftlinie;
	}
/**
 * Checks if Node can be reached without turning
 *  @param start Start Location
 * @param goal End Location
 * @return if possible
 */
	public boolean simplereach(Location start, Location goal) {
		int tempx = start.x - goal.x;
		int tempy = start.y - goal.y;
		if ((tempx) == 0 || (tempy) == 0) {
			boolean canmove = true;
			if (tempx == 0) {

				if (goal.y > start.y) {
					for (int a = start.y; a < goal.y; a++) {
						if (!movinglist[a][start.x]) {
							canmove = false;
							break;
						}
					}
				} else {
					for (int a = start.y; a > goal.y; a--) {
						if (!movinglist[a][start.x]) {
							canmove = false;
							break;
						}
					}
				}

			} else {
				if (goal.x > start.x) {
					for (int a = start.x; a < goal.x; a++) {
						if (!movinglist[start.y][a]) {
							canmove = false;
							break;
						}
					}
				} else {
					for (int a = start.x; a > goal.x; a--) {
						if (!movinglist[start.y][a]) {
							canmove = false;
							break;
						}
					}
				}

			}

			return canmove;
		} else {
			return false;
		}

	}
/**
 * Cleans drawn Route 
 */
	public void cleandrawn(GGBackground bg) {
		bg.restore();

	}
/**
 * Lets Actor walk along the Route
 * @param r Route to walk
 * @param actor Actor to move
 */
	public boolean walkroute(Route r, aiinterface actor) {
		Location loc=actor.getLocation();
		if (loc.toString().equals(r.getgoal().toString())) {
			return true;
		} else if (r.next().toString().equals(loc.toString())) {
			r.reachedcord();

		}
		CompassDirection c = loc.getCompassDirectionTo(r.next());

		if (c.equals(c.NORTH)) {
			actor.up();
		} else if (c.equals(c.SOUTH)) {
			actor.down();
		} else if (c.equals(c.EAST)) {
			actor.right();
		} else if (c.equals(c.WEST)) {
			actor.left();
		}

		return true;

	}
/**
 * @return Blocks that an actor can walk on 
 */
	public int getMovingblocks() {
		return movingblocks;
	}


	private void setMovingblocks(int movingblocks) {
		this.movingblocks = movingblocks;
	}
/**
 * Precalculates all possible Routes in the Game-Grid
 * @return A 4-D Route-Array where [Y-Start][X-Start][Y-Goal][X-Goal]
 */
	public Route[][][][] precalcallroutes() {
		Route[][][][] wtfmatrix = new Route[PacGrid.getnbver()][PacGrid.getnbhor()][PacGrid.getnbver()][PacGrid
				.getnbhor()];
		ArrayList<Route> routelist = new ArrayList<Route>();
		System.out.println("##############ROUTE PRE CALC##############");
		System.out.println("WARNING : CAN TAKE VERY LONG");
		int nubersofserches = (movingblocks * movingblocks) - movingblocks;
		System.out.println("Numbers of Operations : " + nubersofserches);

		Stopwatch stopwatch = new Stopwatch(1);
		for (int a = 0; a < 10; a++) {
			boolean same = true;
			while (same) {
				int avetempa = (int) (Math.random() * walklist.size());
				int avetempb = (int) (Math.random() * walklist.size());
				if (!(avetempa == avetempb)) {
					same = false;
					stopwatch.start();
					Location aveloca = walklist.get(avetempa);
					Location avelocb = walklist.get(avetempb);
					ASearch(aveloca, avelocb);
					stopwatch.stop();

				}
			}

		}
		int aveoften = stopwatch.getresult();
		System.out.println("Average Time Per 10 OPS : " + aveoften + " ms");
		double estimatettime = aveoften * (nubersofserches / 10);
		System.out.println("ESTIMATET TIME : " + estimatettime / 1000 + " sec");
		System.out.println("START OF CALCULATIONS");

		int calcintervall = movingblocks / 4;
		Thread calcthread1 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				int startat = calcintervall * 0;
				int endwith = calcintervall * 1;
				for (int a = startat; a <= endwith; a++) {
					Location loc1 = walklist.get(a);
					for (Location loc2 : walklist) {
						if (!loc1.toString().equals(loc2.toString())) {
							Route tempadd = ASearch(loc1, loc2);
							if (tempadd == null)
								System.out.println("Thread1 ERROR : NULL ROUTE " + loc1.toString() + loc2.toString());

							wtfmatrix[tempadd.getstart().y][tempadd.getstart().x][tempadd.getgoal().y][tempadd
									.getgoal().x] = tempadd;
							done++;
						}
					}
				}
				System.out.println("Thread 1 done");
			}
		});
		Thread calcthread2 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				int startat = calcintervall * 1;
				int endwith = calcintervall * 2;
				for (int a = startat; a <= endwith; a++) {
					Location loc1 = walklist.get(a);
					for (Location loc2 : walklist) {
						if (!loc1.toString().equals(loc2.toString())) {
							Route tempadd = ASearch(loc1, loc2);
							if (tempadd == null)
								System.out.println("Thread2 ERROR : NULL ROUTE " + loc1.toString() + loc2.toString());

							wtfmatrix[tempadd.getstart().y][tempadd.getstart().x][tempadd.getgoal().y][tempadd
									.getgoal().x] = tempadd;
							done++;
						}
					}
				}
				System.out.println("Thread 2 done");
			}
		});
		Thread calcthread3 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				int startat = calcintervall * 2;
				int endwith = calcintervall * 3;
				for (int a = startat; a <= endwith; a++) {
					Location loc1 = walklist.get(a);
					for (Location loc2 : walklist) {
						if (!loc1.toString().equals(loc2.toString())) {
							Route tempadd = ASearch(loc1, loc2);
							if (tempadd == null)
								System.out.println("Thread3 ERROR : NULL ROUTE " + loc1.toString() + loc2.toString());

							wtfmatrix[tempadd.getstart().y][tempadd.getstart().x][tempadd.getgoal().y][tempadd
									.getgoal().x] = tempadd;
							done++;
						}
					}
				}
				System.out.println("Thread 3 done");
			}
		});
		Thread calcthread4 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				int startat = calcintervall * 3;
				int endwith = calcintervall * 4;
				for (int a = startat; a <= endwith; a++) {
					Location loc1 = walklist.get(a);
					for (Location loc2 : walklist) {
						if (!loc1.toString().equals(loc2.toString())) {
							Route tempadd = ASearch(loc1, loc2);
							if (tempadd == null)
								System.out.println("Thread4 ERROR : NULL ROUTE " + loc1.toString() + loc2.toString());

							wtfmatrix[tempadd.getstart().y][tempadd.getstart().x][tempadd.getgoal().y][tempadd
									.getgoal().x] = tempadd;
							done++;
						}
					}
				}
				System.out.println("Thread 4 done");
			}
		});
		Thread percent = new Thread(new Runnable() {

			@Override
			public void run() {
				String tempold = "";
				int intervall = 0;
				while (done < nubersofserches) {
					try {
						Thread.sleep(1000);

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						return;
					}
					int percent = (done * 100 / nubersofserches);
					String temp = percent + "% done (" + done + " of " + nubersofserches + ")";
					if (!temp.equals(tempold)) {
						System.out.println(percent + "% done (" + done + " of " + nubersofserches
								+ ") THREADS STILL RUNNING(" + calcthread1.isAlive() + "|" + calcthread2.isAlive() + "|"
								+ calcthread3.isAlive() + "|" + calcthread4.isAlive() + ")");
						intervall = 0;
						tempold = temp;
					} else {
						intervall++;
					}
					if (intervall > 5) {
						System.out.println("#ASEARCH STUCK#");
						System.out.println("STATS " + Aserchstats);
					}
				}

			}
		});
		percent.start();
		calcthread1.start();
		calcthread2.start();
		calcthread3.start();
		calcthread4.start();
		while (calcthread1.isAlive() || calcthread2.isAlive() || calcthread3.isAlive() || calcthread4.isAlive()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		percent.interrupt();
		percent = null;
		int temp = 0;
		for (int dafuck = 0; dafuck < routelist.size(); dafuck++) {
			if (routelist.get(dafuck) == null) {
				temp++;
				routelist.remove(dafuck);
			}
		}
		System.out.println(temp + " GHOST ERRORS ?    Number of routs " + routelist.size());
		System.out.println("RE-Organising list into 4D Matrix");

		/*
		 * for(int a=0;a<PacGrid.getnbver();a++){ for(int
		 * b=0;a<PacGrid.getnbhor();b++){ for(Route rt:routelist){
		 * if(rt.getstart().equals(new Location(b, a))){
		 * wtfmatrix[a][b][rt.getgoal().y][rt.getgoal().x]=rt; } } } }
		 */

		System.out.println("##############DONE##############");

		return wtfmatrix;
	}
/**
 * @return A 2-D Boolean Array where [Y][X]
 * True == Actor can move
 * False == Actor can't move 
 */
	public boolean[][] getMovinglist() {
		return movinglist.clone();
	}

	
	private void setMovinglist(boolean[][] movinglist) {
		this.movinglist = movinglist;
	}
}
