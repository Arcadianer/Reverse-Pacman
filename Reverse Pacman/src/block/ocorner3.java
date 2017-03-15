package block;
/**
 * Game Block 
 *
 * @author Manuel Plonski
 * @see block
 */
import ch.aplu.jgamegrid.GGBackground;
import ch.aplu.jgamegrid.Location;

public class ocorner3 extends block {

	public ocorner3() {
		super("Outer corner 3","3", "res/o_corner3.gif");
		// TODO Auto-generated constructor stub
		blockmovment=true;
	}

	@Override
	public void draw(Location lc, GGBackground bd) {
		
			bd.drawImage(path, lc.x*30, lc.y*30);
			
	}

	@Override
	public block clone() {
		// TODO Auto-generated method stub
		return new ocorner3();
	}

}
