package block;
/**
 * Game Block 
 *
 * @author Manuel Plonski
 * @see block
 */
import ch.aplu.jgamegrid.GGBackground;
import ch.aplu.jgamegrid.Location;

public class t3 extends block {

	public t3() {
		super("T-3","s", "res/t3.gif");
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
		return new t3();
	}

}
