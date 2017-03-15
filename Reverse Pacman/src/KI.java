import java.awt.Color;
import java.util.ArrayList;
import ch.aplu.jgamegrid.Location;
import ch.aplu.jgamegrid.Location.CompassDirection;
import nav.Route;
import tree.Gaertner;
import tree.Tree_Node;
import tree.gamestate;
/**
 * 
 * AI CLASS 
 * Class for all AI algorithms
 * @author Manuel Plonski
 */
public class KI {
	
	public aiinterface actor;
	public static final int SIMPLE_FIND_PILLS = 0;
	public static final int CRAZY_FIND_PILLS = 1;
	@Deprecated
	public static final int PRECALC_FIND_PILLS = 2;
	public static final int AVOID_GHOST = 3;
	public static final int SMART_REFLEX_AGENT = 4;
	public static final int MIN_MAX_AI = 5;
	public static int AVOIDING_RANGE = 3;
	public static final int RANDOM = 6;
	public static boolean warningghost = false;
	public int zappelphilipcountert = 0;
	public int aiselect;
	public Navigation navi;
	public boolean onroute = false;
	public Route route;
	public boolean forceavoid = false;
	public int force_zappelstop = 0;
	public boolean switch_zappel = false;
	public ArrayList<Location> block = null;
	public boolean thinking = false;
	public int prevtreedebth=KIData.Tree_Debth;

	public ArrayList<Location> zappeldistance = new ArrayList<Location>();
/**
 * Constructor for the Ki Class
 */
	public KI(aiinterface actor, int aiselect, Navigation navi) {
		this.actor = actor;
		this.aiselect = aiselect;
		this.navi = navi;
		KIData.running = true;
		KIData.updatestatus("IDLE", Color.green);
		Statskeeper sk = new Statskeeper(aiselect);
		KIData.sk = sk;
	}
/**
 * Selects Ai that is to be used
 * @param a ID of the Ai
 */
	public void selectai(int a) {
		aiselect = a;
		Statskeeper sk = new Statskeeper(a);
		KIData.sk = sk;

	}

/**
 * Triggers the calculation step
 */
	public void act() {

		think();
		KIData.pacloc = actor.getLocation();
	}

