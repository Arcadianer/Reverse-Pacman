package nav;

import java.util.ArrayList;

import ch.aplu.jgamegrid.Location;
/**
 *Node of Navigation network
 *Represents a Crossroad/Turn in the Game-Grid
 *Nodes connect to other nodes to represent a straight line Pacman can walk on
 */
public class node {
	/**
	 * Array of neighbor blocks if an actor can walk on it
	 * 0=North Neighbor block
	 * 1=South Neighbor block
	 * 2=East Neighbor block
	 * 3=West Neighbor block
	 */
	public boolean[] partner = new boolean[4];
	/**
	 * X-Coordinate of node 
	 */
	public int x = 0;
	/**
	 *Y-Coordinate of node
	 */
	public int y = 0;
	/**
	 * {@link way} objects to the next Node
	 */
	public way[] nodelist = new way[4];
	/**
	 * Name of the Node
	 */
	public String name="K?";

	public node(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public node(Location loc) {
		this.x = loc.x;
		this.y = loc.y;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String temp="{"+name+","+x+"|"+y+","+partner[0]+","+partner[1]+","+partner[2]+","+partner[3]+";"+nodelist[0]+","+nodelist[1]+","+nodelist[2]+","+nodelist[3]+"}";
		return temp;
	}
	/**
	 * Gets the location of the node
	 */
	public Location getlocation(){
		Location temp=new Location(this.x,this.y);
		return temp;
	}
	
	
}
