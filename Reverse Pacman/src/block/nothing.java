package block;
/**
 * Game Block 
 *
 * @author Manuel Plonski
 * @see block
 */
import java.awt.Color;

import ch.aplu.jgamegrid.GGBackground;
import ch.aplu.jgamegrid.Location;

public class nothing extends block {

	public nothing() {
		super("void", " ","");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Location lc, GGBackground bd) {
		// TODO Auto-generated method stub
		 bd.fillCell(lc, Color.black);
	}

	@Override
	public block clone() {
		// TODO Auto-generated method stub
		return new nothing();
	}

}
