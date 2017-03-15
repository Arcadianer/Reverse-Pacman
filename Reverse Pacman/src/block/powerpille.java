package block;

import java.awt.Color;
import java.awt.Point;

import ch.aplu.jgamegrid.GGBackground;
import ch.aplu.jgamegrid.Location;
/**
 * Game Block 
 *
 * @author Manuel Plonski
 * @see block
 */
public class powerpille extends block {

	public powerpille(){
		super("powerpill", "p","");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Location lc, GGBackground bd) {
		// TODO Auto-generated method stub
		bd.fillCell(lc, Color.black);
		
		bd.drawCircle(new Point(lc.x*30+15, lc.y*30+15), 10);
	}

	@Override
	public block clone() {
		// TODO Auto-generated method stub
		return new powerpille();
	}

}
