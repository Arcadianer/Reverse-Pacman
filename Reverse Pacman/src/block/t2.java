package block;
/**
 * Game Block 
 *
 * @author Manuel Plonski
 * @see block
 */
import ch.aplu.jgamegrid.GGBackground;
import ch.aplu.jgamegrid.Location;

public class t2 extends block {

	public t2() {
		super("T-2","d", "res/t2.gif");
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
		return new t2();
	}

}
