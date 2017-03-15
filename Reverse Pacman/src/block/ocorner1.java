package block;
/**
 * Game Block 
 *
 * @author Manuel Plonski
 * @see block
 */
import ch.aplu.jgamegrid.GGBackground;
import ch.aplu.jgamegrid.Location;
/**
 * Game Block 
 *
 * @author Manuel Plonski
 * @see block
 */
public class ocorner1 extends block {

	public ocorner1() {
		super("Outer corner 1","1", "res/o_corner1.gif");
		// TODO Auto-generated constructor stub
		blockmovment=true;
	}

	@Override
	public void draw(Location lc, GGBackground bd) {
		// TODO Auto-generated method stub
		
		bd.drawImage(path, lc.x*30, lc.y*30);
	
	}

	@Override
	public block clone() {
		// TODO Auto-generated method stub
		return new ocorner1();
	}

}
