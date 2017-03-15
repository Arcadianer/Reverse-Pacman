package block;

import ch.aplu.jgamegrid.GGBackground;
import ch.aplu.jgamegrid.Location;
/**
 * Game Block 
 *
 * @author Manuel Plonski
 * @see block
 */
public class ohor extends block {

	public ohor() {
		super("Outer horizontal","-", "res/o_horizontal.gif");
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
		return new ohor();
	}

}
