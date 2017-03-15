package block;
/**
 * Game Block 
 *
 * @author Manuel Plonski
 * @see block
 */
import ch.aplu.jgamegrid.GGBackground;
import ch.aplu.jgamegrid.Location;

public class justblockall extends block{
	public justblockall(){
		super("blockall","x","res/test.gif",true);
	}

	@Override
	public void draw(Location lc, GGBackground bd) {
		// TODO Auto-generated method stub
		bd.drawImage(path, lc.x*30, lc.y*30);
	}

	@Override
	public block clone() {
		// TODO Auto-generated method stub
		return new justblockall();
	}
	

}
