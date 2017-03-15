package tree;

import java.util.ArrayList;
import java.util.Arrays;

import block.block;
import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;
/**
 * Represents a state of the Game 
 */
public class gamestate {
	/**
	 * 2D Array representation of the Game-Grid
	 */
	public block[][] gamegrid;
	/**
	 * Location of Pacman 
	 */
	Location pacloc;
	/**
	 * Ghostlocation Arraylist
	 */
	ArrayList<Location> ghloc;
	/**
	 *Number of Pills 
	 */
	int pills;
	/**
	 * Pacman has Powerpill
	 */
	boolean pacpower;
	/**
	 * Utillity from the gamestate
	 */
	double utillity;
	/**
	 * Game ending State
	 */
	boolean isterminal;
	
	int pills_left;
	/**
	 * Blocks walked
	 */
	double pacwalked;
	/**
	 * Ghost distance score
	 */
	double ghost_k;
	/**
	 * Pill distance score
	 */
	double pilldistance;
	/**
	 * Pills remaining
	 */
	double pill_remain;
	/**
	 * Pill score
	 */
	double pillscore;
	/**
	 * Power Pill remain
	 */
	double ppill_remain;
	/**
	 * Power Pill d
	 */
	double ppilldistance;
	/**
	 * Number Power Pills
	 */
	int Ppills;
	/**
	 * Power Pill Score
	 */
	int Ppillscore;
	
	public gamestate(block[][] gamegrid, Location pacloc, ArrayList<Location> ghloc, int pills, boolean pacpower,int pacwalked,int Ppills) {
		super();
		this.gamegrid = gamegrid;
		this.pacloc = pacloc;
		this.ghloc = ghloc;
		this.pills = pills;
		this.pacpower = pacpower;
		this.pacwalked=pacwalked;
		this.Ppills=Ppills;
		
	}
	public gamestate() {
		
	}

	public block[][] getGamegrid() {
		return gamegrid;
	}

	public void setGamegrid(block[][] gamegrid) {
		this.gamegrid = gamegrid;
	}

	public Location getPacloc() {
		return pacloc;
	}

	public void setPacloc(Location pacloc) {
		this.pacloc = pacloc;
	}

	public ArrayList<Location> getGhloc() {
		return ghloc;
	}

	public void setGhloc(ArrayList<Location> ghloc) {
		this.ghloc = ghloc;
	}

	public int getPills() {
		return pills;
	}

	public void setPills(int pills) {
		this.pills = pills;
	}

	public boolean isPacpower() {
		return pacpower;
	}

	public void setPacpower(boolean pacpower) {
		this.pacpower = pacpower;
	}

	public Double getUtillity() {
		return utillity;
	}

	public void setUtillity(double tump) {
		this.utillity = tump;
	}
	public gamestate clone(){
		gamestate newstate=new gamestate();
		newstate.gamegrid=clonemaze();
		newstate.pacloc=pacloc.clone();
		newstate.ghloc=(ArrayList<Location>) this.ghloc.clone();
		newstate.isterminal=Boolean.valueOf(isterminal);
		newstate.pacpower=Boolean.valueOf(pacpower);
		newstate.pills=Integer.valueOf(pills);
		newstate.pills_left=Integer.valueOf(pills_left);
		newstate.utillity=Double.valueOf(utillity);
		newstate.pacwalked=Double.valueOf(pacwalked);
		newstate.ppill_remain=Double.valueOf(ppill_remain);
		newstate.pill_remain=Double.valueOf(ppilldistance);
		newstate.Ppills=Integer.valueOf(Ppills);
		newstate.Ppillscore=Integer.valueOf(Ppillscore);
		return newstate;
	}
	public int searchpillsleft(){
		int pillsleft=0;
		for(block[] bl: gamegrid){
			for(block bl2: bl){
				if(bl2.name.equals("dot")){
					pillsleft++;
				}
			}
		}
		
		
		
		return pillsleft;
		
	}
	public boolean isterminal() {
		return isterminal;
	}
	public void setterminal(boolean isterminal) {
		this.isterminal = isterminal;
	}
	public int getPills_left() {
		return pills_left;
	}
	public void setPills_left(int pills_left) {
		this.pills_left = pills_left;
	}
	