	private synchronized void think() {
		KIData.updatestatus("THINKING", Color.green);
		switch (aiselect) {
		case 0:
			findpill();
			break;
		case 1:
			findpillcrazy();
			break;
		case 2:
			precalcfindpill();
			break;
		case 3:
			ghostavoid();
			break;
		case 4:
			simpleki();
			break;
		case 5:
			minmaxai();
			break;
		case 6:
			random();

		default:
			break;
		}
	}
/**
 * Algorithm for finding pills (Crazy edition)
 */
	public void findpillcrazy() {
		actor.eatPill(actor.getLocation());
		if (!onroute) {
			Location ailoc = actor.getLocation();

			Route min = new Route(ailoc, new Location(0, 0));
			min.setScore(9999999);
			min.setAirscore(0);
			navi.getpills();
			

					Route temp = navi.ASearch(ailoc, navi.pilllist.get(0));

					

				
			
			onroute = true;
			route = temp;
			if (PacMan.drawrouts)
				route.drawroute(PacMan.getbg());
		} else {

			boolean there = navi.walkroute(route, actor);
			if (there) {
				onroute = false;
				if (PacMan.drawrouts)
					route.cleanroute(PacMan.getbg());
			}
		}

	}
/**
 * Algorithms that moves Randomly
 */
	public void random() {

		int walkto = (int) (Math.random() * 3);

		switch (walkto) {
		case 0:

			actor.up();
			KIData.cdd = CompassDirection.NORTH;
			break;
		case 1:
			actor.down();
			KIData.cdd = CompassDirection.SOUTH;
			break;
		case 2:
			actor.right();
			KIData.cdd = CompassDirection.EAST;
			break;
		case 3:
			actor.left();
			KIData.cdd = CompassDirection.WEST;
			break;

		}

	}
	/**
	 * Algorithm for finding pills 
	 */
	public void findpill() {
		actor.eatPill(actor.getLocation());
		if (!onroute) {
			Location ailoc = actor.getLocation();
		
			Route min = new Route(ailoc, new Location(0, 0));
			min.setScore(9999999);
			min.setAirscore(9999999);
			navi.getpills();
			for (Location pill : navi.pilllist) {
				if (!(pill == null)) {
					// LUFTLIENEIN VERGLEICH
					Route temp = new Route(ailoc, pill);

					if (temp.airline() < min.airline()) {
						temp = navi.ASearch(ailoc, pill);
						if (temp.getscore() < min.getscore()) {
							min = temp;
							if (min.airline() == 1)
								break;

						}
					}
				}
			}
			onroute = true;
			route = min;
			if (KIData.draw_asearch)
				route.drawroute(PacMan.getbg());
		} else {
			boolean there = true;
			try {
				there = navi.walkroute(route, actor);
			} catch (Exception e) {
				

			}

			if (there) {
				onroute = false;
				if (PacMan.drawrouts)
					route.cleanroute(PacMan.getbg());
			}
		}

	}
@Deprecated
	public void precalcfindpill() {
		actor.eatPill(actor.getLocation());
		if (!onroute) {
			Location ailoc = actor.getLocation();
			if (ailoc.toString().equals("(10, 8)"))
				System.out.println("10,8");
			;
			Route min = new Route(ailoc, new Location(0, 0));
			min.setScore(9999999);
			min.setAirscore(9999999);
			navi.getpills();
			for (Location pill : navi.pilllist) {
				if (!(pill == null)) {
					// LUFTLIENEIN VERGLEICH
					Route temp = PacMan.precalclist[ailoc.y][ailoc.x][pill.y][pill.x];

					if (temp.getscore() < min.getscore()) {
						min = temp;
						if (min.airline() == 1)
							break;

					}
				}

			}
			onroute = true;
			Route cloneroute = new Route(min);
			cloneroute.finishroute();
			route = cloneroute;
			if (PacMan.drawrouts)
				route.drawroute(PacMan.getbg());
		} else {

			boolean there = navi.walkroute(route, actor);
			if (there) {
				onroute = false;
				if (PacMan.drawrouts)
					route.cleanroute(PacMan.getbg());
			}
		}

	}
/**
 * Algorithm that avoids Ghost
 */
	public void ghostavoid() {
		AVOIDING_RANGE=KIData.Ghost_Scare_distance;
		actor.eatPill(actor.getLocation());
		Location pacloc = actor.getLocation();
		ArrayList<Ghostplayer> gp = PacMan.getPlayerlist();
		Ghostplayer ghostyouaretoclose = null;
		//searches for ghost to close to pacman
		for (Ghostplayer gh : gp) {
			int radarlength = navi.simplelenghth(actor.getLocation(), gh.getLocation());
			if (radarlength < AVOIDING_RANGE) {
				ghostyouaretoclose = gh;
				break;
			}

		}
		if (forceavoid) {
			Ghostplayer minn = gp.get(0);
			for (Ghostplayer gh : gp) {
				int radarlength = navi.simplelenghth(actor.getLocation(), gh.getLocation());
				if (radarlength < navi.simplelenghth(actor.getLocation(), minn.getLocation())) {
					ghostyouaretoclose = gh;
					minn = gh;
					break;
				}
			}
		}
		/*
		 * Avoid ghost
		 */
		if (!(ghostyouaretoclose == null)) {
			warningghost = true;
			onroute = false;
			boolean testloc1 = navi.movinglist[pacloc.y - 1][pacloc.x];
			boolean testloc2 = navi.movinglist[pacloc.y + 1][pacloc.x];
			boolean testloc3 = navi.movinglist[pacloc.y][pacloc.x - 1];
			boolean testloc4 = navi.movinglist[pacloc.y][pacloc.x + 1];
			ArrayList<Location> chloc = new ArrayList<Location>();
			if (testloc1)
				chloc.add(new Location(pacloc.x, pacloc.y - 1));
			if (testloc2)
				chloc.add(new Location(pacloc.x, pacloc.y + 1));
			if (testloc3)
				chloc.add(new Location(pacloc.x - 1, pacloc.y));
			if (testloc4)
				chloc.add(new Location(pacloc.x + 1, pacloc.y));
			Route testrout = navi.ASearch(pacloc, ghostyouaretoclose.getLocation());
			CompassDirection c;
			try {
			c = pacloc.getCompassDirectionTo(testrout.next());
			chloc.remove(testrout.getnextcord(pacloc));
			} catch (Exception e) {
				// TODO: handle exception
				
			}
			

			

			
			if (chloc.size() == 1) {
				c = pacloc.getCompassDirectionTo(chloc.get(0));
				if (!zappelstop(c)) {
					if (c.equals(c.NORTH)) {
						actor.up();
					} else if (c.equals(c.SOUTH)) {
						actor.down();
					} else if (c.equals(c.EAST)) {
						actor.right();
					} else if (c.equals(c.WEST)) {
						actor.left();
					}
				}
			} else {
				Route escape = findroutetopillwithwhitelist(chloc);

				if (!zappelstop(pacloc.getCompassDirectionTo(escape.getnextcord(pacloc)))) {
					try {
						navi.walkroute(escape, actor);
					} catch (Exception e) {
						System.out.println("[AI] SHIT");
						System.out.println(e.getMessage());
					}
				}

			}
			for (Ghostplayer gh : gp) {
				int radarlength = navi.simplelenghth(actor.getLocation(), gh.getLocation());
				if (radarlength < AVOIDING_RANGE + 1) {
					forceavoid = true;
					break;
				} else {
					forceavoid = false;
					warningghost = false;
				}

			}

		} else {
			warningghost = false;

		}

	}
/**
 * Finds Route to closes pills only by going over the whitelist
 * @param whitelist List that 
 */
	public Route findroutetopillwithwhitelist(ArrayList<Location> whitelist) {
		Location ailoc = actor.getLocation();
		Route min = new Route(ailoc, new Location(0, 0));
		min.setScore(9999999);
		min.setAirscore(9999999);
		navi.getpills();
		for (Location pill : navi.pilllist) {
			if (!(pill == null)) {
				// LUFTLIENEIN VERGLEICH
				Route temp = new Route(ailoc, pill);

				if (temp.airline() < min.airline()) {
					temp = navi.ASearch(ailoc, pill);
					if (whitelist.contains(temp.getnextcord(ailoc))) {
						if (temp.getscore() < min.getscore()) {
							min = temp;
							if (min.airline() == 1)
								break;

						}
					}
				}
			}
		}
		return min;
	}
/**
 * Algorithm that combines ghostavoid and findpill algorithms 
 */
	public void simpleki() {
		if (!actor.getpower()) {
			try {
				ghostavoid();
			} catch (Exception e) {

			}

		} else {

			warningghost = false;
		}

		if (!warningghost) {
			zappelphilipcountert = 0;
			findpill();
		}

	}
/**
 * Returns inverted direction c
 * @param c Direction to invert
 */
	public CompassDirection invertedir(CompassDirection c) {
		CompassDirection result = null;
		switch (c) {
		case SOUTH:
			result = CompassDirection.NORTH;
			break;
		case NORTH:
			result = CompassDirection.SOUTH;
		case WEST:
			result = CompassDirection.EAST;
		case EAST:
			result = CompassDirection.WEST;

		default:
			break;
		}
		return result;
	}

