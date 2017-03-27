package tree;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.util.ArrayList;
import java.util.concurrent.atomic.LongAccumulator;

import block.nothing;
import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;
/**
 * Node for the Minimax Tree
 */
public class Tree_Node {
/**
 * {@link gamestate} of the Tree_Node
 */
	public gamestate state;
	/**
	 * Pacmans turn
	 */
	boolean pacmove = false;
	/**
	 * Parent {@link Tree_Node}
	 */
	public Tree_Node prev;
	/**
	 * Children {@link Tree_Node}
	 */
	public ArrayList<Tree_Node> next;
/**
 * Tree_Node constructor
 */
	public Tree_Node(gamestate state, boolean pacmove, Tree_Node prev) {

		this.state = state.clone();
		this.pacmove = pacmove;
		this.prev = prev;
	}

	public gamestate getState() {
		return state;
	}

	public void setState(gamestate state) {
		this.state = state;
	}

	public boolean isPacmove() {
		return pacmove;
	}

	public void setPacmove(boolean pacmove) {
		this.pacmove = pacmove;
	}
/**
 * Develops new Tree_Nodes
 */
	public void develop(int debth) {
		ArrayList<Tree_Node> branch = new ArrayList<Tree_Node>();

		if (pacmove) {

			gamestate newstate = state.clone();

			Location loc = newstate.pacloc;

			boolean[] validmove = { !newstate.gamegrid[loc.y - 1][loc.x].blockmovment,
					!newstate.gamegrid[loc.y + 1][loc.x].blockmovment,
					!newstate.gamegrid[loc.y][loc.x - 1].blockmovment,
					!newstate.gamegrid[loc.y][loc.x + 1].blockmovment };
			if (validmove[0]) {
				Location newloc = new Location(loc.x, loc.y - 1);
				newstate.pacloc = newloc;
				// pillpicsim
				if (newstate.gamegrid[newloc.y][newloc.x].name.equals("dot")) {
					newstate.gamegrid[newloc.y][newloc.x] = new nothing();
					newstate.pills++;
					newstate.pills_left--;
				}
				if (newstate.gamegrid[newloc.y][newloc.x].id.equals("p")) {
					newstate.gamegrid[newloc.y][newloc.x] = new nothing();
					newstate.Ppills++;
					newstate.ppill_remain--;
					newstate.pacpower = true;
				}
				if (winforghost(newloc, newstate.isPacpower(), newstate.getGhloc())) {
					newstate.setterminal(true);

				}
				if (pacwin(newstate.pills_left)) {
					newstate.setterminal(true);

				}
				newstate.pacwalked++;
				Tree_Node newnode = new Tree_Node(newstate, false, this);
				branch.add(newnode);

			}
			newstate = state.clone();
			if (validmove[1]) {
				Location newloc = new Location(loc.x, loc.y + 1);
				newstate.pacloc = newloc;
				// pillpicsim
				if (newstate.gamegrid[newloc.y][newloc.x].name.equals("dot")) {
					newstate.gamegrid[newloc.y][newloc.x] = new nothing();
					newstate.pills++;
					newstate.pills_left--;
				}
				if (newstate.gamegrid[newloc.y][newloc.x].id.equals("p")) {
					newstate.gamegrid[newloc.y][newloc.x] = new nothing();
					newstate.Ppills++;
					newstate.ppill_remain--;
					newstate.pacpower = true;
				}
				if (winforghost(newloc, newstate.isPacpower(), newstate.getGhloc())) {
					newstate.setterminal(true);

				}
				if (pacwin(newstate.pills_left)) {
					newstate.setterminal(true);

				}
				newstate.pacwalked++;
				Tree_Node newnode = new Tree_Node(newstate, false, this);
				branch.add(newnode);

			}
			newstate = state.clone();
			if (validmove[2]) {
				Location newloc = new Location(loc.x - 1, loc.y);
				newstate.pacloc = newloc;
				// pillpicsim
				if (newstate.gamegrid[newloc.y][newloc.x].name.equals("dot")) {
					newstate.gamegrid[newloc.y][newloc.x] = new nothing();
					newstate.pills++;
					newstate.pills_left--;
				}
				if (newstate.gamegrid[newloc.y][newloc.x].id.equals("p")) {
					newstate.gamegrid[newloc.y][newloc.x] = new nothing();
					newstate.Ppills++;
					newstate.ppill_remain--;
					newstate.pacpower = true;
				}
				if (winforghost(newloc, newstate.isPacpower(), newstate.getGhloc())) {
					newstate.setterminal(true);

				}
				if (pacwin(newstate.pills_left)) {
					newstate.setterminal(true);

				}
				newstate.pacwalked++;
				Tree_Node newnode = new Tree_Node(newstate, false, this);
				branch.add(newnode);

			}
			newstate = state.clone();
			if (validmove[3]) {
				Location newloc = new Location(loc.x + 1, loc.y);
				newstate.pacloc = newloc;
				// pillpicsim
				if (newstate.gamegrid[newloc.y][newloc.x].name.equals("dot")) {
					newstate.gamegrid[newloc.y][newloc.x] = new nothing();
					newstate.pills++;
					newstate.pills_left--;
				}
				if (newstate.gamegrid[newloc.y][newloc.x].id.equals("p")) {
					newstate.gamegrid[newloc.y][newloc.x] = new nothing();
					newstate.Ppills++;
					newstate.ppill_remain--;
					newstate.pacpower = true;
				}
				if (winforghost(newloc, newstate.isPacpower(), newstate.getGhloc())) {
					newstate.setterminal(true);

				}
				if (pacwin(newstate.pills_left)) {
					newstate.setterminal(true);

				}
				newstate.pacwalked++;
				Tree_Node newnode = new Tree_Node(newstate, false, this);
				branch.add(newnode);

			}

		} else {
			gamestate newstate = state.clone();
			ArrayList<Location> ghlocopy = (ArrayList<Location>) newstate.ghloc.clone();
			int devdebth = newstate.ghloc.size();
			int currentdebth = debth;
			boolean debthreached = (devdebth == currentdebth);

			Location[] ghlocarr = new Location[newstate.ghloc.size()];
			if (newstate.ghloc.size() > 0) {
				for (int add = 0; add < newstate.ghloc.size(); add++) {
					ghlocarr[add] = newstate.ghloc.get(add);
				}

				// GHOST 1
				Location loc = ghlocarr[currentdebth - 1];
				boolean[] validmove = { !newstate.gamegrid[loc.y - 1][loc.x].blockmovment,
						!newstate.gamegrid[loc.y + 1][loc.x].blockmovment,
						!newstate.gamegrid[loc.y][loc.x - 1].blockmovment,
						!newstate.gamegrid[loc.y][loc.x + 1].blockmovment };

				if (validmove[0]) {
					Location newloc = new Location(loc.x, loc.y - 1);
					ghlocarr[currentdebth - 1] = newloc;
					// pillpicsim

					if (winforghost(newstate.pacloc, newstate.isPacpower(), arraytolist(ghlocarr))) {
						newstate.setterminal(true);
						
					}
					if (pacwin(newstate.pills_left)) {
						newstate.setterminal(true);
						
					}
					newstate.ghloc = arraytolist(ghlocarr);
					Tree_Node newnode = new Tree_Node(newstate, debthreached, this);
					branch.add(newnode);

				}
				newstate = state.clone();
				if (validmove[1]) {
					Location newloc = new Location(loc.x, loc.y + 1);
					ghlocarr[currentdebth - 1] = newloc;
					// pillpicsim

					if (winforghost(newstate.pacloc, newstate.isPacpower(), arraytolist(ghlocarr))) {
						newstate.setterminal(true);
						
					}
					if (pacwin(newstate.pills_left)) {
						newstate.setterminal(true);
						
					}
					newstate.ghloc = arraytolist(ghlocarr);
					Tree_Node newnode = new Tree_Node(newstate, debthreached, this);
					branch.add(newnode);
				}
				newstate = state.clone();
				if (validmove[2]) {
					Location newloc = new Location(loc.x - 1, loc.y);
					ghlocarr[currentdebth - 1] = newloc;
					// pillpicsim

					if (winforghost(newstate.pacloc, newstate.isPacpower(), arraytolist(ghlocarr))) {
						newstate.setterminal(true);
						
					}
					if (pacwin(newstate.pills_left)) {
						newstate.setterminal(true);
						
					}
					newstate.ghloc = arraytolist(ghlocarr);
					Tree_Node newnode = new Tree_Node(newstate, debthreached, this);
					branch.add(newnode);

				}
				newstate = state.clone();
				if (validmove[3]) {
					Location newloc = new Location(loc.x + 1, loc.y);
					ghlocarr[currentdebth - 1] = newloc;
					// pillpicsim

					if (winforghost(newstate.pacloc, newstate.isPacpower(), arraytolist(ghlocarr))) {
						newstate.setterminal(true);
						
					}
					if (pacwin(newstate.pills_left)) {
						newstate.setterminal(true);
						
					}
					newstate.ghloc = arraytolist(ghlocarr);
					Tree_Node newnode = new Tree_Node(newstate, debthreached, this);
					branch.add(newnode);
				}
				this.next = branch;
				if (!debthreached) {
					currentdebth++;
					for (Tree_Node tn : branch) {
						tn.develop(currentdebth);
					}
				} else {
					for (Tree_Node tn : branch) {
						Gaertner.endghostnode.add(tn);
					}
				}
			}
		}

		this.next = branch;
		Gaertner.devcount++;
	}
/**
 * Checks if Ghost win
 * @param pacloc Pacman Location
 * @param power Pacman Power
 * @param playerlist List of Ghost Locations
 * @return if ghost win
 */
	public boolean winforghost(Location pacloc, boolean power, ArrayList<Location> playerlist) {
		boolean result = false;
		if (power == false) {

			for (Location loc : playerlist) {
				if (loc.equals(pacloc)) {
					result = true;

				}
			}
		} else {
			ArrayList<Location> delllist = new ArrayList<Location>();
			for (Location loc : playerlist) {
				if (loc.equals(pacloc)) {
					delllist.add(loc);

				}
			}
			for (Location location : delllist) {
				playerlist.remove(location);
			}
		}

		return result;
	}
/**
 * Checks if Pacman Wins
 */
	public boolean pacwin(int pills_left) {

		if (pills_left == 0) {
			return true;
		}
		return false;
	}

	public ArrayList<Location> arraytolist(Location[] arr) {
		ArrayList<Location> list = new ArrayList<Location>();
		for (Location loc : arr) {
			list.add(new Location(loc.x, loc.y));
		}
		return list;

	}

	public Location[] listtoarray(ArrayList<Location> ahloc) {
		Location[] ghlocarr = new Location[ahloc.size()];
		for (int add = 0; add < ahloc.size(); add++) {
			ghlocarr[add] = ahloc.get(add);
		}
		return ghlocarr;
	}
}
