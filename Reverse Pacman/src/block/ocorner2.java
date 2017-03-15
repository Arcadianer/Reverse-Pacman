package block;
/**
 * Game Block 
 *
 * @author Manuel Plonski
 * @see block
 */
import ch.aplu.jgamegrid.GGBackground;
import ch.aplu.jgamegrid.Location;

public class ocorner2 extends block {

	public ocorner2() {
		super("Outer corner 2","2", "res/o_corner2.gif");
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
		return new ocorner2();
	}

}
