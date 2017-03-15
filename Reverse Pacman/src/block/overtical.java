package block;

import ch./**
 * Game Block 
 *
 * @author Manuel Plonski
 * @see block
 */aplu.jgamegrid.GGBackground;
import ch.aplu.jgamegrid.Location;

public class overtical extends block {

	public overtical() {
		super("Outer vertical","|", "res/o_vertical.gif");
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
		return new overtical();
	}

}
