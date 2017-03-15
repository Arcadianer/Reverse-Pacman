package nav;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.PackedColorModel;
import java.util.ArrayList;

import block.nothing;
import ch.aplu.jgamegrid.GGBackground;
import ch.aplu.jgamegrid.Location;
import ch.aplu.jgamegrid.Location.CompassDirection;
/**
 *A collection of Location / Waypoints that make up a route
 */
public class Route {
	/**
	 * Way score for A*Search
	 */
	int score = 0;
	/**
	 * AIR-Way Score for A*Search
	 */
	int airscore = 0;
	/**
	 * Arraylist of ways to walk
	 */
	
	ArrayList<way> waylist = new ArrayList<way>();
	private ArrayList<Location> cordlist = new ArrayList<Location>();
	/**
	 * List of Locations on the list
	 */
	ArrayList<Location> routelist = new ArrayList<Location>();
	/**
	 * Start Location of Route
	 */
	Location start;
	/**
	 * End Location of Route
	 */
	Location goal;
/**
 * Constructor of Route
 * @param start Start location
 * @param goal Goal of Route
 */
	public Route(Location start, Location goal) {
		this.getCordlist().add(start);
		this.start = start;
		this.goal = goal;
		this.airscore = airline();
		calc(goal);
	}
/**
 *  Constructor of Route
 *  Clones Route
 *  @param lastroute Route to clone
 */
	public Route(Route lastroute) {
		this.setCordlist((ArrayList<Location>) lastroute.getCordlist().clone());
		this.start = lastroute.start.clone();
		this.goal = lastroute.goal.clone();
		this.waylist = (ArrayList<way>) lastroute.waylist.clone();
		this.airscore = airline();
		calc(goal);
	}
/**
 * Adds {@link way}-object to the waypointlist
 * @param newway {@link way} to add 
 */
	public void addway(way newway) {
		getCordlist().add(new Location(newway.endpoint.x, newway.endpoint.y));
		waylist.add(newway);
		calc(goal);
	}
/**
 * calculates total Route score
 */
	public void calc(Location goal) {
		int luftlinie = 0;
		int xtemp = Math.abs(getCordlist().get(getCordlist().size() - 1).x - goal.x);
		int ytemp = Math.abs(getCordlist().get(getCordlist().size() - 1).y - goal.y);
		luftlinie = (int) Math.sqrt((xtemp * xtemp) + (ytemp * ytemp));
		int wayscore = 0;
		for (way ways : waylist) {
			wayscore = wayscore + ways.length;
		}
		score = luftlinie * 2 + wayscore;
	}
	/**
	 * @return score
	 */
	public int getscore() {
		return score;
	}
/**
 * Checks if a node is in the Route 
 * @param nd node to check
 * @return if node is on contained in the Route
 */
	public boolean containsnode(node nd) {
		for (way wy : waylist) {
			if(wy.endpoint.equals(nd))
				return true;
		}

		return false;

	}
/**
 * Finilize the route 
 */
	public void finishroute() {
		
	while((cordlist.size()>0)&&cordlist.get(0).equals(start))
		cordlist.remove(0);
		routelist = (ArrayList<Location>) getCordlist().clone();
	}
/**
 * Next Coordinate
 * @return gets next Location
 */
	public Location next() {
		return routelist.get(0);
	}
/**
 * Removes last Location
 */
	public void reachedcord() {
		routelist.remove(0);
	}
/**
 * Gets last Node
 * @return last Node in list
 */
	public node lastnode() {

		return waylist.get(waylist.size() - 1).endpoint;

	}
/**
 * @return Goal
 */
	public Location getgoal() {
		return goal;
	}
	/**
	 * @return start
	 */
	public Location getstart() {
		return start;
	}
/**
 * Calculates Air Score
 * @return airline score
 */
	public int airline() {
		int luftlinie = 0;
		int xtemp = Math.abs(start.x - goal.x);
		int ytemp = Math.abs(start.y - goal.y);
		luftlinie = (int) Math.sqrt((xtemp * xtemp) + (ytemp * ytemp));
		airscore = luftlinie;
		return luftlinie;
	}
/**
 * Drawes Route on the Game-Grid
 * @param bg {@link GGBackground} object required to draw
 */
	public void drawroute(GGBackground bg) {
		
		bg.save();
		bg.setPaintColor(Color.green);
		Location last = getCordlist().get(0);
		bg.drawCircle(new Point(start.x * 30 + 15, start.y * 30 + 15), 15);
		bg.setPaintColor(Color.red);
		bg.drawCircle(new Point(goal.x * 30 + 15, goal.y * 30 + 15), 15);
		bg.setPaintColor(Color.green);
		for (int a = 1; a < getCordlist().size(); a++) {
			bg.drawLine(getCordlist().get(a).x * 30 + 15, getCordlist().get(a).y * 30 + 15, last.x * 30 + 15,
					last.y * 30 + 15);
			last = getCordlist().get(a);
		}
	}
/**
 *Removes drawn Route  
 * @param bg {@link GGBackground} object required to draw
 */
	public void cleanroute(GGBackground bg) {
		Location last = getCordlist().get(0);
		for (int a = 1; a < getCordlist().size(); a++) {
			new nothing().draw(last, bg);

			last = getCordlist().get(a);
			cleanline(getCordlist().get(a - 1), last, bg);
		}
	}

