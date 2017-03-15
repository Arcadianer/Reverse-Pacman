
// Ghost.java
// Used for PacMan

import ch.aplu.jgamegrid.*;
import ch.aplu.jgamegrid.Location.CompassDirection;
import nav.Route;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.*;

import javax.swing.plaf.basic.BasicBorders.MarginBorder;

import scala.annotation.meta.setter;
import block.block;
/**
 * Ghost Class for the Ghost Actor
 */
public class Ghostplayer extends Actor implements Bridgehandeler, aiinterface {
	private PacMan pacMan;
	private int type;
	private connection con;
	int movecode = 5;
	String message = "5";
	String nextstring = "5";
	private ArrayList<Location> visitedList = new ArrayList<Location>();
	private final int listLength = 10;
	private Navigation navi;
	private Route ghroutr;
	private int stepsize = 0;
	private Location lastloc;

	public Ghostplayer(PacMan pacMan, int type, Navigation navi) {
		super("sprites/ghost_" + (type - 1) + ".gif");
		this.pacMan = pacMan;
		this.type = type;
		this.con = con;
		this.navi = navi;
	}
/**
 * 
 * @see Bridgehandeler#setcon(connection)
 */
	public void setcon(connection con) {
		this.con = con;
	}
/**
 * 
 * @see Bridgehandeler#getcon()
 */
	public connection getcon() {
		return this.con;
	}
	/**
	 * ACT Method
	 * In case of Ghostki : {@link Ghostplayer} follows move strategy 
	 * In case of Player : Lets player controll over Ghost
	 */
	public void act() {
		if (pacMan.ghostki) {
			newmoveStrategy();
		} else {
			Location next = null;
			movecode = Integer.decode(nextstring);

			switch (movecode) {
			case 1:
				next = getLocation().getNeighbourLocation(Location.WEST);
				if (canMove(next))
					message = "1";
				break;
			case 2:
				next = getLocation().getNeighbourLocation(Location.NORTH);
				if (canMove(next))
					message = "2";
				break;
			case 3:
				next = getLocation().getNeighbourLocation(Location.EAST);
				if (canMove(next))
					message = "3";
				break;
			case 4:
				next = getLocation().getNeighbourLocation(Location.SOUTH);
				if (canMove(next))
					message = "4";
				break;
			case 5:
				message = "5";
				break;
			}

			if (!(message == null) && !message.equals(null)) {
				movecode = Integer.decode(message);
				switch (movecode) {
				case 1:
					next = getLocation().getNeighbourLocation(Location.WEST);
					setDirection(Location.WEST);
					break;
				case 2:
					next = getLocation().getNeighbourLocation(Location.NORTH);
					setDirection(Location.NORTH);
					break;
				case 3:
					next = getLocation().getNeighbourLocation(Location.EAST);
					setDirection(Location.EAST);
					break;
				case 4:
					next = getLocation().getNeighbourLocation(Location.SOUTH);
					setDirection(Location.SOUTH);
					break;
				case 5:
					next = null;
					break;
				}
			}

			if (next != null && canMove(next)) {
				setLocation(next);

			}
		}
		if (getDirection() > 150 && getDirection() < 210)
			setHorzMirror(false);
		else
			setHorzMirror(true);

	}

	private boolean canMove(Location location) {
		block temp = PacGrid.maze[location.y][location.x];
		if (temp.blockmovment) {
			return false;
		} else {
			return true;
		}
	}

	private void moveStrategy() {
		if (!pacMan.ghostdumm) {
			Location pacLocation = pacMan.pacActor.getLocation();
			double oldDirection = getDirection();

			// Use an approach strategy:
			// Determine direction to pacActor and try to move in
			// this direction. To avoid looping do not apply the
			// strategy if location has been recently visited
			Location.CompassDirection compassDir = getLocation().get4CompassDirectionTo(pacLocation);
			Location next = getLocation().getNeighbourLocation(compassDir);
			setDirection(compassDir);
			if (!isVisited(next) && canMove(next)) {
				move();
			} else {
				// normal movement
				int sign = Math.random() < 0.5 ? 1 : -1;
				setDirection(oldDirection);
				turn(sign * 90); // Try to turn left/right
				next = getNextMoveLocation();
				if (canMove(next)) {
					move();
				} else {
					setDirection(oldDirection);
					next = getNextMoveLocation();
					if (canMove(next)) // Try to move forward
					{
						move();
					} else {
						setDirection(oldDirection);
						turn(-sign * 90); // Try to turn right/left
						next = getNextMoveLocation();
						if (canMove(next)) {
							move();
						} else {

							setDirection(oldDirection);
							turn(180); // Turn backward
							next = getNextMoveLocation();
							move();
						}
					}
				}
			}
			addVisitedList(next);
		}
	}