	/**
	 * Algorithm that should stop Pacman from shuttering
	 * @param next direction of next move 
	 */
	public boolean zappelstop(CompassDirection next) {
		boolean result = false;
		CompassDirection cmp = invertedir(next);
		CompassDirection last = actor.getlastmove();
		if (invertedir(next) == actor.getlastmove()) {
			zappelphilipcountert++;
		} else {
			zappelphilipcountert = 0;
		}
		if (zappelphilipcountert > 3) {
			result = true;
		}
		return result;
	}

	/**
	 * Algorithm that creates a decision tree to evaluate the next steps
	 */
	public void minmaxai() {

		ArrayList<Location> ghlist = new ArrayList<Location>();

		for (Ghostplayer gh : PacMan.playerlist) {
			ghlist.add(gh.getLocation());
		}

		gamestate gs = new gamestate(PacGrid.clonemaze(), actor.getLocation(), ghlist, actor.getpills(),
				KIActor.getpower, actor.getwalked(), actor.getPpills());

		gs.setPills_left(navi.pilllist.size());
		Gaertner.tree_debth=KIData.Tree_Debth;
		Gaertner test = new Gaertner(gs);
		test.block = this.block;

		int states = test.maketree();

		Tree_Node result = test.minmax();
		block = null;
		if (!(result == null)) {
			Location pacloc = actor.getLocation();
			actor.eatPill(pacloc);
			Location walkto = result.getState().getPacloc();
			double utility = result.getState().getUtillity();
			KIData.pills = result.getState().getPills();
			KIData.pacpower = result.getState().isPacpower();
			KIData.utillity = result.getState().getUtillity();
			KIData.isterminal = result.getState().isIsterminal();
			KIData.pills_left = result.getState().getPills_left();
			KIData.pacwalked = result.getState().getPacwalked();
			KIData.ghost_k = result.getState().getGhost_k();
			KIData.pilldistance = result.getState().getPilldistance();

			KIData.pill_remain = result.getState().getPill_remain();
			KIData.pillscore = result.getState().getPillscore();
			KIData.ppill_remain = result.getState().getPpill_remain();
			KIData.ppilldistance = result.getState().getPpilldistance();
			System.out.println("PP " + actor.getPpills());
			KIData.Ppillscore = result.getState().getPpillscore();
			KIData.root = result.prev;
			KIData.update();
			System.out.println("STATS DEVELOPT : " + test.devcount + " Utillity : " + utility);
			CompassDirection cd = pacloc.get4CompassDirectionTo(walkto);
			newzappelstop(pacloc);
			KIData.updatestatus("RUNNING", Color.green);
			switch (cd) {
			case NORTH:

				actor.up();
				KIData.cdd = CompassDirection.NORTH;
				break;
			case SOUTH:
				actor.down();
				KIData.cdd = CompassDirection.SOUTH;
				break;
			case EAST:
				actor.right();
				KIData.cdd = CompassDirection.EAST;
				break;
			case WEST:
				actor.left();
				KIData.cdd = CompassDirection.WEST;
				break;

			}
			KIData.update();
		} else {
			KIData.updatestatus("SHIT", Color.RED);
			System.out.println("SHIT");
		}

	}
/**
 * @see KI#zappelstop(CompassDirection)
 */
	public boolean zappelstop_pilldistance(Location lc) {
		boolean result = false;
		ArrayList<Ghostplayer> gp = PacMan.getPlayerlist();
		Ghostplayer ghostyouaretoclose = null;
		for (Ghostplayer gh : gp) {
			int radarlength = navi.simplelenghth(actor.getLocation(), gh.getLocation());
			if (radarlength < 3) {
				ghostyouaretoclose = gh;
				break;
			}

		}
		if (ghostyouaretoclose == null) {
			this.block = new ArrayList<Location>();
			if (!(force_zappelstop == 0)) {
				block.add(lc);
				force_zappelstop--;
			}
			zappeldistance.add(lc);
			if (zappeldistance.size() > 10) {
				zappeldistance.remove(0);
			}
			if (zappeldistance.size() >= 3) {
				int testifsame = 0;
				Location test = zappeldistance.get(0);
				for (int a = 1; a < zappeldistance.size(); a++) {
					Location lc1 = zappeldistance.get(a);
					if ((lc1.toString().equals(test.toString()))) {
						testifsame++;

					}
				}

				if (testifsame >= 2) {
					result = true;
					System.out.println("ZAPPEL");
					KIData.updatestatus("ZAPPEL", Color.red);
					if (!switch_zappel) {
						block.add(zappeldistance.get(0));
						block.add(zappeldistance.get(1));
						zappeldistance.clear();
						force_zappelstop = 10;
						switch_zappel = true;
					} else {
						switch_zappel = false;
					}

				} else {
					zappelphilipcountert = 0;
				}
			}
		}else{
			block=null;
		}

		return result;
	}
	/**
	 * @see KI#zappelstop(CompassDirection)
	 */
	public boolean newzappelstop(Location lc) {
		boolean result = false;
		ArrayList<Ghostplayer> gp = PacMan.getPlayerlist();
		Ghostplayer ghostyouaretoclose = null;
		for (Ghostplayer gh : gp) {
			int radarlength = navi.simplelenghth(actor.getLocation(), gh.getLocation());
			if (radarlength < 3) {
				ghostyouaretoclose = gh;
				break;
			}

		}
		if (ghostyouaretoclose == null) {
			this.block = new ArrayList<Location>();
			if (!(force_zappelstop == 0)) {
				KIData.Tree_Debth=4;
				force_zappelstop--;
			}else{
		KIData.Tree_Debth=prevtreedebth;
			}
			zappeldistance.add(lc);
			if (zappeldistance.size() > 10) {
				zappeldistance.remove(0);
			}
			if (zappeldistance.size() >= 3) {
				int testifsame = 0;
				Location test = zappeldistance.get(0);
				for (int a = 1; a < zappeldistance.size(); a++) {
					Location lc1 = zappeldistance.get(a);
					if ((lc1.toString().equals(test.toString()))) {
						testifsame++;

					}
				}

				if (testifsame >= 2) {
					result = true;
					System.out.println("ZAPPEL");
					KIData.updatestatus("ZAPPEL", Color.red);
					if (!switch_zappel) {
						prevtreedebth=KIData.Tree_Debth;
						force_zappelstop = 5;
						
					} else {
						
					}

				} else {
					zappelphilipcountert = 0;
				}
			}
		}else{
			block=null;
		}

		return result;
	}


}
