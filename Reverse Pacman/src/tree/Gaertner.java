package tree;

import java.util.ArrayList;

import javax.swing.text.NavigationFilter;
import javax.swing.text.AbstractDocument.LeafElement;
import javax.swing.text.html.HTMLDocument.HTMLReader.ParagraphAction;

import block.block;
import ch.aplu.jgamegrid.Location;

/**
 * Generate decision tree
 */
public class Gaertner {
	public static int tree_debth = 2;
	public gamestate groot;
	public Tree_Node root;
	public static boolean Zappelstop = false;
	public static ArrayList<Tree_Node> endghostnode;
	public ArrayList<Tree_Node> leaves;
	public static int devcount = 0;
	public static ArrayList<Location> block = null;
	public static boolean zeroghost = false;

	public Gaertner(gamestate groot, boolean zeroghost) {
		this.groot = groot;
		this.root = new Tree_Node(groot, true, null);
		this.zeroghost = zeroghost;
	}

	/**
	 * Creates a decision tree
	 * 
	 * @return Number of Terminal Stats
	 */
	public int maketree() {
		devcount = 0;
		root.develop(0);
		leaves = new ArrayList<Tree_Node>();
		for (int a = 0; a < root.next.size(); a++) {

			develop_further(root.next.get(a));

		}

		int temp = leaves.size();

		return temp;
	}

	/**
	 * Counts the debth of the decision tree
	 */
	public int count_debth(Tree_Node tn) {
		int debth = 0;
		Tree_Node current = tn;
		while (!(current.prev == null)) {
			if (current.pacmove) {
				debth++;
			}
			current = current.prev;
		}

		return debth;
	}

	/**
	 * Simulates gamestate further Method calls itself recursive
	 */
	public void develop_further(Tree_Node tn) {
		if (count_debth(tn) < tree_debth) {
			// Pacman develop
			if (tn.pacmove) {
				tn.develop(0);
				for (int a = 0; a < tn.next.size(); a++) {
					develop_further(tn.next.get(a));
				}
			} else {
				// Ghost develop
				endghostnode = new ArrayList<Tree_Node>();
				endghostnode.clear();
				tn.develop(1);
				for (Tree_Node gn : endghostnode) {
					develop_further(gn);
				}

				endghostnode = null;
			}

		} else {
			if (tn.next == null) {
				leaves.add(tn);
			}

		}
	}

	/**
	 * Get all Gamestates from the same Parent
	 */
	public ArrayList<Tree_Node> getpartners(Tree_Node tn) {

		Tree_Node parent = tn.prev;

		return parent.next;

	}

	/**
	 * Evaluates gamestate and set utility
	 * 
	 * @param gs
	 *            Gamestate to evaluate
	 * @return utility
	 */
	public static double evalution_function(gamestate gs) {
		// DEBUG FUNCTION
		double result = 0;

		// nearestpill
		ArrayList<Location> pilllist = getpills(gs.gamegrid);

		double dis = 100000000.0;
		for (Location location : pilllist) {
			double temp = simplelenghth(gs.pacloc, location);
			if (temp < dis) {
				dis = temp;
			}
		}
		double pilldis = 0;
		if (!Zappelstop) {
			pilldis = 5.0 / dis;
		} else {
			pilldis = 10.0 / dis;
		}
		/*
		 * if(pilldistancerandome){ pilldis=-(Math.random()*100); }
		 */
		gs.setPilldistance(pilldis);
		result = result + (pilldis);

		// PP DIS
		ArrayList<Location> ppilllist = getpp(gs.gamegrid);

		double pdis = 100000000.0;
		for (Location location : ppilllist) {
			double temp = simplelenghth(gs.pacloc, location);
			if (temp < pdis) {
				pdis = temp;
			}
		}
		double ppilldis = 10.0 / pdis;
		/*
		 * if(pilldistancerandome){ pilldis=-(Math.random()*100); }
		 */

		gs.setPpilldistance(ppilldis);
		result = result + (ppilldis);

		double ghdisscore = 0;

		if (!gs.isPacpower()) {
			for (Location ghloc : gs.ghloc) {

				double temp = simplelenghth(gs.pacloc, ghloc);

				if (temp <= 3) {

					ghdisscore = ghdisscore + (-20.0 / (temp * temp * temp * temp));
				} else {
					ghdisscore = ghdisscore + (-5.0 / temp);
				}

			}
		} else {

			for (Location ghloc : gs.ghloc) {

				double temp = simplelenghth(gs.pacloc, ghloc);
				if (temp == 0) {
					ghdisscore = ghdisscore + 50.0;
				} else if (temp <= 3) {

					ghdisscore = ghdisscore + (20.0 / (temp * temp * temp ));
				} else {
					ghdisscore = ghdisscore + (5.0 / temp);
				}

			}
		}
		if (!zeroghost) {
			result = result + ghdisscore;
			gs.setGhost_k(ghdisscore);
		}
		// pill remain
		// gs.setPill_remain((-4.0 * gs.pills_left));
		// result = result + (-4.0 * gs.pills_left);
		// pp left
		// gs.setPpill_remain((-19.0 * gs.ppill_remain));
		// result = result + (-19.0 * gs.ppill_remain);
		// pilleaten
		if (!Zappelstop) {
			gs.setPillscore(gs.pills * 6);
			result = result + gs.pills * 6;
		} else {
			gs.setPillscore(gs.pills * 11);
			result = result + gs.pills * 11;
		}

		// result=result-gs.pacwalked*1;
		gs.setPpillscore(gs.Ppills * 10);
		result = result + gs.Ppills * 10;

		return result;
	}