	private void newmoveStrategy() {
		if (!pacMan.ghostdumm) {
			try {
				
			
			double randomornot = Math.random() * 100;
			if (randomornot < 90) {

				//if (KIData.pacpower == false) {
					if (stepsize == 0) {
						Location pacLocation = pacMan.pacActor.getLocation();
						ghroutr = navi.ASearch(getLocation(), pacLocation);
						navi.walkroute(ghroutr, this);
					} else if (stepsize >= 1) {
						navi.walkroute(ghroutr, this);
					}

					stepsize++;
					if (stepsize == 4) {
						stepsize = 0;
				//	}
				} /*else {
					if (stepsize == 0) {
						double lenghth = 0.0;
						Location fathest = null;
						boolean[][] movinglist = navi.getMovinglist();

						for (int y = 0; movinglist.length > y; y++) {
							for (int x = 0; movinglist[y].length > x; x++) {
								if (movinglist[y][x]) {
									double templength = navi.simplelenghth(KIData.pacloc, new Location(x, y));
									if (templength > lenghth) {
										lenghth = templength;
										fathest = new Location(x, y);
									}
								}
							}

						}

						Location pacLocation = pacMan.pacActor.getLocation();
						ghroutr = navi.ASearch(getLocation(), fathest);
						navi.walkroute(ghroutr, this);
					} else if (stepsize >= 1) {
						navi.walkroute(ghroutr, this);
					}

					stepsize++;
					if (stepsize == 4) {
						stepsize = 0;
					}

				}*/

			} else {

				if(canMove(getNextMoveLocation())){
					move();
				}

			}
			lastloc=getLocation();
			} catch (Exception e) {
				stepsize=0;
				e.printStackTrace();
				setLocation(lastloc);
			}
		}
	}

	private void random() {
		boolean movealowd = false;
		while (movealowd) {
			int walkto = (int) (Math.random() * 3);
			Location gh = getLocation();
			switch (walkto) {
			case 0:

				setDirection(CompassDirection.NORTH);
				gh = gh.getNeighbourLocation(CompassDirection.NORTH);
				break;
			case 1:

				setDirection(CompassDirection.SOUTH);
				gh = gh.getNeighbourLocation(CompassDirection.SOUTH);
				break;
			case 2:
				setDirection(CompassDirection.EAST);
				gh = gh.getNeighbourLocation(CompassDirection.EAST);
				break;
			case 3:
				setDirection(CompassDirection.WEST);
				gh = gh.getNeighbourLocation(CompassDirection.WEST);
				break;

			}
			if (canMove(gh)) {
				movealowd = true;
			}
		}
		move();

	}

	private void addVisitedList(Location location) {
		visitedList.add(location);
		if (visitedList.size() == listLength)
			visitedList.remove(0);
	}

	private boolean isVisited(Location location) {
		for (Location loc : visitedList)
			if (loc.equals(location))
				return true;
		return false;
	}
/**
 * 
 * @see Bridgehandeler#messagereceived(java.lang.String)
 */
	@Override
	public void messagereceived(String message) {
		// TODO Auto-generated method stub
		try {
			Integer.decode(message);

			this.nextstring = message;
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
/**
 * 
 * @see aiinterface#up()
 */
	@Override
	public void up() {
		// TODO Auto-generated method stub
		Location temp = getLocation().getNeighbourLocation(Location.NORTH);
		setDirection(Location.NORTH);
		setLocation(temp);
	}
	/**
	 * 
	 * @see aiinterface#down()
	 */
	@Override
	public void down() {
		// TODO Auto-generated method stub
		Location temp = getLocation().getNeighbourLocation(Location.SOUTH);
		setDirection(Location.SOUTH);
		setLocation(temp);
	}
/**
 * 
 * @see aiinterface#left()
 */
	@Override
	public void left() {
		// TODO Auto-generated method stub
		Location temp = getLocation().getNeighbourLocation(Location.WEST);
		setDirection(Location.WEST);
		setLocation(temp);
	}

	/**
	 * 
	 * @see aiinterface#right()
	 */
	@Override
	public void right() {
		// TODO Auto-generated method stub
		Location temp = getLocation().getNeighbourLocation(Location.EAST);
		setDirection(Location.EAST);
		setLocation(temp);
	}
/**
 * 
 * @see aiinterface#getpower()
 */
	@Override
	public boolean getpower() {
		// TODO Auto-generated method stub
		return false;
	}
/**
 * 
 * @see aiinterface#getpills()
 */
	@Override
	public int getpills() {
		// TODO Auto-generated method stub
		return 0;
	}

/**
 * 
 * @see aiinterface#getPpills()
 */
	@Override
	public int getPpills() {
		// TODO Auto-generated method stub
		return 0;
	}
/**
 * 
 * @see aiinterface#eatPill(ch.aplu.jgamegrid.Location)
 */
	@Override
	public void eatPill(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public CompassDirection getlastmove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getwalked() {
		// TODO Auto-generated method stub
		return 0;
	}
}
