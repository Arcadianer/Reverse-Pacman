package block;

import ch.aplu.jgamegrid.GGBackground;
import ch.aplu.jgamegrid.Location;
/**
 * Game Block 
 *
 * @author Manuel Plonski
 * @see block
 */
public class e3 extends block {

	public e3() {
		super("E-3","k", "res/e3.gif");
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
		return new e3();
	}

}