	/**
	 * Calculates direct length between start and goal location
	 * 
	 * @param start
	 *            Start Location
	 * @param goal
	 *            End Location
	 * @return Length to Block
	 */
	public static double simplelenghth(Location start, Location goal) {
		double luftlinie = 0;
		double xtemp = Math.abs(start.x - goal.x);
		double ytemp = Math.abs(start.y - goal.y);
		luftlinie = Math.sqrt((xtemp * xtemp) + (ytemp * ytemp));
		return luftlinie;
	}

	/**
	 * Gets all pills and adds them to the pilllist
	 * 
	 * @return pilllist of pill location list
	 */
	public static ArrayList<Location> getpills(block[][] maze) {
		ArrayList<Location> pilllist = new ArrayList<Location>();
		for (int y = 0; y < maze.length; y++) {
			for (int x = 0; x < maze[0].length; x++) {
				block bl = maze[y][x];
				if (bl.id.equals(".")) {
					pilllist.add(new Location(x, y));
				}
			}
		}
		return pilllist;
	}

	/**
	 * Get all Powerpills
	 * 
	 * @return Arraylist of PP Location
	 */
	public static ArrayList<Location> getpp(block[][] maze) {
		ArrayList<Location> pilllist = new ArrayList<Location>();
		for (int y = 0; y < maze.length; y++) {
			for (int x = 0; x < maze[0].length; x++) {
				block bl = maze[y][x];
				if (bl.id.equals("p")) {
					pilllist.add(new Location(x, y));
				}
			}
		}
		return pilllist;
	}