	public  block[][] clonemaze(){
		block[][] clone=new block[gamegrid.length][gamegrid[0].length];
		for(int a=0;a<gamegrid.length;a++){
			block[] mazerow=gamegrid[a];
			for(int b=0;b<mazerow.length;b++){
				clone[a][b]=gamegrid[a][b].clone();
			}
		}
		
		
		
		return clone;
		
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	
	
	/**
	 * @return the isterminal
	 */
	public boolean isIsterminal() {
		return isterminal;
	}
	/**
	 * @param isterminal the isterminal to set
	 */
	public void setIsterminal(boolean isterminal) {
		this.isterminal = isterminal;
	}
	/**
	 * @return the pacwalked
	 */
	/**
	 * @return the pacwalked
	 */
	public double getPacwalked() {
		return pacwalked;
	}
	/**
	 * @param pacwalked the pacwalked to set
	 */
	public void setPacwalked(double pacwalked) {
		this.pacwalked = pacwalked;
	}
	/**
	 * @return the ghost_k
	 */
	public double getGhost_k() {
		return ghost_k;
	}
	/**
	 * @param ghost_k the ghost_k to set
	 */
	public void setGhost_k(double ghost_k) {
		this.ghost_k = ghost_k;
	}
	/**
	 * @return the pilldistance
	 */
	public double getPilldistance() {
		return pilldistance;
	}
	/**
	 * @param pilldistance the pilldistance to set
	 */
	public void setPilldistance(double pilldistance) {
		this.pilldistance = pilldistance;
	}
	/**
	 * @return the pill_remain
	 */
	public double getPill_remain() {
		return pill_remain;
	}
	/**
	 * @param pill_remain the pill_remain to set
	 */
	public void setPill_remain(double pill_remain) {
		this.pill_remain = pill_remain;
	}
	/**
	 * @return the pillscore
	 */
	public double getPillscore() {
		return pillscore;
	}
	/**
	 * @param pillscore the pillscore to set
	 */
	public void setPillscore(double pillscore) {
		this.pillscore = pillscore;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		String returnstring="";
		for(int a=0;a<gamegrid.length;a++){
			for(int b=0;b<gamegrid[0].length;b++){
				returnstring=returnstring+gamegrid[a][b].id;
			}
			returnstring=returnstring+";;";
		}
		
		return "gamestate [gamegrid=" +returnstring+ ", pacloc=" + pacloc + ", ghloc=" + ghloc
				+ ", pills=" + pills + ", pacpower=" + pacpower + ", utillity=" + utillity + ", isterminal="
				+ isterminal + ", pills_left=" + pills_left + ", pacwalked=" + pacwalked + ", ghost_k=" + ghost_k
				+ ", pilldistance=" + pilldistance + ", pill_remain=" + pill_remain + ", pillscore=" + pillscore + "]";
	}
	/**
	 * @return the ppill_remain
	 */
	public double getPpill_remain() {
		return ppill_remain;
	}
	/**
	 * @param ppill_remain the ppill_remain to set
	 */
	public void setPpill_remain(double ppill_remain) {
		this.ppill_remain = ppill_remain;
	}
	/**
	 * @return the ppilldistance
	 */
	public double getPpilldistance() {
		return ppilldistance;
	}
	/**
	 * @param ppilldistance the ppilldistance to set
	 */
	public void setPpilldistance(double ppilldistance) {
		this.ppilldistance = ppilldistance;
	}
	public void setPpillscore(int i) {
		// TODO Auto-generated method stub
		this.Ppillscore=i;
	}
	
	public int getPpillscore() {
		return Ppillscore;
	}
	
	
	
	
}
