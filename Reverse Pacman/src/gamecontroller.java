/**
 * Interface that controlls the Game.
 *  The {@link KIoption} class uses it to stop and resume the game.
 */
public interface gamecontroller {
	/**
	 * Pauses the game
	 */
public void pause();
/**
 * Resumes the game
 */
public void resume();
/**
 * Stops music
 */
public void musicstop();
/**
 * Start music
 */
public void musicplay();
/**
 * Sets game speed
 */
public void setgamespeed(int speed);
/**
 * Calculates one game step
 */
public void dostep();
}