	/**
	 * Checks if ghost won
	 * 
	 * @return true if ghost won
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
	 * Checks if Pacman wins
	 * 
	 * @return true if Pacman has won
	 */
	public boolean pacwin(int pills_left, ArrayList<Location> playerlist) {

		if (pills_left == 0) {
			return true;
		}

		if (!zeroghost && playerlist.size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Performs MIN-MAX Algorithm (SEE DOCUMENTATION FOR DETAILS)
	 */
	public Tree_Node minmax() {
		ArrayList<Tree_Node> parentlist = new ArrayList<Tree_Node>();
		for (Tree_Node tree_Node : leaves) {
			double tump = evalution_function(tree_Node.state);
			tree_Node.state.setUtillity(tump);
			if (winforghost(tree_Node.getState().pacloc, tree_Node.getState().pacpower,
					tree_Node.getState().getGhloc())) {
				tree_Node.getState().setterminal(true);
				tree_Node.getState().setUtillity(-1000.0);
			}
			if (pacwin(tree_Node.getState().getPills_left(), tree_Node.getState().getGhloc())) {
				tree_Node.getState().setterminal(true);
				tree_Node.getState().setUtillity(100);
			}
		}
		while (leaves.size() > 0) {
			Tree_Node firstbrother = leaves.get(0);
			Tree_Node parent = firstbrother.prev;
			if (!(parent == null)) {
				ArrayList<Tree_Node> brothers = getpartners(firstbrother);

				parentlist.add(parent);
				Tree_Node extrem = null;

				// max
				if (parent.pacmove) {
					Tree_Node max = brothers.get(0);
					for (Tree_Node tn : brothers) {
						if (tn.getState().getUtillity() > max.getState().getUtillity()) {
							max = tn;
						}
						leaves.remove(tn);
					}
					extrem = max;
				} else {

					// min

					Tree_Node min = brothers.get(0);
					for (Tree_Node tn : brothers) {
						if (tn.getState().getUtillity() < min.getState().getUtillity()) {
							min = tn;
						}
						leaves.remove(tn);
					}
					extrem = min;
				}
				parent.getState().setUtillity(extrem.getState().getUtillity());
				parent.getState().setGhost_k(extrem.getState().ghost_k);
				parent.getState().setPilldistance(extrem.getState().pilldistance);
				parent.getState().setPill_remain(extrem.getState().pill_remain);
				parent.getState().setPillscore(extrem.getState().pillscore);
				parent.getState().setPpill_remain(extrem.getState().getPpill_remain());
				parent.getState().setPpilldistance(extrem.getState().getPpilldistance());
				parent.getState().setPpillscore(extrem.getState().getPpillscore());
			} else {
				leaves.clear();
			}

		}
		// PARTENT UP
		while (!parentlist.contains(root)) {
			leaves = (ArrayList<Tree_Node>) parentlist.clone();
			parentlist.clear();
			while (leaves.size() > 0) {
				Tree_Node firstbrother = leaves.get(0);
				Tree_Node parent = firstbrother.prev;
				if (!(parent == null)) {

					ArrayList<Tree_Node> brothers = getpartners(firstbrother);

					parentlist.add(parent);
					Tree_Node extrem = null;

					// max
					if (parent.pacmove) {
						Tree_Node max = brothers.get(0);
						for (Tree_Node tn : brothers) {
							if (tn.getState().getUtillity() > max.getState().getUtillity()) {
								max = tn;
							}
							leaves.remove(tn);
						}
						extrem = max;
					} else {

						// min

						Tree_Node min = brothers.get(0);
						for (Tree_Node tn : brothers) {
							if (tn.getState().getUtillity() < min.getState().getUtillity()) {
								min = tn;
							}
							leaves.remove(tn);
						}
						extrem = min;
					}
					parent.getState().setUtillity(extrem.getState().getUtillity());
					parent.getState().setGhost_k(extrem.getState().ghost_k);
					parent.getState().setPilldistance(extrem.getState().pilldistance);
					parent.getState().setPill_remain(extrem.getState().pill_remain);
					parent.getState().setPillscore(extrem.getState().pillscore);
					parent.getState().setPpill_remain(extrem.getState().getPpill_remain());
					parent.getState().setPpilldistance(extrem.getState().getPpilldistance());
					parent.getState().setPpillscore(extrem.getState().getPpillscore());
				} else {
					leaves.clear();
				}
			}
		}

		Tree_Node result = null;

		if (!(block == null)) {
			for (Location blockloc : block) {
				for (Tree_Node tn : root.next) {
					if (tn.getState().getPacloc().toString().equals(blockloc.toString())) {
						tn.getState().setUtillity(-100000.0);
					}
				}
			}

		}

		if (root.next.size() > 0) {
			Tree_Node max = root.next.get(0);
			for (Tree_Node tn : root.next) {

				if (tn.getState().utillity > max.getState().getUtillity()) {
					max = tn;

				}

			}
			result = max;
			this.block = null;
		}
		return result;

	}

	/**
	 * @return the groot
	 */
	public gamestate getGroot() {
		return groot;
	}

	/**
	 * @param groot
	 *            the groot to set
	 */
	public void setGroot(gamestate groot) {
		this.groot = groot;
	}

	/**
	 * @return the root
	 */
	public Tree_Node getRoot() {
		return root;
	}

	/**
	 * @param root
	 *            the root to set
	 */
	public void setRoot(Tree_Node root) {
		this.root = root;
	}

	/**
	 * @return the endghostnode
	 */
	public static ArrayList<Tree_Node> getEndghostnode() {
		return endghostnode;
	}

	/**
	 * @param endghostnode
	 *            the endghostnode to set
	 */
	public static void setEndghostnode(ArrayList<Tree_Node> endghostnode) {
		Gaertner.endghostnode = endghostnode;
	}

	/**
	 * @return the leaves
	 */
	public ArrayList<Tree_Node> getLeaves() {
		return leaves;
	}

	/**
	 * @param leaves
	 *            the leaves to set
	 */
	public void setLeaves(ArrayList<Tree_Node> leaves) {
		this.leaves = leaves;
	}

	/**
	 * @return the devcount
	 */
	public static int getDevcount() {
		return devcount;
	}

	/**
	 * @param devcount
	 *            the devcount to set
	 */
	public static void setDevcount(int devcount) {
		Gaertner.devcount = devcount;
	}

	/**
	 * @return the tree_debth
	 */
	public int getTree_debth() {
		return tree_debth;
	}

	/**
	 * @return the pilldistancerandome
	 */

	/**
	 * @param pilldistancerandome
	 *            the pilldistancerandome to set
	 */

}
