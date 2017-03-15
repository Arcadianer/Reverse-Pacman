import ch.aplu.jgamegrid.Location;
import ch.aplu.jgamegrid.Location.CompassDirection;
import scala.util.Right;
/**
 * Interface that connects the AI ( {@link KI} ) with the Game Grid
 * Actor.
 * 
 * 
 * 
 * @author Manuel Plonski
 */
public interface aiinterface {
	/**
	 * Moves Actor up
	 */		
public void up();
/**
 * Moves Actor down
 */		
public void down();
/**
 * Moves Actor left
 */		
public void left();
/**
 * Moves Actor right
 */		
public void right();
/**
 * Gets the power state of the Actor
 * @return Boolean state of Power
 */		
public boolean getpower();
/**
 * Gets the pills number of the Pacman
 * @return Pill number
 */	
public int getpills();
/**
 * Gets the power pills number of the Pacman
 * @return Power-Pill number
 */	
public int getPpills();
/**
 * Gets Location of the Actor
 * @return {@link Location}
 */
public Location getLocation();
/**
 * Pacman eats pill
 */
public void eatPill(Location location);
/**
 * Gets last move direction
 * @return Last move direction
 */
public CompassDirection getlastmove();
/**
 * Returns walked steps
 * @return walked steps
 */
public int getwalked();
}