	@Override
	public String toString() {
		String temp = "{";
		for (way ways : waylist) {
			temp = temp + ways.endpoint.name + "|";
		}
		temp = temp + score + "}";

		return temp;

	}
/**
 *@see java.lang.Object#clone()
 */
	public Route clone() {
		Route temp = new Route(start, goal);
		temp.setCordlist((ArrayList<Location>) getCordlist().clone());
		temp.waylist = (ArrayList<way>) waylist.clone();
		temp.calc(goal);
		return temp;
	}
/**
 * @return get Cordlist
 */
	public ArrayList<Location> getCordlist() {
		return cordlist;
	}
/**
 * @param cordlist list to set
 */
	public void setCordlist(ArrayList<Location> cordlist) {
		this.cordlist = cordlist;
	}
/**
 * @param score score to set
 */
	public void setScore(int score) {
		this.score = score;
	}
/**
 * @param temp Airscore temp
 */
	public void setAirscore(int temp) {
		this.airscore = temp;
	}
/**
 * Cleans line 
 */
	public void cleanline(Location linestart, Location lineend, GGBackground bg) {

		int tempx = linestart.x - lineend.x;
		int tempy = linestart.y - lineend.y;
		if ((tempx) == 0 || (tempy) == 0) {

			if (tempx == 0) {

				if (lineend.y > linestart.y) {
					for (int a = linestart.y; a < lineend.y; a++) {
						Location temp = new Location(lineend.x, a);
						new nothing().draw(temp, bg);
					}
				} else {
					for (int a = linestart.y; a > lineend.y; a--) {
						Location temp = new Location(lineend.x, a);
						new nothing().draw(temp, bg);
					}
				}

			} else {
				if (lineend.x > linestart.x) {
					for (int a = linestart.x; a < lineend.x; a++) {
						Location temp = new Location(a, lineend.y);
						new nothing().draw(temp, bg);
					}
				} else {
					for (int a = linestart.x; a > lineend.x; a--) {
						Location temp = new Location(a, lineend.y);
						new nothing().draw(temp, bg);
					}
				}

			}

		}

	}
	/**
	 * Gets neighbor block in direction of next Location
	 * @param a Current location
	 * @return neighbor block in direction of next Location
	 */
	public Location getnextcord(Location a){
		Location next=new Location(a);
		CompassDirection c = a.getCompassDirectionTo(next());

		if (c.equals(c.NORTH)) {
			next=new Location(a.x,a.y-1);
		} else if (c.equals(c.SOUTH)) {
			next=new Location(a.x,a.y+1);
		} else if (c.equals(c.EAST)) {
			next=new Location(a.x+1,a.y);
		} else if (c.equals(c.WEST)) {
			next=new Location(a.x-1,a.y);
		}
		return next;
	}

}
